package com.prodemy.kelompok3.springminiproject.dto;

import com.prodemy.kelompok3.springminiproject.entity.Order;
import lombok.Value;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * DTO for {@link Order}
 */
@Value
public class OrderDto implements Serializable {
    String id;
    UserDto user;
    List<OrderItemDto> orderItems = new ArrayList<>();
    Long totalPrice;
    LocalDateTime orderDateTime;
    String deliveryMethod;
    String paymentMethod;
}