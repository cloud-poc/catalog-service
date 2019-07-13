package org.akj.springboot.catalog.client.feign;

import java.util.Optional;

import org.akj.springboot.catalog.model.InventoryResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name="inventory-service",fallbackFactory = InventoryServiceFallbackFactory.class)
public interface InventoryServiceClient {
	
	@RequestMapping(method=RequestMethod.GET, value = "/inventory/{code}")
	Optional<InventoryResponse> getProductInventoryByCode(@PathVariable("code")String productCode);

}
