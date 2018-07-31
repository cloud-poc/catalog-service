package org.akj.springboot.catalog.model;

public class InventoryResponse {
	private String productCode;
	private Integer quantity = 0;

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

}
