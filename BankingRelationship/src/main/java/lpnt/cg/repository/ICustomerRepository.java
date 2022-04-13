package lpnt.cg.repository;

import lpnt.cg.model.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface ICustomerRepository extends JpaRepository<Customer, Long> {

    @Query(value = "SELECT c FROM Customer c WHERE c.id <>:id AND c.suspended = false ")
    Iterable<Customer> findAllByIdIsSuspended(@Param("id") Long id);

    @Query(value = "SELECT c FROM Customer c WHERE c.suspended = false ")
    Page<Customer> findAllNoSuspended(Pageable pageable);

}
    