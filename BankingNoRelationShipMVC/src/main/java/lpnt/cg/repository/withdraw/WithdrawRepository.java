package lpnt.cg.repository.withdraw;

import lpnt.cg.model.Withdraw;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
public class WithdrawRepository implements  IWithdrawRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Withdraw> findAll() {
        String hql = "SELECT w FROM Withdraw w WHERE w.isDelete = false";
        TypedQuery<Withdraw> query = entityManager.createQuery(hql, Withdraw.class);
        return query.getResultList();
    }

    @Override
    public Withdraw findById(Long id) {
        String hql = "SELECT w FROM Withdraw w WHERE w.isDelete = false AND w.id = :id";
        TypedQuery<Withdraw> query = entityManager.createQuery(hql, Withdraw.class);
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public void save(Withdraw withdraw) {
        if (withdraw.getId() != null) {
            entityManager.merge(withdraw);
        } else  {
            entityManager.persist(withdraw);
        }
    }

    @Override
    public void remove(Long id) {
        Withdraw withdraw = findById(id);
        if (withdraw != null) {
            withdraw.setDelete(true);
            entityManager.merge(withdraw);
        }
    }
}
