package domain.product;

public class Painting extends Product {
	private String canvasType;
	private String paintType;
	private double length;
	private double width;

	public String getCanvasType() {
		return canvasType;
	}

	public void setCanvasType(String canvasType) {
		this.canvasType = canvasType;
	}

	public String getPaintType() {
		return paintType;
	}

	public void setPaintType(String paintType) {
		this.paintType = paintType;
	}

	public double getLength() {
		return length;
	}

	public void setLength(double length) {
		this.length = length;
	}

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

}
