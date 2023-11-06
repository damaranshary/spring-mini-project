package com.prodemy.kelompok3.springminiproject.controller;

import com.prodemy.kelompok3.springminiproject.dto.UserDto;
import com.prodemy.kelompok3.springminiproject.entity.Cart;
import com.prodemy.kelompok3.springminiproject.entity.CartItem;
import com.prodemy.kelompok3.springminiproject.entity.Product;
import com.prodemy.kelompok3.springminiproject.entity.User;
import com.prodemy.kelompok3.springminiproject.service.CartService;
import com.prodemy.kelompok3.springminiproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;

@Controller
public class CartController {

    @Autowired
    private UserService userService;

    @Autowired
    private CartService cartService;

    @PostMapping("/cart/add")
    public String addProductToCart(@RequestParam(name = "product") Product product,
                                 @RequestParam(name = "quantity") int quantity) {
        User user = userService.findByUsername("damaranshary");

        String result = cartService.addProductToCart(product, user, quantity);

        System.out.println(result);

        return "redirect:/cart";
    }

    @GetMapping(path = "/cart")
    public String getCart(Model model) {
        User user = userService.findByUsername("damaranshary");
        Cart cart = cartService.findCartByUsername(user);

        List<CartItem> cartItemList = cart.getCartItems();
        Long totalPrice = cart.getTotalPrice();

        model.addAttribute("cartItemList", cartItemList);
        model.addAttribute("totalPrice", totalPrice);

        return "cartPage";
    }
}
