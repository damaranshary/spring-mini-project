package com.prodemy.kelompok3.springminiproject.service;

import com.prodemy.kelompok3.springminiproject.entity.Cart;
import com.prodemy.kelompok3.springminiproject.entity.Product;
import com.prodemy.kelompok3.springminiproject.entity.User;

public interface CartService {

    String addProductToCart(Product product, String username, Integer quantity);

    Cart findCartByUsername(String username);

    Cart initializeCartForUser(User user);

    void deleteCartItemById(Long id);
}
