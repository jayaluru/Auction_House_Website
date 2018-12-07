package domain.product;

public class Category {
	
	// Used to keep track of the limited categories in our system.
	// In the future this could be dynamic, but then we would need 
	// dynamic controllers and web pages.
	public static int PAINTING = 1;
	public static int SCULPTURE = 2;
	public static int CRAFT = 3;
	
	private Integer catId;
	private String name;
	private String description;
	
	public Integer getCatId() {
		return catId;
	}
	public void setCatId(Integer catId) {
		this.catId = catId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
		
}
