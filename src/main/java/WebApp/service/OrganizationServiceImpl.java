package WebApp.service;

import WebApp.entity.Organization;
import WebApp.entity.User;
import WebApp.repository.OrganizationRepository;
import WebApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    @Override
    public ResponseEntity add(Organization organization) {

        //статическое поле!
        String authUserName = "raya@mail.ru";

        Optional optionalAuthUser = userRepository.findByEmail(authUserName);
        if (optionalAuthUser.isPresent()) {
            organization.setUser((User) optionalAuthUser.get());
            organizationRepository.save(organization);
            return ResponseEntity.ok("Organization with name " + organization.getName() + " added for user with id " + ((User) optionalAuthUser.get()).getId());
        }   else return ResponseEntity.notFound().build();


    }

    @Override
    public ResponseEntity<Optional<Organization>> getAllForUser(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()){
            Long id = userRepository.findByEmail(user.getEmail()).get().getId();
            if (organizationRepository.findById(id).isPresent()){
                return ResponseEntity.ok(organizationRepository.findById(id));
            }else    return ResponseEntity.notFound().build();
        }else   return ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity updateById(Long id, Organization organization) {
        if (organizationRepository.findById(id).isPresent()){
            organization.setId(id);
            organizationRepository.save(organization);
            return ResponseEntity.ok("Organization with id "+ organization.getId() + " and name " + organization.getName() + " was update.");
        }else   return ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity update(Organization organization) {
        Long orgId=organization.getId();
        if (organizationRepository.findById(orgId).isPresent()){
            organizationRepository.save(organization);
            return ResponseEntity.ok("Organization with id "+ organization.getId() + " and name " + organization.getName() + " was update.");
        }else   return ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity delete(Organization organization) {
        Long orgId = organization.getId();
        if (organizationRepository.findById(orgId).isPresent()){
            organizationRepository.deleteById(orgId);
            return ResponseEntity.ok("Organization with id "+ orgId + "was delete.");
        }
        return ResponseEntity.notFound().build();
    }
}
