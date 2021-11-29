package com.controller;

import com.model.Customer;
import com.service.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private ICustomerService customerService;

    @GetMapping("/create")
    public ModelAndView showCreateForm() {
        ModelAndView modelAndView = new ModelAndView("create");
        modelAndView.addObject("customer", new Customer());
        return modelAndView;
    }

    @PostMapping("/create")
    public ModelAndView saveCustomer(@ModelAttribute("customer") Customer customer) {
        customerService.save(customer);
        ModelAndView modelAndView = new ModelAndView("redirect:/customers");
        modelAndView.addObject("customer", new Customer());
        modelAndView.addObject("message", "New customer created successfully");
        return modelAndView;
    }
    @GetMapping
    public ModelAndView getList(){
        List<Customer> customers = customerService.findAll();
        ModelAndView modelAndView = new ModelAndView("list");
        modelAndView.addObject("customers", customers);
        return modelAndView;
    }
    @GetMapping("/edit/{id}")
    public ModelAndView showFormEdit(@PathVariable long id){
        Customer customer = customerService.findById(id);
        ModelAndView modelAndView;
        if (customer != null){
            modelAndView = new ModelAndView("edit");
            modelAndView.addObject("customer", customer);
        }
        else {
            modelAndView = new ModelAndView("error.404");
        }
        return modelAndView;
    }
    @PostMapping("/edit/{id}")
    public String edit(@ModelAttribute Customer customer, Model model){
        customerService.save(customer);
        model.addAttribute("message", "Customer updated successfully");
        return "redirect:/customers";
    }
//    @GetMapping("/delete/{id}")
//    public ModelAndView showDeleteForm(@PathVariable Long id) {
//        Customer customer = customerService.remove(id);
//        ModelAndView modelAndView;
//        if (customer != null) {
//            modelAndView = new ModelAndView("redirect:customers");
//
//        } else {
//            modelAndView = new ModelAndView("error.404");
//        }
//        return modelAndView;
//    }

    @GetMapping("/delete/{id}")
    public String deleteCustomer(@PathVariable long id) {
        customerService.remove(id);
        return "redirect:customers";
    }
}
