package lpnt.cg.repository.customer;

import lpnt.cg.model.Customer;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository
public class CustomerRepository implements ICustomerRepository{

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Customer> findAll() {
        String hql = "SELECT c FROM Customer c WHERE c.isDelete = false ";
        TypedQuery<Customer> query = em.createQuery(hql, Customer.class);
        return query.getResultList();
    }

    @Override
    public Customer findById(Long id) {
        String hql = "SELECT c FROM Customer c WHERE c.id = :id AND c.isDelete = false ";
        TypedQuery<Customer> query = em.createQuery(hql, Customer.class);
        query.setParameter("id", id);
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public void save(Customer customer) {
        if (customer.getId() != null) {
            em.merge(customer);
        } else  {
            em.persist(customer);
        }
    }

    @Override
    public void remove(Long id) {
        Customer customer = findById(id);
        if (customer != null) {
            customer.setDelete(true);
            em.merge(customer);
        }
    }
}
