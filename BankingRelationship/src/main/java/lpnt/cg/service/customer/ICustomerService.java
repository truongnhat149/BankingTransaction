package lpnt.cg.service.customer;

import lpnt.cg.model.Customer;
import lpnt.cg.service.IGeneralService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface ICustomerService extends IGeneralService<Customer> {

    Page<Customer> findAll(Pageable pageable);

    Page<Customer> findAllNoSuspended(Pageable pageable);

    Iterable<Customer> findAllByIdIsSuspended(Long id);

}
