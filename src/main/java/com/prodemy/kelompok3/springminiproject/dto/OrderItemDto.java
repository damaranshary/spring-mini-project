package com.prodemy.kelompok3.springminiproject.dto;

import com.prodemy.kelompok3.springminiproject.entity.OrderItem;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link OrderItem}
 */
@Value
public class OrderItemDto implements Serializable {
    Long id;
    ProductDto product;
    int quantity;
}