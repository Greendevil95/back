package WebApp.service;


import WebApp.entity.Organization;
import WebApp.entity.Reservation;
import WebApp.entity.User;
import WebApp.entity.response.EntityResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface UserService extends CommonService<User> {
    ResponseEntity getAuthUser();
    ResponseEntity getAuthUserId();
    ResponseEntity<EntityResponse<Organization>>getOrganizationForUserId(Long id, Integer page, String fieldForSort, String search);
    ResponseEntity<EntityResponse<Organization>>getOrganizationForAuthUser(Integer page, String fieldForSort, String search);
    ResponseEntity<EntityResponse<Reservation>>getReservationForUserById(Long id, Integer page, String fieldForSort, String search);
    ResponseEntity<EntityResponse<Reservation>>getReservationForAuthUser(Integer page, String fieldForSort, String search);
}