package com.prodemy.kelompok3.springminiproject.repository;

import com.prodemy.kelompok3.springminiproject.entity.Order;
import com.prodemy.kelompok3.springminiproject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {

    List<Order> findAllByUser(User user);
}