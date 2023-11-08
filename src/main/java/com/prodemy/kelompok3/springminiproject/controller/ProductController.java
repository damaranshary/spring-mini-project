package com.prodemy.kelompok3.springminiproject.controller;

import com.prodemy.kelompok3.springminiproject.entity.CartItem;
import com.prodemy.kelompok3.springminiproject.entity.Product;
import com.prodemy.kelompok3.springminiproject.entity.ProductImage;
import com.prodemy.kelompok3.springminiproject.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Controller
public class ProductController {

    @Autowired
    private ProductService productService;


    @GetMapping(path = "/api/images/{index}/{productId}", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> getImage(@PathVariable(name = "index") Integer index, @PathVariable(name = "productId") String productId) {
        List<ProductImage> productImages = productService.findProductById(productId).getImages();

        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(productImages.get(index - 1).getData());
    }

    @GetMapping(path = "/product/{productId}")
    public String getProductById(@PathVariable(name = "productId") String productId, Model model) {
        model.addAttribute("isAdmin", false);
        return getProductByIdWrapper(productId, model);
    }

    @GetMapping(path = "/products")
    public String getAllProduct(Model model) {
        List<Product> productList = productService.findAllProduct();

        model.addAttribute("productList", productList);
        model.addAttribute("isAdmin", false);

        return "productListPage";
    }

    @GetMapping(path = "/products/filter")
    public String getProductUsingFilter(@RequestParam(name = "query", required = false) String query,
                                        @RequestParam(name = "minPrice", required = false) Long minPrice,
                                        @RequestParam(name = "maxPrice", required = false) Long maxPrice, Model model) {
        model.addAttribute("isAdmin", false);
        return productFilterWrapper(query, minPrice, maxPrice, model);
    }


    // this is the borderline between user and admin controller
    @GetMapping(path = "/admin/get/products")
    public String getAllProductFromAdmin(Model model) {
        List<Product> productList = productService.findAllProduct();

        model.addAttribute("productList", productList);
        model.addAttribute("isAdmin", true);

        return "productListPage";
    }


    @GetMapping(path = "/admin/get/products/filter")
    public String getProductUsingFilterFromAdmin(@RequestParam(name = "query", required = false) String query,
                                                 @RequestParam(name = "minPrice", required = false) Long minPrice,
                                                 @RequestParam(name = "maxPrice", required = false) Long maxPrice, Model model) {
        model.addAttribute("isAdmin", true);
        return productFilterWrapper(query, minPrice, maxPrice, model);
    }

    @GetMapping(path = "/admin/get/product/{productId}")
    public String getProductByIdAdmin(@PathVariable(name = "productId") String productId, Model model) {
        model.addAttribute("isAdmin", true);
        return getProductByIdWrapper(productId, model);
    }

    @GetMapping(path = "/admin/add/product")
    public String addProductPage(Model model) {
        Product product = new Product();

        model.addAttribute("product", product);

        return "addProductPage";
    }

    @PostMapping(path = "/admin/add/product", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String addProduct(@RequestParam(name = "name") String name, @RequestParam(name = "description") String description, @RequestParam(name = "price") Long price, @RequestPart(name = "images") List<MultipartFile> images) {
        Product product = new Product();
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);

        product = productService.addProduct(images, product);

        return "redirect:/admin/get/product/" + product.getId();
    }

    @GetMapping(path = "/admin/update/product/{productId}")
    public String updateProductPage(@PathVariable(name = "productId") String productId, Model model) {
        if (productId == null) {
            return "notFound404Page";
        }
        Product product = productService.findProductById(productId);

        model.addAttribute("product", product);

        return "updateProductPage";
    }

    @PostMapping(path = "/admin/update/product/{productId}")
    public String updateProduct(@PathVariable(name = "productId") String productId,
                                @RequestParam(name = "name") String name,
                                @RequestParam(name = "description") String description,
                                @RequestParam(name = "price") Long price,
                                @RequestPart(name = "images", required = false) List<MultipartFile> images) throws IOException {
        Product product = new Product();
        product.setId(productId);
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);

        if (Objects.equals(images.get(0).getOriginalFilename(), "")) {
            productService.updateProductWithoutImage(product);
        } else {
            productService.updateProduct(images, product);
        }

        return "redirect:/admin/update/product/" + productId;
    }

    @GetMapping(path = "/admin/delete/product/{productId}")
    public String deleteProduct(@PathVariable(name = "productId") String productId) {
        productService.deleteProduct(productId);

        return "redirect:/admin/get/products";
    }

    //this is a wrapper method
    private String getProductByIdWrapper(@PathVariable(name = "productId") String productId, Model model) {
        Product product = productService.findProductById(productId);

        CartItem cartItem = new CartItem();
        cartItem.setProduct(product);
        cartItem.setQuantity(1);

        model.addAttribute("product", product);
        model.addAttribute("cartItem", cartItem);

        return "detailsProductPage";
    }

    private String productFilterWrapper(@RequestParam(name = "query", required = false) String query, @RequestParam(name = "minPrice", required = false) Long minPrice, @RequestParam(name = "maxPrice", required = false) Long maxPrice, Model model) {
        List<Product> productList;

        if (minPrice != null) {
            minPrice -= 1;
        }

        if (maxPrice != null) {
            maxPrice += 1;
        }

        if (query == null && maxPrice == null && minPrice == null) {
            // without filter
            return "redirect:/products";
        }

        if (minPrice == null && maxPrice == null) {
            // name
            productList = productService.filterProductByName(query);
        } else if (query == null && minPrice == null) {
            // maxPrice
            productList = productService.filterProductByMaxPrice(maxPrice);
        } else if (query == null && maxPrice == null) {
            // minPrice
            productList = productService.filterProductByMinPrice(minPrice);
        } else if (query == null) {
            // minPrice && maxPrice
            productList = productService.filterProductByMinPriceAndMaxPrice(minPrice, maxPrice);
        } else if (minPrice == null) {
            // name && maxPrice
            productList = productService.filterProductByNameAndMaxPrice(query, maxPrice);
        } else if (maxPrice == null) {
            // name && minPrice
            productList = productService.filterProductByNameAndMinPrice(query, minPrice);
        } else {
            // name, minPrice && maxPrice
            productList = productService.filterProductByNameAndMinPriceAndMaxPrice(query, minPrice, maxPrice);
        }

        model.addAttribute("productList", productList);

        return "productListWithFilterPage";
    }
}
