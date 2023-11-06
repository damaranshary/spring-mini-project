package com.prodemy.kelompok3.springminiproject.service;

import com.prodemy.kelompok3.springminiproject.entity.Product;
import com.prodemy.kelompok3.springminiproject.entity.ProductImage;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProductImageService {

    List<ProductImage> addImage(List<MultipartFile> files, Product product);

    void updateImage(MultipartFile file, String id) throws IOException;

    ProductImage findImageById(String id);
}
