package org.akj.springboot.catalog.controller;

import java.util.List;

import javax.validation.Valid;

import org.akj.springboot.catalog.entity.Product;
import org.akj.springboot.catalog.service.ProductService;
import org.akj.springboot.common.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("products")
public class ProductController {

	@Autowired
	private ProductService productService = null;

	@RequestMapping(method = RequestMethod.GET)
	public List<Product> list() {
		return productService.findAllProducts();
	}

	@RequestMapping(value = "product/{code}",method=RequestMethod.GET)
	public Product getProductsByCode(@PathVariable String code) {
		return productService.findProductByCode(code)
				.orElseThrow(() -> new BusinessException("ERROR-002-003","Product with code [" + code + "] doesn't exist"));
	}

	@RequestMapping(value="/product", method = RequestMethod.POST)
	public Product add(@RequestBody @Valid Product product) {
		return productService.add(product);
	}

	public ProductService getProductService() {
		return productService;
	}

}