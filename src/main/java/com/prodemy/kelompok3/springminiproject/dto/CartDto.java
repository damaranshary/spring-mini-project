package com.prodemy.kelompok3.springminiproject.dto;

import com.prodemy.kelompok3.springminiproject.entity.Cart;
import lombok.Value;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * DTO for {@link Cart}
 */
@Value
public class CartDto implements Serializable {
    String id;
    List<CartItemDto> cartItems = new ArrayList<>();
    Long totalPrice;
}