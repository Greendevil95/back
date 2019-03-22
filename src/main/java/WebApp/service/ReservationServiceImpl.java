package WebApp.service;

import WebApp.entity.Reservation;
import WebApp.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ReservationServiceImpl extends AbstractService<Reservation,ReservationRepository> implements ReservationService {


    public ReservationServiceImpl(ReservationRepository repository) {
        super(repository);
    }

    @Autowired
    ReservationRepository reservationRepository;

    @Override
    public ResponseEntity update(Reservation reservation) {
        if (reservationRepository.findById(reservation.getId()).isPresent()) {
            reservationRepository.save(reservation);
            return ResponseEntity.ok("Reservation with id " + reservation.getId() + " was update");
        }   else    return ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity updateById(Long id, Reservation reservation) {
        if (reservationRepository.findById(id).isPresent()) {
            reservation.setId(id);
            reservationRepository.save(reservation);
            return ResponseEntity.ok("Reservation with id " + reservation.getId() + " was update");
        }   else    return ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity delete(Reservation reservation) {
        if (reservationRepository.findById(reservation.getId()).isPresent()){
            reservationRepository.deleteById(reservation.getId());
            return ResponseEntity.ok("Organization with id "+ reservation.getId() + "was delete.");
        }
        return ResponseEntity.notFound().build();
    }
}
