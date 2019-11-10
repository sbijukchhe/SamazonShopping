
package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ShoppingCartController {


    @Autowired
    ProductRepository productRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Autowired
    OrderRepository orderRepository;


    private  final SimpleEmailService simpleEmailService;

    private final ShoppingCartService shoppingCartService;

    private final ProductService productService;

    @Autowired
    public ShoppingCartController(ShoppingCartService shoppingCartService, ProductService productService,  SimpleEmailService simpleEmailService ) {
        this.shoppingCartService = shoppingCartService;
        this.productService = productService;
        this.simpleEmailService = simpleEmailService;
    }

    //    @GetMapping("/addtoCart")
    @GetMapping("/shoppingCart")
    public ModelAndView shoppingCart() {
        ModelAndView modelAndView = new ModelAndView("/cartlist");
        modelAndView.addObject("products", shoppingCartService.getProductsInCart());
        modelAndView.addObject("total", shoppingCartService.getTotal().toString());
        modelAndView.addObject("shippingCost", shoppingCartService.getShipping().toString());
        modelAndView.addObject("grandTotal", shoppingCartService.getGrandTotal().toString());

        return modelAndView;
    }

    @GetMapping("/addtoCart/{productId}")
    public ModelAndView addProductToCart(@PathVariable("productId") Long productId, Model model) {
        productService.findById(productId).ifPresent(shoppingCartService::addProduct);
        return shoppingCart();
    }

    @GetMapping("/removeProduct/{productId}")
    public ModelAndView removeProductFromCart(@PathVariable("productId") Long productId) {
        productService.findById(productId).ifPresent(shoppingCartService::removeProduct);
        return shoppingCart();
    }


    @RequestMapping("/checkout")
    public String checkout(Model model){
        try {
            simpleEmailService.sendEmail();
        }
        catch(Exception e){
            return "Error in sending email: "+e;
        }
        model.addAttribute("total",   shoppingCartService.getTotal().toString());
        model.addAttribute("shippingCost",shoppingCartService.getShipping().toString());
        model.addAttribute("grandTotal", shoppingCartService.getGrandTotal().toString());
        return "checkoutlist";
    }


    @RequestMapping("/history")
    public String getHistory(Model model){
        model.addAttribute("products", productRepository.findAllByOrderByUser());
        model.addAttribute("user",userService.getUser());
        return "historyList";
    }
    /*@GetMapping("checkout")
    public ModelAndView checkout() {
        try {
            shoppingCartService.checkout();
        } catch (NotEnoughProductsInStockException e) {
            return shoppingCart().addObject("outOfStockMessage", e.getMessage());
        }
        return shoppingCart();
    }
*/

}