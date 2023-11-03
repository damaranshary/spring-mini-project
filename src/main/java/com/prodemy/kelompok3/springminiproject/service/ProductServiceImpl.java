package com.prodemy.kelompok3.springminiproject.service;

import com.prodemy.kelompok3.springminiproject.dto.ProductDto;
import com.prodemy.kelompok3.springminiproject.entity.Product;
import com.prodemy.kelompok3.springminiproject.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductImageService productImageService;

    @Override
    public void addProduct(List<MultipartFile> images, Product product) {
        product.setId(UUID.randomUUID().toString());
        product.setImages(productImageService.addImage(images, product));

        productRepository.save(product);
    }

    @Override
    public Product findProductById(String productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "product not found"));
    }

    @Override
    public Product updateProduct(List<MultipartFile> images, Product product) {
        Product productTemp = new Product();

        productTemp.setId(product.getId());
        productTemp.setImages(productImageService.addImage(images, product));

        productRepository.save(productTemp);

        return productTemp;
    }

    @Override
    public List<Product> getAllProduct() {
        return productRepository.findAll();
    }

    @Override
    public void deleteProduct(String productId) {
        productRepository.deleteById(productId);
    }
}
