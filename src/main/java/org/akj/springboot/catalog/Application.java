package org.akj.springboot.catalog;

import org.akj.springboot.common.exception.TechnicalException;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileUrlResource;
import org.springframework.core.io.Resource;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.util.ResourceUtils;
import org.springframework.web.client.RestTemplate;

/**
 * Springboot application - Entry point
 * 
 * @author Jamie Y L Zhang
 */

@EnableCircuitBreaker
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = "org.akj.springboot")
public class Application {
	@Value("${security.oauth2.resource.verifier-key.file-path}")
	private String publicKeyFilePath;

	@Bean
	@LoadBalanced
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	@Primary
	public JwtAccessTokenConverter accessTokenConverter() {
		JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
		String publicKey = null;

		try {
			Resource resource = null;
			if (publicKeyFilePath.startsWith(ResourceUtils.FILE_URL_PREFIX)) {
				resource = new FileUrlResource(publicKeyFilePath);
			} else {
				resource = new ClassPathResource(publicKeyFilePath);
			}

			publicKey = IOUtils.toString(resource.getInputStream());

		} catch (Exception e) {
			TechnicalException ex = new TechnicalException("ERROR-020-001", e.getMessage());
			throw ex;
		}

		converter.setVerifierKey(publicKey);
		return converter;
	}

}
