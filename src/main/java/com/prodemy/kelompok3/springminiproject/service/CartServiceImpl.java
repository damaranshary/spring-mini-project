package com.prodemy.kelompok3.springminiproject.service;

import com.prodemy.kelompok3.springminiproject.entity.Cart;
import com.prodemy.kelompok3.springminiproject.entity.CartItem;
import com.prodemy.kelompok3.springminiproject.entity.Product;
import com.prodemy.kelompok3.springminiproject.entity.User;
import com.prodemy.kelompok3.springminiproject.repository.CartItemRepository;
import com.prodemy.kelompok3.springminiproject.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Override
    public void addProductToCart(Product product, User user, Integer quantity) {
        Cart cart = cartRepository.findCartByUser(user);

        if (cart == null) {
            initializeCartForUser(user);
        }

        CartItem cartItem = cartItemRepository.findCartItemByCart_IdAndProduct_Id(cart.getId(), product.getId());

        if (cartItem != null) {
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
            cartItemRepository.save(cartItem);

        } else {
            CartItem newCartItem = new CartItem();

            newCartItem.setProduct(product);
            newCartItem.setQuantity(quantity);
            newCartItem.setCart(cart);

            cartItemRepository.save(newCartItem);
        }

        if (cart.getTotalPrice() == null) {
            cart.setTotalPrice(product.getPrice() * quantity);
        } else {
            cart.setTotalPrice(cart.getTotalPrice() + (product.getPrice() * quantity));
        }

        cartRepository.save(cart);
    }

    @Override
    public Cart initializeCartForUser(User user) {
        Cart cart = new Cart();
        cart.setId(UUID.randomUUID().toString());
        cart.setUser(user);

        return cartRepository.save(cart);
    }

    @Override
    public void deleteCartItemById(Long id) {
        CartItem cartItem = cartItemRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "NOT FOUND"));

        cartItemRepository.deleteById(id);

        updateTotalPriceInCartAfterDeleteCartItem(cartItem.getCart(), cartItem);
    }

    private void updateTotalPriceInCartAfterDeleteCartItem(Cart cart, CartItem cartItem) {
        long totalPrice = cart.getTotalPrice() - (cartItem.getQuantity() * cartItem.getProduct().getPrice());

        if (totalPrice == 0) {
            cart.setTotalPrice(null);
        } else {
            cart.setTotalPrice(totalPrice);
        }

        cartRepository.save(cart);
    }

    @Override
    public Cart findCartByUser(User user) {
        return cartRepository.findCartByUser(user);
    }
}
