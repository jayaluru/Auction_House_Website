package domain.user;

import java.util.List;

import domain.product.Product;

public class Inventory {
	private Integer invnId;
	private List<Product> products;

	public Integer getInvnId() {
		return invnId;
	}

	public void setInvnId(Integer invnId) {
		this.invnId = invnId;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public void addProduct(Product product) {
		this.products.add(product);
	}

}
