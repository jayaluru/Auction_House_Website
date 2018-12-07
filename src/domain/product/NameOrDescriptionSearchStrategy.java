package domain.product;

import java.util.ArrayList;
import java.util.List;

public class NameOrDescriptionSearchStrategy implements SearchStrategy {

	private final String criteria;
	
	public NameOrDescriptionSearchStrategy(String criteria) {
		this.criteria = criteria;
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

}