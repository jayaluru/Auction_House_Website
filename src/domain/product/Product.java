package domain.product;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.util.Base64;

import javax.imageio.ImageIO;

public class Product {

	private Integer prodId;
	private String name;
	private String description;
	private double price;
	private boolean isSold;
	private BufferedImage image;
	private String bidStartTime;
	private String bidEndTime;
	private Date bidDate;

	public Integer getProdId() {
		return prodId;
	}

	public void setProdId(Integer prodId) {
		this.prodId = prodId;
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

	public String getBidStartTime() {
		return bidStartTime;
	}

	public void setBidStartTime(String bidStartTime) {
		this.bidStartTime = bidStartTime;
	}

	public String getBidEndTime() {
		return bidEndTime;
	}

	public void setBidEndTime(String bidEndTime) {
		this.bidEndTime = bidEndTime;
	}

	public Date getBidDate() {
		return bidDate;
	}

	public void setBidDate(Date bidDate) {
		this.bidDate = bidDate;
	}

}
