package WebApp.service;

import WebApp.entity.Organization;
import WebApp.entity.Reservation;
import WebApp.entity.User;
import WebApp.repository.OrganizationRepository;
import WebApp.repository.ReservationRepository;
import WebApp.repository.ServiceRepository;
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

    @Autowired
    ServiceRepository serviceRepository;

    @PreAuthorize("hasAuthority('USER')")
    @Override
    public ResponseEntity add(Reservation reservation) {

        if (!serviceRepository.findById(reservation.getService().getId()).isPresent())
            return ResponseEntity.badRequest().body("Service not found.");

        String authUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        User authUser = userRepository.findByEmail(authUserName).get();

        if (isAuthUser(serviceRepository.findById(
                reservation.getService().getId())
                .get()
                .getOrganization().getUser())){
            return ResponseEntity.badRequest().body("You coudn't add reservation to your organization.");
        }

        reservation.setUser(authUser);
        reservation.setRating(0);
        reservationRepository.save(reservation);

        return ResponseEntity.ok("User with username " + authUserName + "reserved to service with id =  " + reservation.getService().getId());
    }

    @PreAuthorize("hasAuthority('USER')")
    @Override
    public ResponseEntity addRating(Reservation reservation) {

        Optional<Reservation> reservation1ForRating = reservationRepository.findById(reservation.getId());
        if (!reservation1ForRating.isPresent()){
            ResponseEntity.badRequest().body("Reservation not found.");
        }

        if (!isAuthUser(reservation1ForRating.get().getUser())){
            ResponseEntity.badRequest().body("Its reservation not for you .");
        }

        reservation1ForRating.get().setRating(reservation.getRating());
        reservationRepository.save(reservation1ForRating.get());

        updateOrganizationRating(reservation.getService().getOrganization());

        return ResponseEntity.ok("Rating save for service.");
    }

    @PreAuthorize("hasAuthority('USER')")
    @Override
    public ResponseEntity update(Reservation reservation) {
        Optional<Reservation> updateReservation = reservationRepository.findById(reservation.getId());
        if (!updateReservation.isPresent()) {
            return ResponseEntity.badRequest().body("Reservation with id " + updateReservation.get().getId() + " not found.");
        }

        if (!isAuthUser(updateReservation.get().getUser())){
            return ResponseEntity.badRequest().body("Reservation with id " + updateReservation.get().getId() + " not found.");
        }
        reservation.setUser(updateReservation.get().getUser());
        reservation.setService(updateReservation.get().getService());
        reservationRepository.save(reservation);
        updateOrganizationRating(reservation.getService().getOrganization());
        return ResponseEntity.ok("Reservation with id " + reservation.getId() + " was update");
    }

    @PreAuthorize("hasAuthority('USER')")
    @Override
    public ResponseEntity updateById(Long id, Reservation reservation) {
        reservation.setId(id);
        return update(reservation);
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

    private void updateOrganizationRating(Organization organization){
//
//        organization.setRating(organizationRatin);
//        organizationRepository.save(organization);


    }

}
