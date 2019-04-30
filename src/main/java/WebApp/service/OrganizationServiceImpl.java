package WebApp.service;

import WebApp.entity.Organization;
import WebApp.entity.User;
import WebApp.entity.response.EntityResponse;
import WebApp.repository.OrganizationRepository;
import WebApp.repository.ServiceRepository;
import WebApp.repository.UserRepository;
import WebApp.service.forStatistics.OrganizationStatistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrganizationServiceImpl extends AbstractService<Organization, OrganizationRepository> implements OrganizatioService {

    public OrganizationServiceImpl(OrganizationRepository repository) {
        super(repository);
    }

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private ServiceRepository serviceRepository;

    @PreAuthorize("hasAuthority('USER')")
    @Override
    public ResponseEntity add(Organization organization) {

        String authUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional optionalAuthUser = userRepository.findByEmail(authUserName);

        organization.setUser((User) optionalAuthUser.get());
        organization.setRating((float) 0);
        organizationRepository.save(organization);
        return ResponseEntity.ok("Organization with name " + organization.getName() + " added for user with id " + ((User) optionalAuthUser.get()).getId());
    }

    @PreAuthorize("hasAuthority('USER')")
    @Override
    public ResponseEntity updateById(Long id, Organization organization) {
        if (id == null) {
            id = organization.getId();
        }

        String authUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<Organization> optionalUpdateOrganization = organizationRepository.findById(id);

        if (!optionalUpdateOrganization.isPresent()) {
            return ResponseEntity.badRequest().body("Organization not found.");
        }
        if (!isAuthUser(optionalUpdateOrganization.get().getUser())) {
            return ResponseEntity.badRequest().body("This is not your organization.");
        }

        organization.setUser(userRepository.findByEmail(authUserName).get());
        organization.setId(id);
        organizationRepository.save(organization);
        return ResponseEntity.ok("Organization with id " + organization.getId() + " and name " + organization.getName() + " was update.");
    }

    @PreAuthorize("hasAuthority('USER')")
    @Override
    public ResponseEntity update(Organization organization) {
        return updateById(organization.getId(),organization);
    }

    @PreAuthorize("hasAuthority('USER')")
    @Override
    public ResponseEntity delete(Organization organization) {
        Long organizationId = organization.getId();
        String authUserName = SecurityContextHolder.getContext().getAuthentication().getName();

        if (!organizationRepository.findById(organizationId).isPresent()) {
            return ResponseEntity.badRequest().body("Organization with id " + organizationId + " not found.");
        }
        if (!isAuthUser(organizationRepository.findById(organizationId).get().getUser())) {
            return ResponseEntity.badRequest().body("This is not your organization.");
        }
        if (organizationRepository.findById(organizationId).get().getServices()!=null) {
            return ResponseEntity.badRequest().body("Organization with id " + organizationId + " have service. ");
        }
        organizationRepository.deleteById(organizationId);
        return ResponseEntity.ok("Organization with id " + organizationId + "was delete.");
    }

    @PreAuthorize("hasAuthority('USER')")
    @Override
    public ResponseEntity deleteById(Long id) {
        Organization deleteOrganization = organizationRepository.findById(id).get();
        return delete(deleteOrganization);
    }

    @PreAuthorize("hasAuthority('USER')")
    @Override
    public ResponseEntity<EntityResponse<User>> getOwnerOrganization(Long id, Integer page, String fieldForSort, String search) {
        Optional<Organization> organization = organizationRepository.findById(id);
        if (!organization.isPresent()){
            return ResponseEntity.badRequest().build();
        }
        Pageable pageable = initPageable(page,fieldForSort,super.getPageSize());
        return ResponseEntity.ok(new EntityResponse<User>(userRepository.findByOrganization(organization.get(),pageable)));
    }

    @PreAuthorize("hasAuthority('USER')")
    @Override
    public ResponseEntity<EntityResponse<WebApp.entity.Service>> getServicesForOrganizationById(Long id, Integer page, String fieldForSort, String search) {
        Optional<Organization> organization = organizationRepository.findById(id);
        if (!organization.isPresent()){
            return ResponseEntity.badRequest().build();
        }
        Pageable pageable = initPageable(page,fieldForSort,super.getPageSize());
        return ResponseEntity.ok(new EntityResponse<WebApp.entity.Service>(serviceRepository.findByOrganization(organization.get(),pageable)));
    }

    @PreAuthorize("hasAuthority('USER')")
    @Override
    public ResponseEntity<OrganizationStatistics> getStatisticsById(Long id) {
        Optional<Organization> thisOrganization = organizationRepository.findById(id);
        if (!thisOrganization.isPresent()){
            return ResponseEntity.badRequest().build();
        }
        OrganizationStatistics statistics = new OrganizationStatistics(thisOrganization.get());

        statistics.setCountSuccsesReservation(0);
        return ResponseEntity.ok(statistics);
    }
}
