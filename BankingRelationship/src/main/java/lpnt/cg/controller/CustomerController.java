package lpnt.cg.controller;

import lpnt.cg.model.Customer;
import lpnt.cg.service.customer.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Controller
public class CustomerController {

    @Autowired
    private ICustomerService customerService;

    @GetMapping("/customers")
    public ModelAndView listCustomers(@PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC)
                                                  Pageable pageable) {
        Page<Customer> customers = customerService.findAll(pageable);
        ModelAndView modelAndView = new ModelAndView("/customer/list");
        modelAndView.addObject("customers", customers);
        return modelAndView;
    }

    //    @GetMapping("/customers")
//    public ModelAndView listCustomers() {
//        Iterable<Customer> customers = customerService.findAll();
//        ModelAndView modelAndView = new ModelAndView("/customer/list");
//        modelAndView.addObject("customers", customers);
//        return modelAndView;
//    }

    //    @GetMapping("/customers")
//    public ModelAndView listCustomers(@RequestParam("search") Optional<String> search,
//                                      @RequestParam("search_id") Optional<String> search_id,
//                                      @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable
//    ) {
//
//        Page<Customer> customers;
//
//        long num;
////
//        boolean isNum = false;
//        try {
//            num = Long.parseLong(search_id.get());
//            isNum = true;
//        } catch (Exception e) {
//            isNum = false;
//        }
//        if (search_id.isPresent()) {
//            if (isNum) {
//                customers= customerService.findAllById(Long.parseLong(search_id.get()), pageable);
////                customers_bylastname = customerService.findAllByLastNameContaining(search_id.get(), pageable) ;
////                for(Customer c : customers_bylastname){
////                    if(!customers.equals(c)){
////                       customers.toList().add(c) ;
////                    }
////                }
//            } else {
//                customers = customerService.findAllByLastNameContaining(search_id.get(), pageable) ;
//            }
//        } else {
//            customers = customerService.findAll(pageable);
//        }
//
////        if (search.isPresent()) {
////            customers = customers.findAllByFirstNameContaining(search.get(), pageable);
////        } else {
////            customers = customerService.findAll(pageable);
////        }
//        ModelAndView modelAndView = new ModelAndView("/customer/list");
//        modelAndView.addObject("customers", customers);
//        return modelAndView;
//    }

    @GetMapping("/create-customer")
    public ModelAndView showCreateForm() {
        ModelAndView modelAndView = new ModelAndView("/customer/create");
        modelAndView.addObject("customer", new Customer());
        return modelAndView;
    }

    @PostMapping("/create-customer")
    public ModelAndView saveCustomer(@ModelAttribute("customer") Customer customer) {
        customerService.save(customer);
        ModelAndView modelAndView = new ModelAndView("/customer/create");
        modelAndView.addObject("customer", new Customer());
        modelAndView.addObject("message", "New customer created successfully");
        return modelAndView;
    }


    @GetMapping("/edit-customer/{id}")
    public ModelAndView showEditForm(@PathVariable Long id) {
        Optional<Customer> customer = customerService.findById(id);
        if (customer.isPresent()) {
            ModelAndView modelAndView = new ModelAndView("/customer/edit");
            modelAndView.addObject("customer", customer.get());
            return modelAndView;
        } else {
            ModelAndView modelAndView = new ModelAndView("/error");
            return modelAndView;
        }
    }

    @PostMapping("/edit-customer")
    public ModelAndView updateCustomer(@ModelAttribute("customer") Customer customer) {
        customerService.save(customer);
        ModelAndView modelAndView = new ModelAndView("/customer/edit");
        modelAndView.addObject("customer", customer);
        modelAndView.addObject("message", "Customer updated successfully");
        return modelAndView;
    }

    @GetMapping("/delete-customer/{id}")
    public ModelAndView showDeleteForm(@PathVariable Long id) {
        Optional<Customer> customer = customerService.findById(id);
        if (customer.isPresent()) {
            ModelAndView modelAndView = new ModelAndView("/customer/delete");
            modelAndView.addObject("customer", customer.get());
            return modelAndView;

        } else {
            ModelAndView modelAndView = new ModelAndView("/error");
            return modelAndView;
        }
    }

    @PostMapping("/delete-customer")
    public String deleteCustomer(@ModelAttribute("customer") Customer customer) {
        customerService.remove(customer.getId());
        return "redirect:/customers";
    }
}
