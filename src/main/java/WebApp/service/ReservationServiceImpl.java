package WebApp.service;

import WebApp.entity.Organization;
import WebApp.entity.Reservation;
import WebApp.entity.User;
import WebApp.repository.OrganizationRepository;
import WebApp.repository.ReservationRepository;
import WebApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ReservationServiceImpl extends AbstractService<Reservation,ReservationRepository> implements ReservationService {


    public ReservationServiceImpl(ReservationRepository repository) {
        super(repository);
    }

    @Autowired
    OrganizationRepository organizationRepository;

    @Autowired
    ReservationRepository reservationRepository;

    @Autowired
    UserRepository userRepository;


    @Override
    public ResponseEntity add(Reservation reservation) {
        //static field
        String authUserName = "raya@mail.ru";
        Optional optionalAuthUser = userRepository.findByEmail(authUserName);
        if (optionalAuthUser.isPresent()) {

            if (organizationRepository.findById(reservation.getOrganization().getId()).isPresent()){

                reservation.setUser((User) optionalAuthUser.get());
                reservation.setRating(0);
                reservationRepository.save(reservation);

                return ResponseEntity.ok("User with username " + authUserName + "reserved to organization with name" + reservation.getOrganization().getName());
            } else return ResponseEntity.notFound().build();
        } else return ResponseEntity.notFound().build();
    }

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

    @Override
    public ResponseEntity addRating(Reservation reservation) {
        float rating = reservation.getRating();
        if (reservationRepository.findById(reservation.getId()).isPresent()){
            reservation.setRating(rating);
            reservationRepository.save(reservation);

            updateOrganizationRating(reservation.getOrganization(),rating);
            return ResponseEntity.ok("Rating save for organization " + reservation.getOrganization().getName());
        }else return ResponseEntity.notFound().build();

    }

    private void updateOrganizationRating(Organization organization, float Rating){

        if (organizationRepository.findById(organization.getId()).isPresent()) {

            float organizationRatin = organization.getRating();
            Integer countOrganizationReservation = reservationRepository.countByOrganization(organization);

            organizationRatin = (organizationRatin * (countOrganizationReservation - 1) + Rating) / (countOrganizationReservation);

            organization.setRating(organizationRatin);
            organizationRepository.save(organization);
        }
    }

}
