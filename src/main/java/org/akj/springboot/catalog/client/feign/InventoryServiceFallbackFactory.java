package org.akj.springboot.catalog.client.feign;

import org.springframework.stereotype.Component;

import feign.hystrix.FallbackFactory;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@AllArgsConstructor
public class InventoryServiceFallbackFactory implements FallbackFactory<InventoryServiceClient> {
	private InventoryServiceFallback fallback;

	@Override
	public InventoryServiceClient create(Throwable cause) {

		log.error(cause.getMessage(), cause);
		return fallback;
	}

}
