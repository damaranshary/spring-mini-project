package com.prodemy.kelompok3.springminiproject.service;

import com.prodemy.kelompok3.springminiproject.entity.Cart;
import com.prodemy.kelompok3.springminiproject.entity.CartItem;
import com.prodemy.kelompok3.springminiproject.entity.Product;
import com.prodemy.kelompok3.springminiproject.entity.User;
import com.prodemy.kelompok3.springminiproject.repository.CartItemRepository;
import com.prodemy.kelompok3.springminiproject.repository.CartRepository;
import com.prodemy.kelompok3.springminiproject.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public String addProductToCart(Product product, User user, Integer quantity) {
        Cart cart = cartRepository.findByUser(user);

        CartItem cartItem = cartItemRepository.findCartItemByCart_IdAndProduct_Id(product.getId(), cart.getId());

        if (cartItem != null) {
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
            cartItemRepository.save(cartItem);

            return "produk berhasil ditambahkan";
        }

        CartItem newCartItem = new CartItem();

        newCartItem.setProduct(product);
        newCartItem.setQuantity(quantity);
        newCartItem.setCart(cart);

        cartItemRepository.save(newCartItem);

        if (cart.getTotalPrice() == null) {
            cart.setTotalPrice(product.getPrice() * quantity);
        } else {
            cart.setTotalPrice(cart.getTotalPrice() + (product.getPrice() * quantity));
        }

        cartRepository.save(cart);

        return "produk berhasil ditambahkan";
    }

    @Override
    public Cart initializeCartForUser(User user) {
        Cart cart = new Cart();
        cart.setId(UUID.randomUUID().toString());
        cart.setUser(user);

        return cartRepository.save(cart);
    }

    @Override
    public Cart findCartByUsername(User user) {
        return cartRepository.findByUser(user);
    }


}
