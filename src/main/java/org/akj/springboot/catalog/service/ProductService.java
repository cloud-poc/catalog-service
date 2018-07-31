package org.akj.springboot.catalog.service;

import java.util.List;
import java.util.Optional;

import org.akj.springboot.catalog.entity.Product;
import org.akj.springboot.catalog.model.InventoryResponse;
import org.akj.springboot.catalog.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@Service
@Transactional
public class ProductService {
	@Autowired
	private final ProductRepository productRepository;

	@Autowired
	private RestTemplate restTemplate = null;

	public ProductService(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	public List<Product> findAllProducts() {
		return productRepository.findAll();
	}

	public Optional<Product> findProductByCode(String code) {
		Optional<Product> prod = productRepository.findByCode(code);

		if (prod.isPresent()) {
			ResponseEntity<InventoryResponse> itemResponseEntity = restTemplate
					.getForEntity("http://inventory-service/inventory/{code}", InventoryResponse.class, code);

			if (itemResponseEntity.getStatusCode() == HttpStatus.OK) {
				Integer quantity = itemResponseEntity.getBody().getQuantity();
				prod.get().setInStock(quantity > 0);
			} 
		}

		return prod;
	}

	public Product add(Product product) {
		return productRepository.save(product);
	}
}