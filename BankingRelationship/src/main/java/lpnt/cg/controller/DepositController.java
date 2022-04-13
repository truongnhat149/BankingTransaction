package lpnt.cg.controller;

import lpnt.cg.model.Customer;
import lpnt.cg.model.Deposit;
import lpnt.cg.service.customer.ICustomerService;
import lpnt.cg.service.deposit.IDepositService;
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

import java.util.List;
import java.util.Optional;

@Controller
public class DepositController {

    @Autowired
    private ICustomerService customerService;

    @Autowired
    private IDepositService depositService;

    @GetMapping("/deposit/{id}")
    private ModelAndView viewDeposits(@PathVariable Long id) {
        Optional<Customer> customer = customerService.findById(id);

        if (customer.isPresent()) {
            ModelAndView modelAndView = new ModelAndView("/transaction/deposit");
            modelAndView.addObject("deposit", new Deposit());
            modelAndView.addObject("customer", customer.get());
            modelAndView.addObject("success", null);
            modelAndView.addObject("error", null);
            return modelAndView;
        } else {
            ModelAndView modelAndView = new ModelAndView("/error");
            return modelAndView;
        }

    }

    @PostMapping("/deposit/{customerId}")
    private ModelAndView saveDeposits(@PathVariable Long customerId, @Validated @ModelAttribute("deposit") Deposit deposit,
                                      BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView("/transaction/deposit");

        Customer customer = customerService.findById(customerId).get();

//        long money_deposits = deposit.getAmount();

//        boolean isMoney = false;
//
//        if (money_deposits >= 1000 && money_deposits <= 1000000000) {
//            isMoney = true;
//        }



        String error = null;
        if (bindingResult.hasFieldErrors()) {
            List<ObjectError> errorList = bindingResult.getAllErrors();
            error = "Deposit error \n";
            for (int i = 0; i < errorList.size(); i++) {
                error += "***" + errorList.get(i).getDefaultMessage() + "\n";
            }
            modelAndView.addObject("error", error);
        }
        try {
//            if (isMoney) {
                deposit.setCustomer(customer);
                depositService.save(deposit);
                customer.setBalance(customer.getBalance() + deposit.getAmount());
                customerService.save(customer);
                modelAndView.addObject("message", "Deposits successfully");
                modelAndView.addObject("deposit", new Deposit());
                modelAndView.addObject("customer", customer);
//            }
//            else {
//            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            modelAndView.addObject("error", error);
            modelAndView.addObject("deposit", new Deposit());
            modelAndView.addObject("customer", customer);
//            modelAndView.setViewName("/error");
        }
        return modelAndView;
    }
}
