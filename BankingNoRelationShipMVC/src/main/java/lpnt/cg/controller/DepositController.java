package lpnt.cg.controller;

import lpnt.cg.model.Customer;
import lpnt.cg.model.Deposit;
import lpnt.cg.service.customer.ICustomerService;
import lpnt.cg.service.deposit.IDepositService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class DepositController {

    @Autowired
    private ICustomerService customerService;

    @Autowired
    private IDepositService depositService;

    @GetMapping("/deposits/{id}")
    private ModelAndView viewDeposits(@PathVariable Long id){
        ModelAndView modelAndView = new ModelAndView("/transaction/deposits");
        Customer customer = customerService.findById(id);
        modelAndView.addObject("deposits",new Deposit(id));
        modelAndView.addObject("customer",customer);
        return modelAndView ;
    }
    @PostMapping("/deposits")
    private ModelAndView saveDeposits(@ModelAttribute("deposits") Deposit deposits){
        Customer customer = customerService.findById(deposits.getIdOwner());
        long money_deposits = deposits.getAmount();
        boolean isMoney = false;
        if (money_deposits > 1000 && money_deposits <= 1000000000) {
            isMoney = true;
        }
        ModelAndView modelAndView = new ModelAndView("/transaction/deposits");
        if(isMoney){
            depositService.save(deposits);
            customer.setBalance(customer.getBalance()+ deposits.getAmount());
            customerService.save(customer);
            modelAndView.addObject("message", "Deposits successfully");
        }else {
            modelAndView.addObject("error","Deposits error !");
        }
        modelAndView.addObject("deposits", new Deposit(deposits.getIdOwner()));
        modelAndView.addObject("customer",customer);
        return modelAndView ;
    }
}