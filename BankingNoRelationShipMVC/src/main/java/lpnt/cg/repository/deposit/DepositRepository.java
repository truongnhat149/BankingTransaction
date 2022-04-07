package lpnt.cg.repository.deposit;

import lpnt.cg.model.Deposit;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
public class DepositRepository implements IDepositRepository{

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public List<Deposit> findAll() {
        String hql = "SELECT d FROM Deposit d WHERE d.isDelete = false";
        TypedQuery<Deposit> query = entityManager.createQuery(hql, Deposit.class);
        return query.getResultList();
    }

    @Override
    public Deposit findById(Long id) {
        String hql = "SELECT d FROM Deposit d WHERE d.isDelete = false AND d.id = :id";
        TypedQuery<Deposit> query = entityManager.createQuery(hql, Deposit.class);
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public void save(Deposit deposit) {
        if (deposit.getId() != null) {
            entityManager.merge(deposit);
        } else  {
            entityManager.persist(deposit);
        }
    }

    @Override
    public void remove(Long id) {
        Deposit deposit = findById(id);
        if (deposit != null) {
            deposit.setDelete(true);
            entityManager.merge(deposit);
        }
    }
}
