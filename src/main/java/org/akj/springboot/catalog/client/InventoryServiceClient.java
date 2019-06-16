package org.akj.springboot.catalog.client;

import java.util.Optional;

import org.akj.springboot.catalog.model.InventoryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

public class InventoryServiceClient {
	private final RestTemplate restTemplate;

	@Autowired
	public InventoryServiceClient(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	@HystrixCommand(fallbackMethod = "getDefaultProductInventoryByCode", commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000"),
			@HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "60") })
	public Optional<InventoryResponse> getProductInventoryByCode(String productCode) {
		ResponseEntity<InventoryResponse> itemResponseEntity = restTemplate
				.getForEntity("http://inventory-service/api/inventory/{code}", InventoryResponse.class, productCode);
		if (itemResponseEntity.getStatusCode() == HttpStatus.OK) {
			return Optional.ofNullable(itemResponseEntity.getBody());
		} else {
			return Optional.empty();
		}
	}

	Optional<InventoryResponse> getDefaultProductInventoryByCode(String productCode) {
		InventoryResponse response = new InventoryResponse();
		response.setProductCode(productCode);
		response.setQuantity(0);
		return Optional.ofNullable(response);
	}
}
