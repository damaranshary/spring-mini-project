package com.prodemy.kelompok3.springminiproject.repository;

import com.prodemy.kelompok3.springminiproject.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {

    @Modifying
    @Query(value = "UPDATE products SET name = ?1, description = ?2, price = ?3 WHERE id = ?4", nativeQuery = true)
    void updateProductById(String name, String description, Long price, String id);

    List<Product> findAllByNameContainingIgnoreCaseAndPriceAfterAndPriceBefore(String productName, Long minPrice, Long maxPrice);

    List<Product> findAllByNameContainingIgnoreCase(String productName);

    List<Product> findAllByPriceAfter(Long minPrice);

    List<Product> findAllByPriceBefore(Long maxPrice);

    List<Product> findAllByPriceAfterAndPriceBefore(Long minPrice, Long maxPrice);

    List<Product> findAllByNameContainingIgnoreCaseAndPriceAfter(String productName, Long minPrice);

    List<Product> findAllByNameContainingIgnoreCaseAndPriceBefore(String productName, Long maxPrice);

}
