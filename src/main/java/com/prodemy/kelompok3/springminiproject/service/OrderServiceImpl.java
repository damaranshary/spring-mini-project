package com.prodemy.kelompok3.springminiproject.service;

import com.prodemy.kelompok3.springminiproject.entity.*;
import com.prodemy.kelompok3.springminiproject.repository.CartRepository;
import com.prodemy.kelompok3.springminiproject.repository.OrderItemRepository;
import com.prodemy.kelompok3.springminiproject.repository.OrderRepository;
import com.prodemy.kelompok3.springminiproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private CartRepository cartRepository;

    @Override
    public void addOrder(Cart cart, User user, String deliveryMethod, String paymentMethod) {
        if (user == null) {
            throw  new ResponseStatusException(HttpStatus.NOT_FOUND, "NOT FOUND");
        }

        Order order = new Order();

        order.setId(UUID.randomUUID().toString());
        order.setUser(user);

        order.setTotalPrice(cart.getTotalPrice());
        order.setOrderDateTime(LocalDateTime.now());

        order.setDeliveryMethod(deliveryMethod);
        order.setPaymentMethod(paymentMethod);

        orderRepository.save(order);

        order.setOrderItems(convertCartItemsToOrderItems(cart.getCartItems(), order));

        cartRepository.delete(cartRepository.findCartByUser(user));
    }

    @Override
    public List<Order> findOrdersHistoryByUser(User user) {

        return orderRepository.findAllByUser(user);
    }

    @Override
    public List<Order> findAllOrder() {
        return orderRepository.findAll();
    }

    @Override
    public Order findOrderById(String orderId) {
        return orderRepository.findById(orderId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "NOT FOUND"));
    }

    private List<OrderItem> convertCartItemsToOrderItems(List<CartItem> cartItemList, Order order) {
        return cartItemList.stream().map(cartItem -> addOrderItem(cartItem, order)).collect(Collectors.toList());
    }

    private OrderItem addOrderItem(CartItem cartItem, Order order) {
        OrderItem orderItem = new OrderItem();

        orderItem.setOrder(order);
        orderItem.setProduct(cartItem.getProduct());
        orderItem.setQuantity(cartItem.getQuantity());

        orderItem = orderItemRepository.save(orderItem);

        return orderItem;
    }
}
