package com.prodemy.kelompok3.springminiproject.service;

import com.prodemy.kelompok3.springminiproject.entity.Product;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProductService {


    Product addProduct(List<MultipartFile> files, Product product);

    Product findProductById(String productId);

    void updateProduct(List<MultipartFile> files, Product product) throws IOException;

    void updateProductWithoutImage(Product product);

    List<Product> findAllProduct();

    List<Product> filterProductByNameAndMinPriceAndMaxPrice(String productName, Long minPrice, Long maxPrice);

    List<Product> filterProductByName(String productName);

    List<Product> filterProductByMinPrice(Long minPrice);

    List<Product> filterProductByMaxPrice(Long maxPrice);

    List<Product> filterProductByMinPriceAndMaxPrice(Long minPrice, Long maxPrice);

    List<Product> filterProductByNameAndMinPrice(String productName, Long minPrice);

    List<Product> filterProductByNameAndMaxPrice(String productName, Long maxPrice);

    void deleteProduct(String productId);
}
