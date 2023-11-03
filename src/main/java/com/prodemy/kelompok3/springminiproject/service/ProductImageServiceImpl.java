package com.prodemy.kelompok3.springminiproject.service;

import com.prodemy.kelompok3.springminiproject.entity.Product;
import com.prodemy.kelompok3.springminiproject.entity.ProductImage;
import com.prodemy.kelompok3.springminiproject.repository.ProductImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductImageServiceImpl implements ProductImageService {

    @Autowired
    private ProductImageRepository productImageRepository;

    @Override
    public List<ProductImage> addImage(List<MultipartFile> files, Product product) {
        return files.stream().map(multipartFile -> {
            try {
                return uploadImage(multipartFile, product);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).collect(Collectors.toList());
    }

    private ProductImage uploadImage(MultipartFile file, Product product) throws IOException {
        ProductImage image = new ProductImage();

        image.setName(file.getName());
        image.setType(file.getContentType());
        image.setData(file.getBytes());
        image.setProduct(product);

        productImageRepository.save(image);

        return image;
    }
}
