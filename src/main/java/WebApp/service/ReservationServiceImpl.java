package WebApp.service;

import WebApp.entity.Organization;
import WebApp.entity.Reservation;
import WebApp.entity.User;
import WebApp.repository.OrganizationRepository;
import WebApp.repository.ReservationRepository;
import WebApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @PreAuthorize("hasAuthority('USER')")
    @Override
    public ResponseEntity add(Reservation reservation) {

        String authUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional optionalAuthUser = userRepository.findByEmail(authUserName);

        if (optionalAuthUser.isPresent()) {

            Long reservedOrganizationId = reservation.getOrganization().getId();

            if (!userRepository.findByOrganization(reservation.getOrganization()).equals(reservation.getUser())) {
                if (organizationRepository.findById(reservedOrganizationId).isPresent()) {

                    reservation.setUser((User) optionalAuthUser.get());
                    reservation.setRating(0);
                    reservationRepository.save(reservation);

                    return ResponseEntity.ok("User with username " + authUserName + "reserved to organization with name" + reservation.getOrganization().getName());
                } else return ResponseEntity.badRequest().body("Organization for " + authUserName + " not found.");
            }else return ResponseEntity.badRequest().body("You coudn't add reservation to your organization.");

        } else return ResponseEntity.badRequest().body("User with email " + authUserName + " not found.");
    }

    @PreAuthorize("hasAuthority('USER')")
    @Override
    public ResponseEntity update(Reservation reservation) {
        if (reservationRepository.findById(reservation.getId()).isPresent()) {
            reservationRepository.save(reservation);
            return ResponseEntity.ok("Reservation with id " + reservation.getId() + " was update");
        }   else    return ResponseEntity.badRequest().body("Reservation not found.");
    }

    @PreAuthorize("hasAuthority('USER')")
    @Override
    public ResponseEntity updateById(Long id, Reservation reservation) {
        if (reservationRepository.findById(id).isPresent()) {
            reservation.setId(id);
            reservationRepository.save(reservation);
            return ResponseEntity.ok("Reservation with id " + reservation.getId() + " was update.");
        }   else    return ResponseEntity.notFound().build();
    }

    @PreAuthorize("hasAuthority('USER')")
    @Override
    public ResponseEntity delete(Reservation reservation) {
        if (reservationRepository.findById(reservation.getId()).isPresent()){
            reservationRepository.deleteById(reservation.getId());
            return ResponseEntity.ok("Organization with id "+ reservation.getId() + "was delete.");
        }
        return ResponseEntity.notFound().build();
    }

    @PreAuthorize("hasAuthority('USER')")
    @Override
    public ResponseEntity addRating(Reservation reservation) {
        float rating = reservation.getRating();
        if (reservationRepository.findById(reservation.getId()).isPresent()){
            reservation.setRating(rating);
            reservationRepository.save(reservation);

            updateOrganizationRating(reservation.getOrganization(),rating);
            return ResponseEntity.ok("Rating save for organization " + reservation.getOrganization().getName());
        }else return ResponseEntity.badRequest().body("Reservation not found.");

    }

    @PreAuthorize("hasAuthority('USER')")
    private ResponseEntity updateOrganizationRating(Organization organization, float Rating){

        if (organizationRepository.findById(organization.getId()).isPresent()) {

            float organizationRatin = organization.getRating();
            Integer countOrganizationReservation = reservationRepository.countByOrganization(organization);

            organizationRatin = (organizationRatin * (countOrganizationReservation - 1) + Rating) / (countOrganizationReservation);

            organization.setRating(organizationRatin);
            organizationRepository.save(organization);
            return ResponseEntity.ok().build();
        }else return ResponseEntity.badRequest().body("Organization not found.");
    }

}
