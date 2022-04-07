package lpnt.cg.controller;

import lpnt.cg.model.Customer;
import lpnt.cg.model.Withdraw;
import lpnt.cg.repository.ICustomerRepository;
import lpnt.cg.service.customer.ICustomerService;
import lpnt.cg.service.withdraw.IWithdrawService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
public class WithdrawController {

    @Autowired
    private IWithdrawService withdrawService;

    @Autowired
    private ICustomerService customerService;

    @GetMapping("/withdraw/{id}")
    private ModelAndView viewDeposits(@PathVariable Long id) {

        Optional<Customer> customer = customerService.findById(id);
        if (customer.isPresent()) {
            ModelAndView modelAndView = new ModelAndView("/customer/withdraw");
            modelAndView.addObject("withdraw", new Withdraw());
            modelAndView.addObject("customer", customer.get());
            modelAndView.addObject("success", null);
            modelAndView.addObject("error", null);
            return modelAndView;
        } else {
            ModelAndView modelAndView = new ModelAndView("/error.404");
            return modelAndView;
        }
    }

    @PostMapping("/withdraw")
    private ModelAndView saveDeposits(@ModelAttribute("withdraw") Withdraw withdraw) {
        Customer customer = customerService.findById(withdraw.getCustomer().getId()).get();
        long money_withdraw = withdraw.getAmount();
        boolean isMoney = false;
        if (money_withdraw >= 1000) {
            isMoney = true;
        }
        boolean isLimit = false;
        if (money_withdraw <= customer.getBalance()) {
            isLimit = true;
        }

        boolean isTrue = isMoney && isLimit;
        ModelAndView modelAndView = new ModelAndView("/customer/withdraw");
        if (isTrue) {
            withdrawService.save(withdraw);
            customer.setBalance(customer.getBalance() - withdraw.getAmount());
            customerService.save(customer);
            modelAndView.addObject("success", "Withdraw successfully");
        } else {
            modelAndView.addObject("error", "Withdraw error !");
        }
        modelAndView.addObject("withdraw", new Withdraw());
        modelAndView.addObject("customer", customer);

        return modelAndView;
    }
}

