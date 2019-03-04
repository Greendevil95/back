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
public class OrganizationServiceImpl implements OrganizatioService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrganizationRepository organizationRepository;

    @Override
    public ResponseEntity add(Long id, Organization organization) {
        Optional user =userRepository.findById(id);
        if (user.isPresent()){
            organization.setUser((User) user.get());
            organizationRepository.save(organization);
            return ResponseEntity.ok("Organization with name "+ organization.getName()+ " added for user with id "+ ((User) user.get()).getId());
        }else   return ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity<Iterable<Organization>> getAll() {
        return ResponseEntity.ok(organizationRepository.findAll());
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
    public ResponseEntity<Organization> getById(Long id){
        if(organizationRepository.findById(id).isPresent()){
            return ResponseEntity.ok(organizationRepository.findById(id).get());
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
    public ResponseEntity deleteById(Long id) {
        if (organizationRepository.findById(id).isPresent()){
            organizationRepository.deleteById(id);
            return ResponseEntity.ok("Organization with id "+ id + "was delete.");
        }
        return ResponseEntity.notFound().build();
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
