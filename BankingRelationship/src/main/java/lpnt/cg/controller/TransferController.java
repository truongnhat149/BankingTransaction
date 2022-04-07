package lpnt.cg.controller;

import lpnt.cg.model.Customer;
import lpnt.cg.model.Transfer;
import lpnt.cg.service.customer.ICustomerService;
import lpnt.cg.service.transfer.ITransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/transfers")
public class TransferController {

    @Autowired
    private ITransferService transferService;

    @Autowired
    private ICustomerService customerService;

//    @GetMapping("/{id}")
//    private ModelAndView viewTransfer(@PathVariable Long id) {
//        ModelAndView modelAndView = new ModelAndView();
//        Optional<Customer> sender = customerService.findById(id);
//        List<Customer> recipients = customerService.findAllNotId(id);
//
//        if (sender.isPresent()) {
//            modelAndView.setViewName("/transaction/transfer");
//            modelAndView.addObject("success", null);
//            modelAndView.addObject("error", null);
//            modelAndView.addObject("transfer", new Transfer());
//            modelAndView.addObject("sender", sender.get());
//            modelAndView.addObject("recipients", recipients);
//        } else {
//            modelAndView.setViewName("/error");
//        }
//        return modelAndView;
//    }

    @PostMapping("/{senderId}")
    private ModelAndView doTransfer(@PathVariable Long senderId, @ModelAttribute Transfer transfer) {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("/transaction/transfer");

        Optional<Customer> sender = customerService.findById(senderId);

        if (sender.isPresent()) {
            Optional<Customer> recipent = customerService.findById(transfer.getRecipient().getId());

            if (recipent.isPresent()) {
                long senderBalance = sender.get().getBalance();
                long recipientBalance = recipent.get().getBalance();
                long transferAmount = transfer.getTransferAmount();
                long fees = 10;
                long feesAmount = transferAmount / fees;
                long transactionAmount = transferAmount + feesAmount;

                sender.get().setBalance(senderBalance - transactionAmount);
                customerService.save(sender.get());

                recipent.get().setBalance(recipientBalance + transactionAmount);
                customerService.save(recipent.get());

                transfer.setFees(fees);
                transfer.setFeesAmount(feesAmount);
                transfer.setTransactionAmount(transactionAmount);
                transferService.save(transfer);

                List<Customer> recipients = customerService.findAllNotId(senderId);

                modelAndView.addObject("success", "Transfer successfully");
                modelAndView.addObject("error", null);
                modelAndView.addObject("transfer", new Transfer());
                modelAndView.addObject("sender", sender.get());
                modelAndView.addObject("recipients", recipients);
            }
        } else {
            modelAndView.setViewName("/error");
        }
        return modelAndView;
    }

    @GetMapping("/{id}")
    public ModelAndView viewTransferCustom(@PathVariable Long id) {
        Optional<Customer> customerSender = customerService.findById(id);

        List<Customer> customerListRecipient = customerService.findAllNotId(id);
//        Iterable<Customer> customers = customerService.findAll() ;

        if (customerSender.isPresent()) {
            ModelAndView modelAndView = new ModelAndView("/transaction/transfer");
            modelAndView.addObject("success", null);
            modelAndView.addObject("error", null);
            modelAndView.addObject("transfer", new Transfer());
            modelAndView.addObject("customerSender", customerSender.get());
            modelAndView.addObject("customerListRecipient", customerListRecipient);
            return modelAndView;
        } else {
            ModelAndView modelAndView = new ModelAndView("/error.404");
            return modelAndView;
        }
    }
}
