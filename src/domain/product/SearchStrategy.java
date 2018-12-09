package domain.product;

import java.util.List;

public interface SearchStrategy {

	List<Product> filter(List<Product> products);
	
	List<Product> filter2(List<Product> products);
	
}
