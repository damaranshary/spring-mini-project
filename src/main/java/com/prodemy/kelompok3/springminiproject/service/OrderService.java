package com.prodemy.kelompok3.springminiproject.service;

import com.prodemy.kelompok3.springminiproject.entity.Cart;
import com.prodemy.kelompok3.springminiproject.entity.Order;

import java.util.List;

public interface OrderService {

    void addOrder(Cart cart, String username, String deliveryMethod, String paymentMethod);

    List<Order> findOrdersHistoryByUsername(String username);

    Order findOrderById(String orderId);
}
