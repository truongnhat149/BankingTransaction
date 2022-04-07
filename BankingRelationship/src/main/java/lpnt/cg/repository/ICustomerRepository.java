package lpnt.cg.repository;

import lpnt.cg.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICustomerRepository extends JpaRepository<Customer, Long> {

    @Query(value = "SELECT c FROM Customer c WHERE c.id = :id")
    List<Customer> findAllNotId(@Param("id") Long id);

    Iterable<Customer> findAllByIdIsNot(Long id);

}
