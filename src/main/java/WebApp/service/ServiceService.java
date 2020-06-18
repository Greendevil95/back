package WebApp.service;

import WebApp.entity.Organization;
import WebApp.entity.Service;
import WebApp.entity.response.EntityResponse;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

@org.springframework.stereotype.Service
public interface ServiceService extends CommonService<Service> {
    ResponseEntity<Optional<Organization>> getOrganizationForServiceById(Long id);

    ResponseEntity<EntityResponse<Service>> getListRecommendations(Long userId,Integer page, Integer pageSize, String fieldForSort, String search);
}
