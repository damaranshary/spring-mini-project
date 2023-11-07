package com.prodemy.kelompok3.springminiproject.service;

import com.prodemy.kelompok3.springminiproject.entity.Cart;
import com.prodemy.kelompok3.springminiproject.entity.Order;
import com.prodemy.kelompok3.springminiproject.entity.User;

import java.util.List;

public interface OrderService {

    void addOrder(Cart cart, User user, String deliveryMethod, String paymentMethod);

    List<Order> findOrdersHistoryByUser(User user);

    List<Order> findAllOrder();

    Order findOrderById(String orderId);
}
