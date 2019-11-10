package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.internet.MimeMessage;

@Service
@Transactional
public class SimpleEmailService {

    @Autowired
    private JavaMailSender sender;

    @Autowired
    UserService userService;

    @Autowired
    ShoppingCartService shoppingCartService;

    public void sendEmail() throws Exception{
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

//        helper.setTo("skbijukchhe@gmail.com");
        helper.setTo(userService.getUser().getEmail());
        helper.setSubject("Samazon Shopping Details");
        helper.setText(
                "Dear " + userService.getUser().getFirstName() + ",\n\n"
                        + "Thank you for shopping with Samazon Shopping Site !!! \n\n " +

                        " Your shopping details are : \n\n" +

                        " Total Price: $ " + shoppingCartService.getTotal().toString() +
                        " \n Shipping charge: $ " + shoppingCartService.getShipping().toString() +
                        " \n Grand Total: $ " + shoppingCartService.getGrandTotal().toString() +
                        "\n\n Have a great day and thanks again !\n\n" +
                        " - Samazon Shopping Site"
        );
        sender.send(message);
    }
}