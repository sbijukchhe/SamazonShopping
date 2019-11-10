package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Controller
public class HomeController {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @RequestMapping("/about")
    public String getAbout(Model model){
        model.addAttribute("product", new Product());
        return "about";
    }

    @RequestMapping("/aboutus")
    public String aboutUs(Model model){
        model.addAttribute("product", new Product());
        return "aboutus";
    }

    @RequestMapping("/list")
    public String productList(Model model){
        model.addAttribute("products", productRepository.findAll());
//        model.addAttribute("user",userService.getUser());
        if(userService.getUser() != null) {
            model.addAttribute("user_id", userService.getUser().getId());
        }
        return "list";
    }

    @RequestMapping("/add")
    public String addProduct(Model model){
        model.addAttribute("product", new Product());
        return "productform";
    }


    @PostMapping("/processproduct")
    public String processForm(@Valid Product product, BindingResult result){
        if(result.hasErrors()){
            return "productform";
        }
        product.setUser(userService.getUser());
        productRepository.save(product);
        return "redirect:/list";
    }

    @PostMapping("/processsearch")
    public String searchResult(Model model, @RequestParam(name = "search") String search,
                               @RequestParam(name = "category") String category){

        if (category.equals("1")){
            model.addAttribute("products", productRepository.findByNameContainingIgnoreCase(search));
        }
        else if(category.equals("2")){
            model.addAttribute("products", productRepository.findByDescriptionContainingIgnoreCase(search));
        }
        return "searchlist";
    }

    @RequestMapping("/detail/{id}")
    public String showJob(@PathVariable("id") long id, Model model) {
        model.addAttribute("product", productRepository.findById(id).get());
        return "productDetails";
    }

    @RequestMapping("/update/{id}")
    public String updateProduct(@PathVariable("id") long id, Model model) {
        model.addAttribute("product", productRepository.findById(id).get());
        return "productform";
    }

    @RequestMapping("/updateUser/{id}")
    public String updateUser(@PathVariable("id") long id, Model model) {
        model.addAttribute("user", userRepository.findById(id).get());
        return "registration";
    }


    @RequestMapping("/delete/{id}")
    public String deleteJob(@PathVariable("id")long id) {
        productRepository.deleteById(id);
        return "redirect:/list";
    }

    @RequestMapping("/deleteUser/{id}")
    public String deleteUser(@PathVariable("id")long id) {
        userRepository.deleteById(id);
        return "redirect:/userList";
    }

    @RequestMapping("/userlist")
    public String userlist(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "userList";
    }
}