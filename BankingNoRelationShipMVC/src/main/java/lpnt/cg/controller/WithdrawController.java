package lpnt.cg.controller;

import lpnt.cg.model.Customer;
import lpnt.cg.model.Withdraw;
import lpnt.cg.service.customer.ICustomerService;
import lpnt.cg.service.withdraw.IWithdrawService;
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

import javax.validation.Valid;
import java.util.List;


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
    private ModelAndView saveDeposit(@Validated @ModelAttribute Withdraw withdraw,
                                     BindingResult bindingResult) {
        Customer customer = customerService.findById(withdraw.getIdOwner());

        long moneyWithdraw = withdraw.getAmount();

        boolean isMoney = false;
        boolean isLimit = false;

        if (moneyWithdraw >= 1000) {
            isMoney = true;
        }

        if (moneyWithdraw <= customer.getBalance()) {
            isLimit = true;
        }

        boolean flag = isMoney && isLimit;

        ModelAndView modelAndView = new ModelAndView("/transaction/withdraw");
        String error = null;
        if (bindingResult.hasFieldErrors()) {
            List<ObjectError> errorList = bindingResult.getAllErrors();
            error = "Withdraw error \n";
            for (int i = 0; i < errorList.size(); i++) {
                error = "***" + errorList.get(i).getDefaultMessage() + "\n";
            }
            modelAndView.addObject("error", error);
        }
        try {
            if (flag) {
                withdrawService.save(withdraw);
                customer.setBalance(customer.getBalance() - withdraw.getAmount());
                customerService.save(customer);
                modelAndView.addObject("success", "Withdraw Successfully");
            } else {
                modelAndView.addObject("error", error);
            }
        } catch (Exception e) {
            modelAndView.addObject("error", error);
            System.out.println(e.getMessage());
        }

        modelAndView.addObject("withdraw", new Withdraw(withdraw.getIdOwner()));
        modelAndView.addObject("customer", customer);
        return modelAndView;
    }
}
