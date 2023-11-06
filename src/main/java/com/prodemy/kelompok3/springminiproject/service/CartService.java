package com.prodemy.kelompok3.springminiproject.service;

import com.prodemy.kelompok3.springminiproject.entity.Cart;
import com.prodemy.kelompok3.springminiproject.entity.Product;
import com.prodemy.kelompok3.springminiproject.entity.User;

public interface CartService {

    String addProductToCart(Product product, User user, Integer quantity);

    Cart findCartByUsername(User user);

    Cart initializeCartForUser(User user);
}
