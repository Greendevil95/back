package WebApp.service;

import WebApp.entity.Interest;
import WebApp.entity.Organization;
import WebApp.entity.Reservation;
import WebApp.entity.User;
import WebApp.entity.enums.ReservationStatus;
import WebApp.entity.response.EntityResponse;
import WebApp.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ReservationServiceImpl extends AbstractService<Reservation, ReservationRepository> implements ReservationService {

    @Autowired
    OrganizationRepository organizationRepository;
    @Autowired
    ReservationRepository reservationRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ServiceRepository serviceRepository;
    @Autowired
    InterestRepository interestRepository;

    public ReservationServiceImpl(ReservationRepository repository) {
        super(repository);
    }

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
                .getOrganization().getUser())) {
            return ResponseEntity.badRequest().body("You coudn't add reservation to your organization.");
        }

        reservation.setUser(authUser);
        reservation.setRating(0);
        reservation.setStatus(ReservationStatus.INPROCESS);
        reservationRepository.save(reservation);

        return ResponseEntity.ok("User with username " + authUserName + " reserved to service with id =  " + reservation.getService().getId());
    }

    @PreAuthorize("hasAuthority('USER')")
    @Override
    public ResponseEntity addRating(Reservation reservation) {

        Optional<Reservation> reservation1ForRating = reservationRepository.findById(reservation.getId());
        if (!reservation1ForRating.isPresent()) {
            ResponseEntity.badRequest().body("Reservation not found.");
        }

        if (!isAuthUser(reservation1ForRating.get().getUser())) {
            ResponseEntity.badRequest().body("Its reservation not for you .");
        }
        float rating = reservation.getRating();
        rating = rating < 0 ? 0 : rating;
        rating = rating > 10. ? 10 : rating;

        reservation1ForRating.get().setRating(rating);
        reservation1ForRating.get().setStatus(ReservationStatus.FINISHED);
        reservationRepository.save(reservation1ForRating.get());

        updateServiceRating(reservation1ForRating.get().getService());
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

        if (!isAuthUser(updateReservation.get().getUser())) {
            return ResponseEntity.badRequest().body("Reservation with id " + updateReservation.get().getId() + " not found.");
        }
        reservation.setUser(updateReservation.get().getUser());
        reservation.setService(updateReservation.get().getService());
        reservation.setStatus(updateReservation.get().getStatus());
        float rating = reservation.getRating();
        rating = rating < 0 ? 0 : rating;
        rating = rating > 10. ? 10 : rating;
        reservation.setRating(rating);

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
        if (!isAuthUser(deleteReservation.get().getUser())) {
            return ResponseEntity.badRequest().body("Its not you reservation.");
        }
        reservationRepository.deleteById(reservation.getId());
        return ResponseEntity.ok("Organization with id " + reservation.getId() + "was delete.");
    }

    @PreAuthorize("hasAuthority('USER')")
    @Override
    public ResponseEntity deleteById(Long id) {
        Reservation reservation = new Reservation();
        reservation.setId(id);
        return delete(reservation);
    }

    private void updateServiceRating(WebApp.entity.Service service) {
        float rating = serviceRepository.getRating(service.getId());
        rating = (float) (Math.ceil(rating * 10) / 10);
        service.setRating(rating);
        serviceRepository.save(service);
    }

    private void updateOrganizationRating(Organization organization) {
        float rating = organizationRepository.getRating(organization.getId());
        rating = (float) (Math.ceil(rating * 10) / 10);
        organization.setRating(rating);
        organizationRepository.save(organization);
    }

    @PreAuthorize("hasAuthority('USER')")
    @Override
    public ResponseEntity<Optional<User>> getOwnerReservation(Long id) {
        Optional<Reservation> reservation = reservationRepository.findById(id);
        if (!reservation.isPresent()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(userRepository.findByReservations(reservation.get()));
    }

    @PreAuthorize("hasAuthority('USER')")
    @Override
    public ResponseEntity<Optional<WebApp.entity.Service>> getServiceForReservation(Long id) {
        Optional<Reservation> reservation = reservationRepository.findById(id);
        if (!reservation.isPresent()) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(serviceRepository.findByReservations(reservation.get()));
    }

    @PreAuthorize("hasAuthority('USER')")
    @Override
    public ResponseEntity setStatusById(Long id, ReservationStatus reservationStatuses) {

        Optional<Reservation> thisReservation = reservationRepository.findById(id);

        if (!thisReservation.isPresent()) {
            return ResponseEntity.badRequest().body("Reservation with id " + id + " not found.");
        }


        Organization organization = thisReservation.get().getService().getOrganization();
        if (!isAuthUser(thisReservation.get().getUser()) && !isAuthUser(thisReservation.get().getService().getOrganization().getUser())) {
            return ResponseEntity.badRequest().body("Its reservation not for you. ");
        }

        if (!ReservationStatus.canChange(thisReservation.get().getStatus(), reservationStatuses)) {
            return ResponseEntity.badRequest().body("Bad status. Old status is " + thisReservation.get().getStatus().toString());
        }

        thisReservation.get().setStatus(reservationStatuses);
        reservationRepository.save(thisReservation.get());

        if (reservationStatuses == ReservationStatus.FINISHED) {
            updateInteres(thisReservation.get());
        }

        return ResponseEntity.ok().body("Status changed.");

    }

    @PreAuthorize("hasAuthority('USER')")
    @Override
    public ResponseEntity<EntityResponse<Reservation>> getReservationForServiceByIdStatus(Long id, Integer page, Integer pageSize, String fieldForSort, String status) {
        Pageable pageable = initPageable(page, fieldForSort, pageSize);
        if (status == null) {
            status = "inprocess";
        }
        Optional<WebApp.entity.Service> service = serviceRepository.findById(id);
        return ResponseEntity.ok(new EntityResponse<Reservation>(reservationRepository.findByServiceAndStatus(service.get(), ReservationStatus.get(status), pageable)));
    }

    @PreAuthorize("hasAuthority('USER')")
    @Override
    public ResponseEntity getReservationForServiceByIdStatusCount(Long id, String status) {
        if (status == null) {
            status = "inprocess";
        }
        Optional<WebApp.entity.Service> service = serviceRepository.findById(id);
        return ResponseEntity.ok(reservationRepository.countByServiceAndStatus(service.get(), ReservationStatus.get(status)));
    }

    @PreAuthorize("hasAuthority('USER')")
    @Override
    public ResponseEntity getReservationForUserByIdStatusCount(Long id, String status) {
        if (status == null) {
            status = "inprocess";
        }
        return ResponseEntity.ok(reservationRepository.getCountForOwnerOrganizationAndStatus(id, status));
    }

    private void updateInteres(Reservation reservation) {
        Optional<Interest> interest = interestRepository.findByUserAndCategory(reservation.getUser(), reservation.getService().getCategory());
        if (interest.isPresent()) {
            interest.get().setCount(interest.get().getCount() + 1);
        } else {
            Interest newInteres = new Interest();
            newInteres.setUser(reservation.getUser());
            newInteres.setCategory(reservation.getService().getCategory());
            newInteres.setCount(1);
            interestRepository.save(newInteres);
        }
    }


}
