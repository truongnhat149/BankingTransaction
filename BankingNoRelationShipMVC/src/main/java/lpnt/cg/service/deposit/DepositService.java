package lpnt.cg.service.deposit;

import lpnt.cg.model.Deposit;
import lpnt.cg.repository.deposit.IDepositRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class DepositService implements IDepositService{

    @Autowired
    private IDepositRepository depositRepository;

    @Override
    public List<Deposit> findAll() {
        return depositRepository.findAll();
    }

    @Override
    public Deposit findById(Long id) {
        return depositRepository.findById(id);
    }

    @Override
    public void save(Deposit deposit) {
        depositRepository.save(deposit);
    }

    @Override
    public void remove(Long id) {
        depositRepository.remove(id);
    }
}
