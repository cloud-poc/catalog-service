package org.akj.springboot.catalog.repository;

import java.util.Optional;

import org.akj.springboot.catalog.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepository extends JpaRepository<Product, String> {
	@Query(value="SELECT p FROM Product p where p.code = :code")
	Optional<Product> findByCode(String code);
}