package com.prodemy.kelompok3.springminiproject.controller;

import com.prodemy.kelompok3.springminiproject.entity.Cart;
import com.prodemy.kelompok3.springminiproject.entity.CartItem;
import com.prodemy.kelompok3.springminiproject.entity.Product;
import com.prodemy.kelompok3.springminiproject.entity.User;
import com.prodemy.kelompok3.springminiproject.service.CartService;
import com.prodemy.kelompok3.springminiproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class CartController {

    @Autowired
    private UserService userService;

    @Autowired
    private CartService cartService;

    @PostMapping("/cart/add")
    public String addProductToCart(@RequestParam(name = "product") Product product,
                                   @RequestParam(name = "quantity") int quantity) {
        User user = userService.getCurrentUser();
        Cart cart = user.getCart();

        if (cart == null) {
            cartService.initializeCartForUser(user);
        }

        cartService.addProductToCart(product, user, quantity);

        return "redirect:/cart";
    }

    @GetMapping(path = "/cart")
    public String getCart(Model model) {
        User user = userService.getCurrentUser();
        Cart cart = user.getCart();

        if (cart == null) {
            cartService.initializeCartForUser(user);
        }

        model.addAttribute("cart", cart);

        return "cartPage";
    }

    @GetMapping(path = "/cart/delete/{cartItemId}")
    public String deleteCartItemFromCart(@PathVariable(name = "cartItemId") Long cartItemId) {
        cartService.deleteCartItemById(cartItemId);

        return "redirect:/cart";
    }
}
