package org.akj.springboot.catalog.repository;

import java.util.Optional;

import org.akj.springboot.catalog.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, String> {
	Optional<Product> findByCode(String code);
}