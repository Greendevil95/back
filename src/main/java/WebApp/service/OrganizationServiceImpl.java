package WebApp.service;

import WebApp.entity.Organization;
import WebApp.entity.User;
import WebApp.repository.OrganizationRepository;
import WebApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
            ResponseEntity.badRequest().body("Organization with id " + organizationId + " not found.");
        }
        if (isAuthUser(organizationRepository.findById(organizationId).get().getUser())) {
            organizationRepository.deleteById(organizationId);
            return ResponseEntity.ok("Organization with id " + organizationId + "was delete.");
        } else return ResponseEntity.badRequest().body("This is not your organization.");
    }

    @PreAuthorize("hasAuthority('USER')")
    @Override
    public ResponseEntity deleteById(Long id) {
        Organization deleteOrganization = organizationRepository.findById(id).get();
        return delete(deleteOrganization);
    }
}
