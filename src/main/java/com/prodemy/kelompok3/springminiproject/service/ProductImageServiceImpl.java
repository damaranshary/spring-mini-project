package com.prodemy.kelompok3.springminiproject.service;

import com.prodemy.kelompok3.springminiproject.entity.Product;
import com.prodemy.kelompok3.springminiproject.entity.ProductImage;
import com.prodemy.kelompok3.springminiproject.repository.ProductImageRepository;
import com.prodemy.kelompok3.springminiproject.service.ProductImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductImageServiceImpl implements ProductImageService {

    @Autowired
    private ProductImageRepository productImageRepository;

    @Override
    public List<ProductImage> addProductImages(List<MultipartFile> files, Product product) {
        return files.stream().map(multipartFile -> {
            try {
                return addImage(multipartFile, product);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void updateImage(MultipartFile file, String id) throws IOException {
        productImageRepository.updateProductImageById(file.getBytes(), file.getName(), file.getContentType(), id);
    }

    @Override
    public ProductImage findImageById(String id) {
        return productImageRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "NOT FOUND"));
    }

    private ProductImage addImage(MultipartFile file, Product product) throws IOException {
        ProductImage image = new ProductImage();

        image.setId(UUID.randomUUID().toString());
        image.setName(file.getName());
        image.setType(file.getContentType());
        image.setData(file.getBytes());
        image.setProduct(product);

        productImageRepository.save(image);

        return image;
    }
}
