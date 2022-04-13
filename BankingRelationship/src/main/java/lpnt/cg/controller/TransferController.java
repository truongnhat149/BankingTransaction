package lpnt.cg.controller;

import lpnt.cg.model.Customer;
import lpnt.cg.model.Transfer;
import lpnt.cg.service.customer.ICustomerService;
import lpnt.cg.service.transfer.ITransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Controller

public class TransferController {

    @Autowired
    private ITransferService transferService;

    @Autowired
    private ICustomerService customerService;


    @PostMapping("/transfers/{senderId}")
    private ModelAndView doTransfer(@PathVariable Long senderId,@Validated @ModelAttribute Transfer transfer,
                                    BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView("/transaction/transfer");
        Optional<Customer> sender = customerService.findById(senderId);

        if (sender.isPresent()) {
            List<Customer> recipients = (List<Customer>) customerService.findAllByIdIsSuspended(senderId);
            if(!transfer.getRecipient().getId().equals(sender.get().getId())) {
                Optional<Customer> recipientOptional = customerService.findById(transfer.getRecipient().getId());
                if (recipientOptional.isPresent()) {
                    long senderBalance = sender.get().getBalance();
                    long recipientBalance = recipientOptional.get().getBalance();
                    long transferAmount = transfer.getTransferAmount();
                    long fees = 10;
                    long feesAmount = transferAmount / fees;
                    long transactionAmount = transferAmount + feesAmount;

                   boolean isMoney = transferAmount >= 1000 && transferAmount < 10000000000L;
                   boolean isLimit = senderBalance - transactionAmount > 0;

                   String error = null;

                   if (bindingResult.hasFieldErrors()) {
                       List<ObjectError> errorList = bindingResult.getAllErrors();
                       error = "Transfer error \n";
                       for (int i = 0; i < errorList.size() ; i++) {
                           error += "***" + errorList.get(i).getDefaultMessage() + "\n";
                       }
                       modelAndView.addObject("error", error);
                   }
                   try {
                       if (isMoney && isLimit) {
                           sender.get().setBalance(senderBalance - transactionAmount);
                           customerService.save(sender.get());

                           recipientOptional.get().setBalance(recipientBalance + transactionAmount);
                           customerService.save(recipientOptional.get());

                           transfer.setFees(fees);
                           transfer.setFeesAmount(feesAmount);
                           transfer.setTransactionAmount(transactionAmount);
                           transferService.save(transfer);
                           modelAndView.addObject("success", "Transfer successfully");
                           modelAndView.addObject("transfer", new Transfer());
                           modelAndView.addObject("sender", sender.get());
                           modelAndView.addObject("recipients", recipients);
                           return modelAndView;
                       }
                   } catch (Exception e) {

                   }
                    modelAndView.addObject("error", error);
                    modelAndView.addObject("transfer", new Transfer());
                    modelAndView.addObject("sender", sender.get());
                    modelAndView.addObject("recipients", recipients);
                    return modelAndView;
            }

            }
        } else {
            modelAndView.setViewName("/error");
        }
        return modelAndView;
    }

    @GetMapping("/transfers/{id}")
    public ModelAndView viewTransferCustom(@PathVariable Long id) {
        Optional<Customer> sender = customerService.findById(id);

        Iterable<Customer> recipients = customerService.findAllByIdIsSuspended(id);
//        Iterable<Customer> customers = customerService.findAll() ;

        if (sender.isPresent()) {
            ModelAndView modelAndView = new ModelAndView("/transaction/transfer");
            modelAndView.addObject("success", null);
            modelAndView.addObject("error", null);
            modelAndView.addObject("transfer", new Transfer());
            modelAndView.addObject("sender", sender.get());
            modelAndView.addObject("recipients", recipients);
            return modelAndView;
        } else {
            ModelAndView modelAndView = new ModelAndView("/error");
            return modelAndView;
        }
    }
    @GetMapping("/history-transfers")
    public ModelAndView showListTransfer() {
        ModelAndView modelAndView = new ModelAndView("/transaction/transfer-list");
        Iterable<Transfer> transfers = transferService.findAll();
        long total = 0;
        for (Transfer transfer : transfers) {
                total += transfer.getFeesAmount();
        }
        modelAndView.addObject("transfers", transfers);
        modelAndView.addObject("total", total);
        return modelAndView;
    }
}
