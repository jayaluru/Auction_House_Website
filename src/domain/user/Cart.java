package domain.user;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import db.services.impl.TransactionPersistenceServiceImpl;
import domain.product.Product;
import domain.transaction.Transaction;

public class Cart {
	private Integer cartId;
	private List<Product> products;

	public Integer getCartId() {
		return cartId;
	}

	public void setCartId(Integer cartId) {
		this.cartId = cartId;
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
	
	public void removeProduct(Product prod)
	{
		Integer prodId = prod.getProdId();
		Iterator<Product> prodIter = products.iterator();
		while (prodIter.hasNext()) {
		  Product product = prodIter.next();
		  if (product.getProdId().equals(prodId)) {
			  prodIter.remove();
		  }
		}
		
	}

	public Transaction checkout() {
		Transaction trxn = TransactionPersistenceServiceImpl.getInstance().getTransaction();
		double totalPrice = 0.0;
		List<Product> trxnProds = new ArrayList<Product>();
		for (Product prod : this.products) {
			prod.setSold(true);
			trxnProds.add(prod);
			totalPrice += prod.getPrice();
		}
		trxn.setProducts(trxnProds);
		trxn.setPrice(totalPrice);
		trxn.setDate(new Date(System.currentTimeMillis()));
		this.products = new ArrayList<Product>();
		return trxn;
	}
	
}
