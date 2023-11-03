package com.prodemy.kelompok3.springminiproject.controller;

import com.prodemy.kelompok3.springminiproject.entity.Product;
import com.prodemy.kelompok3.springminiproject.entity.ProductImage;
import com.prodemy.kelompok3.springminiproject.service.ProductImageService;
import com.prodemy.kelompok3.springminiproject.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductImageService productImageService;

//    @GetMapping(path = "/api/images/{productId}")
//    public ResponseEntity<byte[]> whatever(@PathVariable(name = "productId") String productId) {
//        List<ProductImage> productImages = productService.findProductById(productId).getImages();
//
//        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(productImages.get(0).getData());
//    }

    @GetMapping(path = "/products/{productId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Product> getProductById(@PathVariable(name = "productId") String productId) {
        Product product = productService.findProductById(productId);

        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(product);
    }

    @GetMapping(
            path = "/products",
            produces = MediaType.APPLICATION_JSON_VALUE
    ) public ResponseEntity<List<Product>> getAllProduct() {
        List<Product> productList = productService.getAllProduct();

        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(productList);
    }

    @PostMapping(
            path = "/products/add",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Product> addProduct(@RequestParam(name = "name") String name,
                                              @RequestParam(name = "description") String description,
                                              @RequestParam(name = "price") Long price, Model model,
                                              @RequestPart(name = "files") List<MultipartFile> images) {
        Product product = new Product();
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);

        productService.addProduct(images, product);

        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(product);
    }

    @PatchMapping(path = "/products/update/{productId}")
    public String updateProduct(@PathVariable(name = "productId") String productId,
                                                @RequestParam(name = "name") String name,
                                                @RequestParam(name = "description") String description,
                                                @RequestParam(name = "price") Long price,
                                                @RequestPart(name = "images") List<MultipartFile> images,
                                                Model model) {
        Product product = new Product();
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);

        if (productService.findProductById(productId) != null) {
            product.setId(productId);
            model.addAttribute("product", productService.updateProduct(images, product));
        } else {
            return "productNotFound";
        }

        return "updatedProduct";
    }

    @DeleteMapping(path = "/products/{productId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteProduct(@PathVariable(name = "productId") String productId) {
        productService.deleteProduct(productId);

        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body("Product " + productId + " has been deleted");
    }
}
