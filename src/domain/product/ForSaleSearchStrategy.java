package domain.product;

import java.util.ArrayList;
import java.util.List;

public class ForSaleSearchStrategy implements SearchStrategy {

	@Override
	public List<Product> filter(List<Product> products) {
		ArrayList<Product> filtered = new ArrayList<>(products.size());
		for (Product prod : products) {
			if (!prod.isSold()) {
				filtered.add(prod);
			}
		}
		return filtered;
	}

}
