package com.prodemy.kelompok3.springminiproject.dto;

import com.prodemy.kelompok3.springminiproject.entity.CartItem;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link CartItem}
 */
@Value
public class CartItemDto implements Serializable {
    Long id;
    ProductDto product;
    int quantity;
}