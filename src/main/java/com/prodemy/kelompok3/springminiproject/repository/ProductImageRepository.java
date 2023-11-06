package com.prodemy.kelompok3.springminiproject.repository;

import com.prodemy.kelompok3.springminiproject.entity.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductImageRepository extends JpaRepository<ProductImage, String> {

    @Modifying
    @Query(value = "UPDATE product_images SET data = ?1, name = ?2, type = ?3 WHERE id = ?4", nativeQuery = true)
    void updateProductImageById(byte[] data, String name, String type, String id);
}
