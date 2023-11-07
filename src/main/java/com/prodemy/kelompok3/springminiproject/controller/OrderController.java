package com.prodemy.kelompok3.springminiproject.controller;

import com.prodemy.kelompok3.springminiproject.entity.Cart;
import com.prodemy.kelompok3.springminiproject.entity.Order;
import com.prodemy.kelompok3.springminiproject.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/order/add")
    public String addOrder(@ModelAttribute(name = "cart") Cart cart,
                           @RequestParam(name = "deliveryMethod") String deliveryMethod,
                           @RequestParam(name = "paymentMethod") String paymentMethod) {
        // we add temporary username to get a user
        String username = "damaranshary";

        orderService.addOrder(cart, username, deliveryMethod, paymentMethod);

        return "redirect:/order";
    }

    @GetMapping("/order")
    public String getOrderHistoryFromUser(Model model) {
        // get order from temporary user
        List<Order> orderList = orderService.findOrdersHistoryByUsername("damaranshary");

        model.addAttribute("orderList", orderList);

        return "orderPage";
    }

    @GetMapping("/order/{orderId}")
    public String getOrderDetailsById(@PathVariable(name = "orderId") String orderId, Model model){
        Order order = orderService.findOrderById(orderId);

        model.addAttribute("order", order);

        return "detailsOrderPage";
    }

    @PostMapping("/checkout")
    public String checkoutPage(@ModelAttribute(name = "cart") Cart cart,
                               Model model) {
        model.addAttribute("cart", cart);

        model.addAttribute("deliveryMethod", null);
        model.addAttribute("paymentMethod", null);

        return "checkoutPage";
    }
}
