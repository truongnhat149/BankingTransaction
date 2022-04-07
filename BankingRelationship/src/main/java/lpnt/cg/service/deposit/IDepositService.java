package lpnt.cg.service.deposit;

import lpnt.cg.model.Customer;
import lpnt.cg.model.Deposit;
import lpnt.cg.service.IGeneralService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IDepositService extends IGeneralService<Deposit> {

//    Page<Deposit> findAll(Pageable pageable);

    List<Deposit> findAllNotId(Long id);
}
