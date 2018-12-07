package domain.transaction;

import java.sql.Date;
import java.util.List;

import domain.product.Product;

public class Transaction {
	private Integer trxnId;
	private Date date;
	private double price;
	private List<Product> products;

	public Integer getTrxnId() {
		return trxnId;
	}

	public void setTrxnId(Integer trxnId) {
		this.trxnId = trxnId;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
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
