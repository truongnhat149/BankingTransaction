package lpnt.cg.service.customer;

import lpnt.cg.model.Customer;
import lpnt.cg.service.IGeneralService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ICustomerService extends IGeneralService<Customer> {

    Page<Customer> findAll(Pageable pageable);

    List<Customer> findAllNotId(Long id);

    Iterable<Customer> findAllByIdIsNot(Long id);
}
