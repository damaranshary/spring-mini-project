package com.prodemy.kelompok3.springminiproject.dto;

import com.prodemy.kelompok3.springminiproject.entity.ProductImage;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link ProductImage}
 */
@Value
public class ProductImageDto implements Serializable {
    Long id;
    String name;
    String type;
    byte[] data;
}