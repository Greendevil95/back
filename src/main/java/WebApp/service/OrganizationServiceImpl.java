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
    public ResponseEntity addOrganization(Long id, Organization organization) {
        Optional user =userRepository.findById(id);
        if (user.isPresent()){
            organization.setUser((User) user.get());
            organizationRepository.save(organization);
            return ResponseEntity.ok("Organization with name "+ organization.getName()+ " added for user with id "+ ((User) user.get()).getId());
        }
        return ResponseEntity.notFound().build();
    }
}
