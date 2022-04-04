package lpnt.cg.service.customer;

import lpnt.cg.model.Customer;
import lpnt.cg.repository.ICustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService implements ICustomerService {

    @Autowired
    private ICustomerRepository customerRepository;

    @Override
    public Iterable<Customer> findAll() {
        return customerRepository.findAll();
    }

    @Override
    public Optional<Customer> findById(Long id) {
        return customerRepository.findById(id);
    }

    @Override
    public void save(Customer customer) {
        customerRepository.save(customer);
    }

    @Override
    public void remove(Long id) {
        Customer customer = findById(id).get();
        customer.setSupended(true);
        save(customer);
    }

    @Override
    public Page<Customer> findAll(Pageable pageable) {
        return customerRepository.findAll(pageable);
    }

    @Override
    public List<Customer> findAllNotId(Long id) {
        return customerRepository.findAllNotId(id);
    }

    @Override
    public Iterable<Customer> findAllByIdIsNot(Long id) {
        return customerRepository.findAllByIdIsNot(id);
    }
}
