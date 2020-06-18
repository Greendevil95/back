package WebApp.service;

import WebApp.entity.Organization;
import WebApp.entity.Reservation;
import WebApp.entity.Service;
import WebApp.entity.User;
import WebApp.entity.response.EntityResponse;
import WebApp.recomendation.SlopeOne;
import WebApp.repository.OrganizationRepository;
import WebApp.repository.ReservationRepository;
import WebApp.repository.ServiceRepository;
import WebApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.*;

@org.springframework.stereotype.Service
public class ServiceServiceImpl extends AbstractService<Service, ServiceRepository> implements ServiceService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    OrganizationRepository organizationRepository;
    @Autowired
    ServiceRepository serviceRepository;
    @Autowired
    ReservationRepository reservationRepository;

    public ServiceServiceImpl(ServiceRepository repository) {
        super(repository);
    }

    @PreAuthorize("hasAuthority('USER')")
    @Override
    public ResponseEntity add(Service service) {
        Organization organizationWithService = organizationRepository.findById(service.getOrganization().getId()).get();
        if (!isAuthUser(organizationWithService.getUser())) {
            return ResponseEntity.badRequest().body("This is not your organization.");
        }

        service.setOrganization(organizationWithService);
        service.setReservations(null);
        if (service.getTime() == null) {
            service.setTime(60);
        }
        service.setRating((float) 0);
        serviceRepository.save(service);
        return ResponseEntity.ok().body("Service for organization " + organizationWithService.getName() + " added.");
    }

    /*@PreAuthorize("hasAuthority('USER')")
    @Override
    public ResponseEntity<EntityResponse<Service>> getAll(Integer page, Integer pageSize, String fieldForSort, String search) {

        Specification<Service> specification = initSpecification(search);
        Pageable pageable = initPageable(page, fieldForSort, pageSize);
        ResponseEntity<EntityResponse<Service>> r = getListRecomendations(userRepository.findByOrganization(service.getOrganization()).get().getId());
        return ResponseEntity.ok(new EntityResponse<Service>(repository.findAll(specification, pageable)));
    }*/


    @PreAuthorize("hasAuthority('USER')")
    @Override
    public ResponseEntity updateById(Long id, Service service) {
        if (id == null)
            id = service.getId();

        if (!serviceRepository.findById(id).isPresent()) {
            return ResponseEntity.badRequest().body("Service add id " + id + " not found");
        }

        Organization serviceForOrganization = organizationRepository.findById(service.getOrganization().getId()).get();

        if (!isAuthUser(serviceForOrganization.getUser())) {
            return ResponseEntity.badRequest().body("This is not your organization.");
        }

        service.setId(id);
        service.setReservations(serviceRepository.findById(id).get().getReservations());

        service.setOrganization(serviceForOrganization);
        return ResponseEntity.ok("Service add id " + service.getId() + " updated.");
    }

    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<EntityResponse<Service>> getListRecommendations(Long userId,Integer page, Integer pageSize, String fieldForSort, String search){
        if (reservationRepository.findUserRaitingCount(userId) > 2) {
            List<Reservation> rec = reservationRepository.findAllWithRating();
            Pageable pageable = initPageable(page, fieldForSort, pageSize);
            return ResponseEntity.ok(new EntityResponse<Service>(SlopeOne.slopeOne( userRepository.findById(userId).get(), 4.0, pageable,rec)));
        }
            else return getAll(page, pageSize, fieldForSort, search);
    }

    /*@PreAuthorize("hasAuthority('USER')")
    private ResponseEntity<EntityResponse<Service>> getFakeListRecommendations(Long userId, Integer page, Integer pageSize, String fieldForSort, String search){
        Specification<Service> specification = initSpecification(search);
        Pageable pageable = initPageable(page, fieldForSort, pageSize);
        return ResponseEntity.ok(new EntityResponse<Service>(serviceRepository.findAllWithGoodRat(specification,pageable));
    }*/

    @PreAuthorize("hasAuthority('USER')")
    @Override
    public ResponseEntity update(Service service) {
        return updateById(service.getId(), service);
    }

    @PreAuthorize("hasAuthority('USER')")
    @Override
    public ResponseEntity delete(Service service) {

        Optional<Service> serviceForDelete = serviceRepository.findById(service.getId());
        if (!serviceForDelete.isPresent()) {
            return ResponseEntity.badRequest().body("Service add id " + service.getId() + " not found");
        }

        String authUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        User authUser = userRepository.findByEmail(authUserName).get();
        Organization serviceForOrganization = organizationRepository.findById(serviceForDelete.get().getOrganization().getId()).get();

        if (!serviceForOrganization.getUser().equals(authUser)) {
            return ResponseEntity.badRequest().body("This is not your organization.");
        }
        if (serviceForDelete.get().getReservations() != null) {
            return ResponseEntity.badRequest().body("Service add id " + service.getId() + " have reservation.");
        }
        serviceRepository.deleteById(service.getId());
        return ResponseEntity.ok("Service add id " + service.getId() + " was deleted.");
    }

    @PreAuthorize("hasAuthority('USER')")
    @Override
    public ResponseEntity deleteById(Long id) {
        Optional<Service> service = serviceRepository.findById(id);
        if (service.isPresent()) {
            return delete(serviceRepository.findById(id).get());
        }
        else {
            return ResponseEntity.badRequest().body("Service add id " + id + " not found");
        }
    }

    @PreAuthorize("hasAuthority('USER')")
    @Override
    public ResponseEntity<Optional<Organization>> getOrganizationForServiceById(Long id) {
        Optional<Service> service = serviceRepository.findById(id);
        if (!service.isPresent()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(organizationRepository.findByServices(service.get()));
    }


}
