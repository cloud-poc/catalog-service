package org.akj.springboot.catalog.service;

import java.util.List;
import java.util.Optional;

import org.akj.springboot.catalog.client.feign.InventoryServiceClient;
import org.akj.springboot.catalog.entity.Product;
import org.akj.springboot.catalog.model.InventoryResponse;
import org.akj.springboot.catalog.repository.ProductRepository;
import org.akj.springboot.common.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {
	@Autowired
	private final ProductRepository productRepository;

//	@Autowired
//	private RestTemplate restTemplate = null;

//	@Autowired
//	private InventoryServiceClient client = null;
	@Autowired
	private InventoryServiceClient client;

	public ProductService(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	public List<Product> findAllProducts() {
		return productRepository.findAll();
	}

	public Optional<Product> findProductByCode(String code) {
		Optional<Product> prod = productRepository.findByCode(code);

		if (prod.isPresent()) {
//			ResponseEntity<InventoryResponse> itemResponseEntity = restTemplate
//					.getForEntity("http://inventory-service/inventory/{code}", InventoryResponse.class, code);

			Optional<InventoryResponse> itemResponseEntity = client.getProductInventoryByCode(code);
			if (itemResponseEntity.isPresent()) {
				Integer quantity = itemResponseEntity.get().getQuantity();
				prod.get().setInStock(quantity > 0);
			}
		}

		return prod;
	}

	@Transactional
	public Product add(Product product) {
		if (productRepository.findByCode(product.getCode()).isPresent()) {
			BusinessException ex = new BusinessException("ERROR-002-002",
					"Product.code = '" + product.getCode() + "' exists");
			throw ex;
		}
		return productRepository.save(product);
	}
}