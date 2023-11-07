package com.prodemy.kelompok3.springminiproject.service;

import com.prodemy.kelompok3.springminiproject.entity.Product;
import com.prodemy.kelompok3.springminiproject.repository.ProductRepository;
import com.prodemy.kelompok3.springminiproject.service.ProductImageService;
import com.prodemy.kelompok3.springminiproject.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductImageService productImageService;

    @Override
    public Product addProduct(List<MultipartFile> images, Product product) {
        product.setId(UUID.randomUUID().toString());
        product.setImages(productImageService.addProductImages(images, product));

        productRepository.save(product);

        return product;
    }

    @Override
    public List<Product> findAllProduct() {
        return productRepository.findAll();
    }

    @Override
    public Product findProductById(String productId) {
        return productRepository.findById(productId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "product not found"));
    }

    @Override
    public List<Product> filterProductByName(String productName) {
        return productRepository.findAllByNameContainingIgnoreCase(productName);
    }

    @Override
    public List<Product> filterProductByMinPrice(Long minPrice) {
        return productRepository.findAllByPriceAfter(minPrice);
    }

    @Override
    public List<Product> filterProductByMaxPrice(Long maxPrice) {
        return productRepository.findAllByPriceBefore(maxPrice);
    }

    @Override
    public List<Product> filterProductByMinPriceAndMaxPrice(Long minPrice, Long maxPrice) {
        return productRepository.findAllByPriceAfterAndPriceBefore(minPrice, maxPrice);
    }

    @Override
    public List<Product> filterProductByNameAndMinPrice(String productName, Long minPrice) {
        return productRepository.findAllByNameContainingIgnoreCaseAndPriceAfter(productName, minPrice);
    }

    @Override
    public List<Product> filterProductByNameAndMaxPrice(String productName, Long maxPrice) {
        return productRepository.findAllByNameContainingIgnoreCaseAndPriceBefore(productName, maxPrice);
    }

    @Override
    public List<Product> filterProductByNameAndMinPriceAndMaxPrice(String productName, Long minPrice, Long maxPrice) {
        return productRepository.findAllByNameContainingIgnoreCaseAndPriceAfterAndPriceBefore(productName, minPrice, maxPrice);
    }

    @Transactional
    @Override
    public void updateProduct(List<MultipartFile> files, Product product) throws IOException {
        Product productImagesBeforeUpdate = productRepository.findById(product.getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "NOT FOUND"));

        if (files.size() > productImagesBeforeUpdate.getImages().size()) {
            throw new IOException("You upload images more or less than you should");
        }

        int i = 0;
        for (MultipartFile file : files) {
            productImageService.updateImage(file, productImagesBeforeUpdate.getImages().get(i).getId());
            i++;
        }

        productRepository.updateProductById(product.getName(), product.getDescription(), product.getPrice(), product.getId());
    }

    @Transactional
    @Override
    public void updateProductWithoutImage(Product product) {
        productRepository.updateProductById(product.getName(), product.getDescription(), product.getPrice(), product.getId());
    }

    @Override
    public void deleteProduct(String productId) {
        productRepository.deleteById(productId);
    }
}
