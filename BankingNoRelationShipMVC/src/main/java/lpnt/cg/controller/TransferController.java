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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Controller
public class TransferController {

    @Autowired
    private ITransferService transferService;

    @Autowired
    private ICustomerService customerService;

    @GetMapping("/transfer/{id}")
    private ModelAndView viewTransfer(@PathVariable Long id
    ) {
        ModelAndView modelAndView = new ModelAndView("/transaction/transfer");
        Customer customer_sender = customerService.findById(id);
        List<Customer> customerList = customerService.findAll();
        List<Customer> customerListRecipient = new ArrayList<>();

        for (Customer c : customerList) {
            if (!Objects.equals(c.getId(), customer_sender.getId())) {
                customerListRecipient.add(c);
            }
        }
        try {
            modelAndView.addObject("success", null);
            modelAndView.addObject("error", null);
            modelAndView.addObject("transfer", new Transfer(id));
            modelAndView.addObject("customer_sender", customer_sender);
            modelAndView.addObject("customersList", customerListRecipient);
        } catch (Exception e) {
            modelAndView.setViewName("/error");
            System.out.println("Bug" + e.getMessage());
        }
        return modelAndView;
    }

    @GetMapping("/history-transfers")
    public ModelAndView showListTransfers() {
        ModelAndView modelAndView = new ModelAndView("/transaction/transfers_list");
        List<Customer> customersList = customerService.findAll();
        List<Transfer> transfers = transferService.findAll();
        long total = 0;
        for (Transfer t : transfers) {
            total += t.getTransaction_amount();
        }
        modelAndView.addObject("transfers", transfers);
        modelAndView.addObject("total", total);
        modelAndView.addObject("customersList", customersList);
        return modelAndView;
    }

    @PostMapping("/transfer")
    private ModelAndView saveTransfers(@Validated @ModelAttribute("transfer") Transfer transfer,
                                      BindingResult bindingResult) {

        long transaction_amount = transfer.getTransferAmount() / transfer.getFees();
        transfer.setTransaction_amount(transaction_amount);
        Customer customer_sender = customerService.findById(transfer.getIdSender());
        Long id_sender = customer_sender.getId();
        Customer customer_recipient = customerService.findById(transfer.getIdRecipient());
        long totalMoneyTransfer = transfer.getTransferAmount() + transfer.getTransaction_amount();
        long balance_sender = customer_sender.getBalance();
        List<Customer> customersListAll = customerService.findAll();
        List<Customer> customerListRecipient = new ArrayList<>();
        for (Customer c : customersListAll) {
            if (!c.getId().equals(customer_sender.getId())) {
                customerListRecipient.add(c);
            }
        }
        ModelAndView modelAndView = new ModelAndView("/transaction/transfer");

        boolean isTrue = false;
        boolean isLimit = balance_sender >= totalMoneyTransfer;
        boolean isMoney = transfer.getTransferAmount() >= 1000 && transfer.getTransferAmount() < 100000L * 100000;
        boolean isIdRecipient = customer_recipient != null;
        boolean isNotnull = transfer.getTransferAmount() != 0;

        isTrue = isLimit && isMoney && isIdRecipient && isNotnull;
        String error = null;
        if (bindingResult.hasFieldErrors()) {
            List<ObjectError> errorList = bindingResult.getAllErrors();
            error = "new customer create error \n";
            for (int i = 0; i < errorList.size(); i++) {
                error += "***" + errorList.get(i).getDefaultMessage() + "\n";
            }
        }
        try {
            if (isTrue) {
                transferService.save(transfer);
                customer_sender.setBalance(customer_sender.getBalance() - transfer.getTransaction_amount() - transfer.getTransferAmount());
                customer_recipient.setBalance(customer_recipient.getBalance() + transfer.getTransferAmount());
                customerService.save(customer_sender);
                customerService.save(customer_recipient);
                modelAndView.addObject("success", "Transfer successfully");
            } else {
                modelAndView.addObject("error", error);
            }
            modelAndView.addObject("transfer", new Transfer(id_sender));
            modelAndView.addObject("customer_sender", customer_sender);
            modelAndView.addObject("customersList", customerListRecipient);
            return modelAndView;
        } catch (Exception e) {
            modelAndView.setViewName("/error");
            System.out.println(e.getMessage());
        }
        return modelAndView;
    }
//    @PostMapping("/transfer")
//    private ModelAndView saveDeposits(@Validated @ModelAttribute("transfer") Transfer transfer, BindingResult bindingResult) throws Exception {
//
//        long transaction_amount = transfer.getTransferAmount() / transfer.getFees();
//        transfer.setTransaction_amount(transaction_amount);
//
//        Customer customer_sender = customerService.findById(transfer.getIdSender());
//        Long id_sender = customer_sender.getId();
//
//        Customer customer_recipient = customerService.findById(transfer.getIdRecipient());
//        long totalMoneyTransfer = transfer.getTransferAmount() + transfer.getTransaction_amount();
//        long balance_sender = customer_sender.getBalance();
//        List<Customer> customersListAll = customerService.findAll();
//        List<Customer> customerListRecipient = new ArrayList<>();
//        for (Customer c : customersListAll) {
//            if (!c.getId().equals(customer_sender.getId())) {
//                customerListRecipient.add(c);
//            }
//        }
//
//        ModelAndView modelAndView = new ModelAndView("/transaction/transfer");
//
//        boolean isTrue;
//        boolean isLimit = false;
//
//        if (balance_sender >= totalMoneyTransfer) {
//            isLimit = true;
//        }
////        else {
////            modelAndView.addObject("error", " Transfer amount is not enough !");
////        }
//
//        boolean isMoney = false;
//
//        if (transfer.getTransferAmount() >= 1000 && transfer.getTransferAmount() < 100000L * 100000) {
//            isMoney = true;
//        }
////        else {
////            modelAndView.addObject("error", "Money transfer limit must be from");
////        }
//
//        boolean isIdRecipient = false;
//
//        if (customer_recipient != null) {
//            isIdRecipient = true;
//        }
////        else {
////            modelAndView.addObject("error", "recipient id not available");
////        }
////        boolean isNotnull = false ;
////        if(transfer.getTransferAmount() != 0){
////            isNotnull = true;
////        }
//
//        isTrue = isLimit && isMoney && isIdRecipient;
//        String error = null;
//        if (!bindingResult.hasFieldErrors()) {
//            List<ObjectError> errorList = bindingResult.getAllErrors();
//            error = "New customer create error \n";
//            for (int i = 0; i < errorList.size(); i++) {
//                error += "*" + errorList.get(i).getDefaultMessage() + "\n";
//            }
//            modelAndView.addObject("error", error);
//        }
//        try {
//            if (isTrue) {
//                transferService.save(transfer);
//                customer_sender.setBalance(customer_sender.getBalance() - transfer.getTransaction_amount() - transfer.getTransferAmount());
//                customer_recipient.setBalance(customer_recipient.getBalance() + transfer.getTransferAmount());
//                customerService.save(customer_sender);
//                customerService.save(customer_recipient);
//                modelAndView.addObject("success", "Transfer successfully");
//
//            } else {
//                modelAndView.addObject("error", error);
//            }
//        } catch (Exception e) {
//            modelAndView.addObject("error", error);
//            modelAndView.addObject("transfer", new Transfer(id_sender));
//            modelAndView.addObject("customer_sender", customer_sender);
//            modelAndView.addObject("customersList", customerListRecipient);
//        }
//        return modelAndView;
//    }


}
