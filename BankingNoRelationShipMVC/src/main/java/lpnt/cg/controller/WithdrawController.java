package lpnt.cg.controller;

import lpnt.cg.model.Customer;
import lpnt.cg.model.Withdraw;
import lpnt.cg.service.customer.ICustomerService;
import lpnt.cg.service.withdraw.IWithdrawService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class WithdrawController {

    @Autowired
    private IWithdrawService withdrawService;

    @Autowired
    private ICustomerService customerService;

    @GetMapping("/withdraw/{id}")
    private ModelAndView viewWithdraw(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("/transaction/withdraw");
        Customer customer = customerService.findById(id);
        modelAndView.addObject("withdraw", new Withdraw(id));
        modelAndView.addObject("customer", customer);
        modelAndView.addObject("success", null);
        modelAndView.addObject("error", null);
        return modelAndView;
    }

    @PostMapping("/withdraw")
    private ModelAndView saveDeposit(@ModelAttribute Withdraw withdraw) {
        Customer customer = customerService.findById(withdraw.getIdOwner());

        long moneyWithdraw = withdraw.getAmount();

        boolean isMoney = false;
        boolean isLimit = false;

        if (moneyWithdraw > 1000 && moneyWithdraw <= customer.getBalance()) {
            isMoney = true;
            isLimit = true;
        }

        boolean flag = isMoney && isLimit;

        ModelAndView modelAndView = new ModelAndView("/transaction/withdraw");

        if (flag) {
            withdrawService.save(withdraw);
            customer.setBalance(customer.getBalance() - withdraw.getAmount());
            customerService.save(customer);
            modelAndView.addObject("success", "Withdraw Successfully");
        } else {
            modelAndView.addObject("error", "Withdraw error!");
        }

        modelAndView.addObject("withdraw", new Withdraw(withdraw.getIdOwner()));
        modelAndView.addObject("customer", customer);
        return modelAndView;
    }
}
