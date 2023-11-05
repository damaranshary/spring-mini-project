package com.prodemy.kelompok3.springminiproject.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.prodemy.kelompok3.springminiproject.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
