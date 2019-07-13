package org.akj.springboot.catalog.client.feign;

import java.util.Optional;

import org.akj.springboot.catalog.model.InventoryResponse;
import org.springframework.stereotype.Component;

@Component
public class InventoryServiceFallback implements InventoryServiceClient {

	@Override
	public Optional<InventoryResponse> getProductInventoryByCode(String productCode) {
		return Optional.empty();
	};
}
