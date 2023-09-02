package webOnlineShopping;

import java.util.List;

public class Customer {
	private int customerId;
	private String customer_FName;
	private String customer_LName;
	private String email;
	private String address;
	private List<Product> products;
	private List<Order> orders;

	public Customer() {
	}

	public Customer(int customerId, String customer_FName, String customer_LName, String email, String address,
			List<Product> products, List<Order> orders) {
		this.customerId = customerId;
		this.customer_FName = customer_FName;
		this.customer_LName = customer_LName;
		this.email = email;
		this.address = address;
		this.products = products;
		this.orders = orders;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public String getCustomer_FName() {
		return customer_FName;
	}

	public void setCustomer_FName(String customer_FName) {
		this.customer_FName = customer_FName;
	}

	public String getCustomer_LName() {
		return customer_LName;
	}

	public void setCustomer_LName(String customer_LName) {
		this.customer_LName = customer_LName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	public void addProduct(Product product) {
		products.add(product);
	}

	public void removeProduct(Product product) {
		products.remove(product);
	}

	public List<Product> getProducts() {
		return products;
	}

	public void addOrder(Order order) {
		orders.add(order);
	}

	public void removeOrder(Order order) {
		orders.remove(order);
	}

	public List<Order> getOrders() {
		return orders;
	}

	@Override
	public String toString() {
		return "Customer ID: " + customerId + ", Customer First Name: " + customer_FName + ", Customer Last Name: "
				+ customer_LName + ", Email: " + email + ", Address: " + address + ", Products: " + products
				+ ", Orders: " + orders + "\n";
	}

}
