package WebApp.service;

import WebApp.entity.Organization;
import WebApp.entity.Reservation;
import WebApp.entity.Service;
import WebApp.entity.response.EntityResponse;
import org.springframework.http.ResponseEntity;

@org.springframework.stereotype.Service
public interface ServiceService extends CommonService<Service> {
    ResponseEntity<EntityResponse<Organization>> getOrganizationForServiceById(Long id, Integer page, String fieldForSort, String search);
    ResponseEntity<EntityResponse<Reservation>> getReservationForServiceById(Long id, Integer page, String fieldForSort, String search);
}
