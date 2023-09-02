package webOnlineShopping;

public class Product {

	private int productId;
	private String productName;
	private double productPrice;
	private String productDesc;
	
	public Product() {
	}
	
	public Product(int productId, String productName, double productPrice, String productDesc) {
		this.productId = productId;
		this.productName = productName;
		this.productPrice = productPrice;
		this.productDesc = productDesc;	
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public double getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(double productPrice) {
		this.productPrice = productPrice;
	}

	public String getProductDesc() {
		return productDesc;
	}

	public void setProductDesc(String productDesc) {
		this.productDesc = productDesc;
	}

	@Override
	public String toString() {
		return "Product ID: " + productId + ", Product Name: " + productName + ", Price: " + productPrice
				+ ", Product Description: " + productDesc + "\n";
	}
}