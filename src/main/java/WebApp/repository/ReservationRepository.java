package WebApp.repository;

import WebApp.entity.Organization;
import WebApp.entity.Reservation;

public interface ReservationRepository extends CommonRepository<Reservation> {
    Iterable<Reservation> findByOrganization (Organization organization);
    Integer countByOrganization (Organization organization);
}
