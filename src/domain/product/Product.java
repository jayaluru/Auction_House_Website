package domain.product;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

import javax.imageio.ImageIO;

public class Product {

	private Integer prodId;
	private Category category;
	private String name;
	private String description;
	private double price;
	private boolean isSold;
	private BufferedImage image;

	public Integer getProdId() {
		return prodId;
	}

	public void setProdId(Integer prodId) {
		this.prodId = prodId;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
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

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public boolean isSold() {
		return isSold;
	}

	public void setSold(boolean isSold) {
		this.isSold = isSold;
	}
	
	public void setImage(BufferedImage img)
	{
		this.image=img;
	}
	
	public BufferedImage getImage()
	{
		return image;
	}
	
	public String getEncodedImage()
	{
		String encodedImage = null;
		ByteArrayOutputStream baos = null;
		try {
			baos = new ByteArrayOutputStream();
			ImageIO.write(this.image, "jpg", baos);
			baos.flush();
			byte[] imageInByteArray = baos.toByteArray();
			encodedImage = Base64.getEncoder().encodeToString(imageInByteArray);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (baos != null) {
				try {
					baos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return encodedImage;
	}

}
