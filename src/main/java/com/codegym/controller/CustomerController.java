package com.codegym.controller;

import com.codegym.model.Customer;
import com.codegym.service.CustomerService;
import com.codegym.service.ICustomerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller                                  // @Controller giúp Spring xác định lớp hiện tại là một Controller
@RequestMapping("/customer")
public class CustomerController {
    private final ICustomerService customerService = new CustomerService();

    @GetMapping("")
    //@GetMapping xác định phương thức Index sẽ đón nhận các request có HTTP method là GET và URI pattern là "/"
    public String index(Model model) {
        List<Customer> customerList = customerService.findAll();
        model.addAttribute("customers", customerList);
        // addAttribute(): tên biến đại diện cho danh sách mà ta sẽ dùng ở View sau này.
        return "/index";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("customer", new Customer());
        return "/create";
    }

    @PostMapping("/save")
    public String save(Customer customer) {
        customer.setId((int) (Math.random() * 10000));
        //customer.setId() sẽ tạo Id ngẫu nhiên cho đối tượng customer.
        customerService.save(customer);
        return "redirect:/customer";
        //đằng sau "redirect:" là đường dẫn của trang mà mình muốn redirect
    }
    @GetMapping("/{id}/edit")
    public String edit(@PathVariable int id, Model model) {
        // @PathVariable int id lấy id của customer từ đường dẫn rồi gán vào biến id.
        model.addAttribute("customer", customerService.findById(id));
//        customerService.findById(id) sẽ lấy customer theo id rồi truyền sang view edit.html
        return "/edit";
    }
    @PostMapping("/update")
    public String update(Customer customer) {
        customerService.update(customer.getId(), customer);
        return "redirect:/customer";
    }
    @GetMapping("/{id}/delete")
    public String delete(@PathVariable int id, Model model) {
        model.addAttribute("customer", customerService.findById(id));
        return "/delete";
    }
    @PostMapping("/delete")
    public String delete(Customer customer, RedirectAttributes redirect) {
        customerService.remove(customer.getId());
        redirect.addFlashAttribute("success", "Removed customer successfully!");
        return "redirect:/customer";
    }
    @GetMapping("/{id}/view")
    public String view(@PathVariable int id, Model model) {
        model.addAttribute("customer", customerService.findById(id));
        return "/view";
    }
}