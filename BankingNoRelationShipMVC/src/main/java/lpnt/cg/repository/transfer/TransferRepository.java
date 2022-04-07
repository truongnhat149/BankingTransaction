package lpnt.cg.repository.transfer;

import lpnt.cg.model.Transfer;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
public class TransferRepository implements ITransferRepository{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Transfer> findAll() {
        String hql = "SELECT t FROM Transfer t WHERE t.isDelete = false";
        TypedQuery<Transfer> query = entityManager.createQuery(hql, Transfer.class);
        return query.getResultList();
    }

    @Override
    public Transfer findById(Long id) {
        String hql = "SELECT t FROM Transfer t WHERE t.isDelete = false AND t.id = :id";
        TypedQuery<Transfer> query = entityManager.createQuery(hql, Transfer.class);
        try {
             return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public void save(Transfer transfer) {
        if (transfer.getId() != null){
            entityManager.merge(transfer);
        } else {
            entityManager.persist(transfer);
        }
    }

    @Override
    public void remove(Long id) {
        Transfer transfer = findById(id);
        if (transfer != null) {
            transfer.setDelete(true);
            entityManager.merge(transfer);
        }
    }
}
