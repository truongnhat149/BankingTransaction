package lpnt.cg.repository;

import lpnt.cg.model.Deposit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IDepositRepository extends JpaRepository<Deposit, Long> {

    @Query(value = "SELECT d FROM Deposit AS d WHERE d.id = :id")
    List<Deposit> findAllNotId(@Param("id") Long id);

    Iterable<Deposit> findAllByIdIsNot(Long id);
}
