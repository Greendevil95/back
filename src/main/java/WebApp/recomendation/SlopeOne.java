package WebApp.recomendation;

import WebApp.entity.Reservation;
import WebApp.entity.Service;
import WebApp.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;
import java.util.Map.Entry;

/**
 * Slope One algorithm implementation
 */
public class SlopeOne {

    private static Map<Service, Map<Service, Double>> difference = new HashMap<>();
    private static Map<Service, Map<Service, Integer>> frequency = new HashMap<>();
    private static Map<User, HashMap<Service, Double>> inputData;
    private static Map<User, HashMap<Service, Double>> outputData = new HashMap<>();

    public static Page<Service> slopeOne( User user, Double minRating, Pageable pageable,List<Reservation> rec) {
        inputData = toHashMap(rec);
        printData(inputData);
        //inputData = InputData.initializeData(numberOfUsers);
        long start = System.currentTimeMillis();
        System.out.println("Slope One - Before the Prediction\n");
        buildDifferencesMatrix(inputData);
        System.out.println("\nSlope One - With Predictions\n");
        ArrayList<Service> result = predict(inputData, user, minRating, 0.55, 0.45);
        long finish = System.currentTimeMillis();
        long resultCalc = finish - start;
        System.out.println("result = " + resultCalc);
        System.out.println(result.toString());
        int first =(int) pageable.getOffset();
        int end = (first + pageable.getPageSize()) > result.size() ? result.size() : (first + pageable.getPageSize());
        return new PageImpl<Service>(result.subList(first, end), pageable, result.size());
    }

    private static Map<User, HashMap<Service, Double>> toHashMap(List<Reservation> rec){
        Map<User, HashMap<Service, Double>> allElements = new HashMap<>();
        HashMap<Service, Double> map2;
        for (int i = 0; i<rec.size(); i++){
            map2 = new HashMap<Service, Double>();
            map2.put(rec.get(i).getService(), (double)rec.get(i).getRating());
            if(i+1 < rec.size() && rec.get(i + 1).getUser().equals(rec.get(i).getUser()))
                for(Reservation reservation: rec) {
                    if (reservation.getUser().equals(rec.get(i).getUser()))
                        map2.put(reservation.getService(),(double)reservation.getRating());
                }
            if(!allElements.keySet().contains(rec.get(i).getUser()))
                allElements.put(rec.get(i).getUser(), map2);
        }
        return allElements;
    }

    /**
     * Нахождение матрицы отклонений
     * @param data существующая матрица оценок
     */
    private static void buildDifferencesMatrix(Map<User, HashMap<Service, Double>> data) {
        for (HashMap<Service, Double> user : data.values()) {
            for (Entry<Service, Double> e : user.entrySet()) {
                if (!difference.containsKey(e.getKey())) {
                    difference.put(e.getKey(), new HashMap<Service, Double>());
                    frequency.put(e.getKey(), new HashMap<Service, Integer>());
                }
                for (Entry<Service, Double> e2 : user.entrySet()) {
                    int oldCount = 0;
                    if (frequency.get(e.getKey()).containsKey(e2.getKey())) {
                        oldCount = frequency.get(e.getKey()).get(e2.getKey());
                    }
                    double oldDiff = 0.0;
                    if (difference.get(e.getKey()).containsKey(e2.getKey())) {
                        oldDiff = difference.get(e.getKey()).get(e2.getKey());
                    }
                    double observedDiff = e.getValue() - e2.getValue();
                    //System.out.println("obsDif =" + observedDiff);
                    frequency.get(e.getKey()).put(e2.getKey(), oldCount + 1);
                    difference.get(e.getKey()).put(e2.getKey(), oldDiff + observedDiff);
                }
            }
        }
        for (Service j : difference.keySet()) {
            for (Service i : difference.get(j).keySet()) {
                double oldValue = difference.get(j).get(i);
                int count = frequency.get(j).get(i);
                //System.out.println("oldValue = " + oldValue);
                //System.out.println("count = " + count);
                difference.get(j).put(i, oldValue / count);
            }
        }
        //prinDiff(difference);
        //prinFreq(frequency);
        //printData(data);
    }

    /**
     * На основании имеющихся данных прогнозируют все недостающие рейтинги.
     * Если предсказание не возможно, значение будет равно -1
     * @param data существующая матрица оценок
     */
    private static ArrayList<Service> predict(Map<User, HashMap<Service, Double>> data, User user, Double minRat, Double w1, Double w2) {
        HashMap<Service, Double> uPred = new HashMap<Service, Double>();
        HashMap<Service, Integer> uFreq = new HashMap<Service, Integer>();
        ArrayList<Service> recomendation = new ArrayList<>();
        for (Service j : difference.keySet()) {
            uFreq.put(j, 0);
            uPred.put(j, 0.0);
        }
        for (Entry<User, HashMap<Service, Double>> e : data.entrySet()) {
            for (Service j : e.getValue().keySet()) {
                for (Service k : difference.keySet()) {
                    try {
                        double predictedValue = difference.get(k).get(j) + e.getValue().get(j);
                        //System.out.println("predict value = " + predictedValue);
                        double finalValue = predictedValue * frequency.get(k).get(j);
                        //System.out.println("final value = " + finalValue);
                        uPred.put(k, uPred.get(k) + finalValue);
                        uFreq.put(k, uFreq.get(k) + frequency.get(k).get(j));
                    } catch (NullPointerException e1) {
                        e1.fillInStackTrace();
                    }
                }
            }
            HashMap<Service, Double> clean = new HashMap<Service, Double>();
            for (Service j : uPred.keySet()) {
                if (uFreq.get(j) > 0) {
                    clean.put(j, uPred.get(j) / uFreq.get(j));
                }
            }
            for(Entry<User, HashMap<Service, Double>> e2 :inputData.entrySet()){
            for (Service j : e.getValue().keySet()) {
                if (e.getValue().containsKey(j)) {
                 double weightRat = e.getValue().get(j) * w1 + j.getRating() * w2;
                    clean.put(j, weightRat);
                } else if (!clean.containsKey(j)) {
                    clean.put(j, -1.0);
                }
            }
            }
            outputData.put(e.getKey(), clean);

        }
            for(Entry<User, HashMap<Service, Double>> map: outputData.entrySet()) { //create recommendation list
                if (map.getKey() == user) {
                    for (Entry<Service, Double> eSet : map.getValue().entrySet()) {
                        if (eSet.getValue() >= minRat)
                            recomendation.add(eSet.getKey());
                    }
                    System.out.println("res = " + user.getReservations());
                    for (Reservation res : user.getReservations()) { //delete own reservation from recommendation list
                        if(res.getRating() > 0)
                        recomendation.remove(res.getService());

                    }
                }
            }


            recomendation.sort(Comparator.comparing(Service::getRating));
        //System.out.println("rec = " + recomendation.toString());
        printData(outputData);
        return recomendation;
        //rmse(outputData);
    }


    static double CalculateRmse(double[] pred, double[] real) {
        double sum = 0;
        double average;
        double rmse;

        int n = pred.length;
        for (int i = 0; i < n; i++) {
            sum += Math.pow((pred[i] - real[i]), 2);
        }
        average = sum / n;
        rmse = Math.sqrt(average);
        return rmse;
    }


    private static void printData(Map<User, HashMap<Service, Double>> data) {
        for (User user : data.keySet()) {
            System.out.println(user.getName() + ":");
            print(data.get(user));
        }
    }

    private static void print(HashMap<Service, Double> hashMap) {
        NumberFormat formatter = new DecimalFormat("#0.000");
        for (Service j : hashMap.keySet()) {
            System.out.println(" " + j.getName() + " --> " + formatter.format(hashMap.get(j).doubleValue()));
        }
    }

    private static void prinDiff(Map<Service, Map<Service, Double>> data){
            for(Service service : data.keySet()){
                System.out.println(service.getName() + ":");
                print2(data.get(service));
            }
    }
    private static void print2(Map<Service, Double> hashMap) {
        NumberFormat formatter = new DecimalFormat("#0.000");
        for (Service j : hashMap.keySet()) {
            System.out.println(" " + j.getName() + " --> " + formatter.format(hashMap.get(j).doubleValue()));
        }
    }


    private static void prinFreq(Map<Service, Map<Service, Integer>> data) {
        for (Map<Service, Integer> item : data.values()) {
            System.out.println("item  freq= " + item);
        }
    }

}
