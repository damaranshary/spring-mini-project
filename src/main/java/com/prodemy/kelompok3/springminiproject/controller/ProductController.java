package com.prodemy.kelompok3.springminiproject.controller;

import com.prodemy.kelompok3.springminiproject.entity.Product;
import com.prodemy.kelompok3.springminiproject.entity.ProductImage;
import com.prodemy.kelompok3.springminiproject.service.ProductImageService;
import com.prodemy.kelompok3.springminiproject.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Controller
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductImageService productImageService;

    @GetMapping(path = "/api/images/1/{productId}", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> getFirstImage(@PathVariable(name = "productId") String productId) {
        List<ProductImage> productImages = productService.findProductById(productId).getImages();

        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(productImages.get(0).getData());
    }

    @GetMapping(path = "/api/images/2/{productId}", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> getSecondImage(@PathVariable(name = "productId") String productId) {
        List<ProductImage> productImages = productService.findProductById(productId).getImages();

        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(productImages.get(1).getData());
    }

    @GetMapping(path = "/api/images/3/{productId}", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> getThirdImage(@PathVariable(name = "productId") String productId) {
        List<ProductImage> productImages = productService.findProductById(productId).getImages();

        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(productImages.get(2).getData());
    }


    @GetMapping(path = "/products/{productId}")
    public String getProductById(@PathVariable(name = "productId") String productId, Model model) {
        Product product = productService.findProductById(productId);

        model.addAttribute("product", product);

        return "productPage";
    }

    @GetMapping(path = "/products")
    public String getAllProduct(Model model) {
        List<Product> productList = productService.getAllProduct();

        model.addAttribute("productList", productList);

        return "allProductPage";
    }

    @GetMapping(path = "/products/add")
    public String addProductPage(Model model) {
        Product product = new Product();

        model.addAttribute("product", product);

        return "addProductPage";
    }

    @PostMapping(path = "/products/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String addProduct(@RequestParam(name = "name") String name, @RequestParam(name = "description") String description, @RequestParam(name = "price") Long price, @RequestPart(name = "images") List<MultipartFile> images) {
        Product product = new Product();
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);

        product = productService.addProduct(images, product);

        return "redirect:/products/" + product.getId();
    }

    @GetMapping(path = "/products/update/{productId}")
    public String updateProductPage(@PathVariable(name = "productId") String productId, Model model) {
        if (productId == null) {
            return "notFound404Page";
        }
        Product product = productService.findProductById(productId);

        model.addAttribute("product", product);

        return "updateProductPage";
    }

    @PostMapping(path = "/products/update/{productId}")
    public String updateProduct(@PathVariable(name = "productId") String productId,
                                @RequestParam(name = "name") String name,
                                @RequestParam(name = "description") String description,
                                @RequestParam(name = "price") Long price,
                                @RequestPart(name = "images", required = false) List<MultipartFile> images, Model model) throws IOException {
        Product product = new Product();
        product.setId(productId);
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);

        if (Objects.equals(images.get(0).getOriginalFilename(), "")) {
            productService.updateProductWithoutImage(product);
        } else {
            productService.updateProduct(images, product);
        };

        return "redirect:/products/" + productId;
    }

    @GetMapping(path = "/products/delete/{productId}")
    public String deleteProduct(@PathVariable(name = "productId") String productId) {
        productService.deleteProduct(productId);

        return "redirect:/products";
    }
}
