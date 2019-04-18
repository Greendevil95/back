package WebApp.repository;

import WebApp.entity.Organization;
import WebApp.entity.Reservation;
import WebApp.entity.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ServiceRepository extends CommonRepository<Service> {
    Page<Service> findByOrganization(Organization organization, Pageable pageable);
    Page<Service> findByReservations(Reservation reservation, Pageable pageable);
}
