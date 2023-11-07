package com.prodemy.kelompok3.springminiproject.controller;

import com.prodemy.kelompok3.springminiproject.entity.Cart;
import com.prodemy.kelompok3.springminiproject.entity.Order;
import com.prodemy.kelompok3.springminiproject.entity.User;
import com.prodemy.kelompok3.springminiproject.service.OrderService;
import com.prodemy.kelompok3.springminiproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @PostMapping("/order/add")
    public String addOrder(@ModelAttribute(name = "cart") Cart cart,
                           @RequestParam(name = "deliveryMethod") String deliveryMethod,
                           @RequestParam(name = "paymentMethod") String paymentMethod) {
        // we add temporary username to get a user
        User user = userService.getCurrentUser();

        orderService.addOrder(cart, user, deliveryMethod, paymentMethod);

        return "redirect:/order";
    }

    @GetMapping("/order")
    public String getOrderHistoryFromUser(Model model) {
        User user = userService.getCurrentUser();

        List<Order> orderList = orderService.findOrdersHistoryByUser(user);

        model.addAttribute("orderList", orderList);
        model.addAttribute("isAdmin", false);

        return "orderListPage";
    }

    @GetMapping("/order/{orderId}")
    public String getOrderDetailsById(@PathVariable(name = "orderId") String orderId, Model model){
        User user = userService.getCurrentUser();
        Order order = orderService.findOrderById(orderId);

        if (user.getOrders().contains(order)) {
            model.addAttribute("order", order);
            return "detailsOrderPage";
        } else {
            return "notFound404Page";
        }
    }

    @PostMapping("/order/checkout")
    public String checkoutPage(@ModelAttribute(name = "cart") Cart cart,
                               Model model) {
        model.addAttribute("cart", cart);

        model.addAttribute("deliveryMethod", null);
        model.addAttribute("paymentMethod", null);

        return "checkoutPage";
    }

    // this is the borderline between user and admin controller
    @GetMapping("/admin/get/orders")
    public String getOrders(Model model) {
        List<Order> orderList = orderService.findAllOrder();

        model.addAttribute("orderList", orderList);
        model.addAttribute("isAdmin", true);

        return "orderListPage";
    }

    @GetMapping("/admin/get/order/{orderId}")
    public String getOrderById(@PathVariable("orderId") String orderId, Model model){
        Order order = orderService.findOrderById(orderId);
        model.addAttribute("order", order);

        return "detailsOrderPage";
    }
}
