package lpnt.cg.service.withdraw;

import lpnt.cg.model.Withdraw;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class WithdrawService implements IWithdrawService{

    @Autowired
    private IWithdrawService withdrawService;

    @Override
    public List<Withdraw> findAll() {
        return null;
    }

    @Override
    public Withdraw findById(Long id) {
        return null;
    }

    @Override
    public void save(Withdraw withdraw) {

    }

    @Override
    public void remove(Long id) {

    }
}
