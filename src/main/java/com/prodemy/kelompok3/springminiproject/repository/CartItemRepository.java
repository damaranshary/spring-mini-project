package com.prodemy.kelompok3.springminiproject.repository;

import com.prodemy.kelompok3.springminiproject.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    CartItem findCartItemByCart_IdAndProduct_Id(String cartId, String productId);

    @Modifying
    @Query(value = "UPDATE cart_items SET quantity = ?1 WHERE id = ?2", nativeQuery = true)
    void updateQuantityProductInCartItem(Long quantity, Long id);
}
