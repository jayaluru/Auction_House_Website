package domain.product;

import java.util.ArrayList;
import java.util.List;
import java.sql.Date;

public class NameOrDescriptionSearchStrategy implements SearchStrategy {

	private final String criteria;
	private Date startDate;
	private Date endDate;
	
	public NameOrDescriptionSearchStrategy(String criteria) {
		this.criteria = criteria;
	}
	
	public NameOrDescriptionSearchStrategy(String criteria, Date startDate, Date endDate) {
		this.criteria = criteria;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	
	@Override
	public List<Product> filter(List<Product> products) {
		ArrayList<Product> filtered = new ArrayList<>(products.size());
		for (Product prod : products) {
			boolean nameMatch = prod.getName().toLowerCase().contains(this.criteria.toLowerCase());
			boolean descMatch = prod.getDescription().toLowerCase().contains(this.criteria.toLowerCase());
			if (nameMatch || descMatch) {
				filtered.add(prod);
			}
		}
		return filtered;
	}

	@Override
	public List<Product> filter2(List<Product> products) {
		ArrayList<Product> filtered = new ArrayList<>(products.size());
		for (Product prod : products) {
			boolean nameMatch = prod.getName().toLowerCase().contains(this.criteria.toLowerCase());
			boolean descMatch = prod.getDescription().toLowerCase().contains(this.criteria.toLowerCase());
			int startMatch = prod.getBidDate().compareTo(this.startDate);
			int endMatch = prod.getBidDate().compareTo(this.endDate);
			if ((nameMatch || descMatch )&& (startMatch>=0 && endMatch <= 0)) {
				filtered.add(prod);
			}
		}
		return filtered;
	}
	
}