package WebApp.service;

import WebApp.entity.Organization;
import WebApp.entity.Service;
import WebApp.entity.User;
import WebApp.repository.OrganizationRepository;
import WebApp.repository.ServiceRepository;
import WebApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;

@org.springframework.stereotype.Service
public class ServiceServiceImpl extends AbstractService<Service,ServiceRepository> implements ServiceService {

    public ServiceServiceImpl(ServiceRepository repository) {
        super(repository);
    }

    @Autowired
    UserRepository userRepository;

    @Autowired
    OrganizationRepository organizationRepository;

    @Autowired
    ServiceRepository serviceRepository;

    @PreAuthorize("hasAuthority('USER')")
    @Override
    public ResponseEntity add(Service service) {
        String authUserName =SecurityContextHolder.getContext().getAuthentication().getName();
        User authUser = userRepository.findByEmail(authUserName).get();
        Organization serviceForOrganization = organizationRepository.findById(service.getOrganization().getId()).get();
        if (serviceForOrganization.getUser().equals(authUser)){
            service.setOrganization(serviceForOrganization);
            service.setReservations(null);
            serviceRepository.save(service);
            return ResponseEntity.ok().body("Service for organization " + serviceForOrganization.getName() + " added.");
        } else return ResponseEntity.badRequest().body("This is not your organization.");
    }

    @PreAuthorize("hasAuthority('USER')")
    @Override
    public ResponseEntity updateById(Long id, Service service) {
        if (id == null)
            id = service.getId();

        if (!serviceRepository.findById(id).isPresent())
            return ResponseEntity.badRequest().body("Service with id " + id + " not found");

        service.setId(id);
        service.setReservations(serviceRepository.findById(id).get().getReservations());

        String authUserName =SecurityContextHolder.getContext().getAuthentication().getName();
        User authUser = userRepository.findByEmail(authUserName).get();
        Organization serviceForOrganization = organizationRepository.findById(service.getOrganization().getId()).get();
        if (!serviceForOrganization.getUser().equals(authUser))
            return ResponseEntity.badRequest().body("This is not your organization.");

        service.setOrganization(serviceForOrganization);
        return ResponseEntity.ok("Service with id " + service.getId() + " updated." );
    }

    @PreAuthorize("hasAuthority('USER')")
    @Override
    public ResponseEntity update(Service service) {
        return updateById(service.getId(),service);
    }

    @PreAuthorize("hasAuthority('USER')")
    @Override
    public ResponseEntity delete(Service service) {

        Service serviceForDelet = serviceRepository.findById(service.getId()).get();
        if (serviceForDelet == null)
            return ResponseEntity.badRequest().body("Service with id " + service.getId() + " not found");

        String authUserName =SecurityContextHolder.getContext().getAuthentication().getName();
        User authUser = userRepository.findByEmail(authUserName).get();
        Organization serviceForOrganization = organizationRepository.findById(serviceForDelet.getOrganization().getId()).get();

        if (!serviceForOrganization.getUser().equals(authUser))
            return ResponseEntity.badRequest().body("This is not your organization.");

        serviceRepository.deleteById(service.getId());
        return ResponseEntity.ok("Service with id " + service.getId() + " was deleted.");
    }
}
