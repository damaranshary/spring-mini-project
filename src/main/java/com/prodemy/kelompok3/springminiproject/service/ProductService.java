package com.prodemy.kelompok3.springminiproject.service;

import com.prodemy.kelompok3.springminiproject.dto.ProductDto;
import com.prodemy.kelompok3.springminiproject.entity.Product;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {


    Product addProduct(List<MultipartFile> files, Product product);

    Product findProductById(String productId);

    void updateProduct(List<MultipartFile> files, Product product);

    Product updateProductWithoutImage(Product product);

    List<Product> getAllProduct();

    void deleteProduct(String productId);
}
