package WebApp.service;

import WebApp.entity.Organization;
import WebApp.entity.Reservation;
import WebApp.entity.ReservationStatus;
import WebApp.entity.User;
import WebApp.entity.response.EntityResponse;
import WebApp.repository.OrganizationRepository;
import WebApp.repository.ReservationRepository;
import WebApp.repository.ServiceRepository;
import WebApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collections;
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
        reservation.setStatus(Collections.singleton(ReservationStatus.INPROCESS));
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

        updateOrganizationRating(reservation1ForRating.get().getService().getOrganization());

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
        updateServiceRating(reservation.getService());
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
        Optional<Reservation> deleteReservation = reservationRepository.findById(reservation.getId());
        if (!deleteReservation.isPresent()) {
            return ResponseEntity.badRequest().body("Reservatiom with id " + deleteReservation.get().getId() + " not found.");
        }
        if (!isAuthUser(deleteReservation.get().getUser())){
            return ResponseEntity.badRequest().body("Its not you reservation.");
        }
        reservationRepository.deleteById(reservation.getId());
        return ResponseEntity.ok("Organization with id "+ reservation.getId() + "was delete.");
    }

    @PreAuthorize("hasAuthority('USER')")
    @Override
    public ResponseEntity deleteById(Long id) {
        Reservation reservation = new Reservation();
        reservation.setId(id);
        return delete(reservation);
    }

    private void updateServiceRating(WebApp.entity.Service service){
        float rating = serviceRepository.getRating(service.getId());
        rating = (float) (Math.ceil(rating*10)*10);
        service.setRating(rating);
        serviceRepository.save(service);
    }

    private void updateOrganizationRating(Organization organization){
        float rating = organizationRepository.getRating(organization.getId());
        rating = (float) (Math.ceil(rating*10)*10);
        organization.setRating(rating);
        organizationRepository.save(organization);
    }

    @PreAuthorize("hasAuthority('USER')")
    @Override
    public ResponseEntity<EntityResponse<User>> getOwnerReservation(Long id, Integer page, String fieldForSort, String search) {
        Optional<Reservation> reservation = reservationRepository.findById(id);
        if(!reservation.isPresent()){
            return ResponseEntity.badRequest().build();
        }
        Pageable pageable = initPageable(page,fieldForSort,super.getPageSize());
        return ResponseEntity.ok(new EntityResponse<User>(userRepository.findByReservations(reservation.get(),pageable)));
    }

    @PreAuthorize("hasAuthority('USER')")
    @Override
    public ResponseEntity<EntityResponse<WebApp.entity.Service>> getServiceForReservation(Long id, Integer page, String fieldForSort, String search) {
        Optional<Reservation> reservation = reservationRepository.findById(id);
        if(!reservation.isPresent()){
            return ResponseEntity.badRequest().build();
        }
        Pageable pageable = initPageable(page,fieldForSort,super.getPageSize());
        return ResponseEntity.ok(new EntityResponse<WebApp.entity.Service>(serviceRepository.findByReservations(reservation.get(),pageable)));
    }

    @PreAuthorize("hasAuthority('USER')")
    @Override
    public ResponseEntity setStatus(Reservation reservation) {

        Optional<Reservation> thisReservatio = reservationRepository.findById(reservation.getId());

        if (!thisReservatio.isPresent()){
            return ResponseEntity.badRequest().body("Reservation with id " + reservation.getId() + " not found." );
        }

        String authUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> authUser = userRepository.findByEmail(authUserName);
        Organization organization = thisReservatio.get().getService().getOrganization();
        if (!authUser.get().equals(organization.getUser())){
            return ResponseEntity.badRequest().body("Its reservation not for you organization. ");
        }

        thisReservatio.get().setStatus(reservation.getStatus());
        reservationRepository.save(thisReservatio.get());
        return ResponseEntity.ok().body("Status changed.");

    }
}
