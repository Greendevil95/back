package WebApp.service;

import WebApp.entity.Organization;
import WebApp.repository.OrganizationRepository;
import WebApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class OrganizationServiceImpl implements OrganizatioService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    OrganizationRepository organizationRepository;

    @Override
    public ResponseEntity addOrganization(Long userId, Organization organization) {
        if (userRepository.findById(userId).isPresent()){
            organization.setUserId(userRepository.findById(userId).get());
            organizationRepository.save(organization);
            return ResponseEntity.ok("Organization with name " + organization.getName() + " added.");
        }else return ResponseEntity.notFound().build();
    }
}
