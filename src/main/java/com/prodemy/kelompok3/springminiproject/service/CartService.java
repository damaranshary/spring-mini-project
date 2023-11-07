package com.prodemy.kelompok3.springminiproject.service;

import com.prodemy.kelompok3.springminiproject.entity.Cart;
import com.prodemy.kelompok3.springminiproject.entity.Product;
import com.prodemy.kelompok3.springminiproject.entity.User;

public interface CartService {

    void addProductToCart(Product product, User user, Integer quantity);

    Cart findCartByUser(User user);

    Cart initializeCartForUser(User user);

    void deleteCartItemById(Long id);
}
