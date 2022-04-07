package lpnt.cg.service.transfer;

import lpnt.cg.model.Transfer;
import lpnt.cg.repository.ITransferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TransferService implements ITransferService {

    @Autowired
    private ITransferRepository transferRepository;

    @Override
    public Iterable<Transfer> findAll() {
        return transferRepository.findAll();
    }

    @Override
    public Optional<Transfer> findById(Long id) {
        return transferRepository.findById(id);
    }

    @Override
    public void save(Transfer transfer) {
        transferRepository.save(transfer);
    }

    @Override
    public void remove(Long id) {
        Transfer transfer = findById(id).get();
        transfer.setSuspended(true);
        transferRepository.save(transfer);
    }
}
