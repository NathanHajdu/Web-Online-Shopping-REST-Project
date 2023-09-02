package webOnlineShopping;

import java.util.List;

public class Order {
	
	private int orderId;
	private int customerId;
	private List<OrderItem> orderItems;
	private String status;
	
	public Order() {
	}
	
	public Order(int orderId, int customerId, List<OrderItem> orderItems, String status) {
		this.orderId = orderId;
		this.customerId = customerId;
		this.orderItems = orderItems;
		this.status = status;
	}
	
	public int getOrderId() {
		return orderId;
	}
	
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	
	public int getCustomerId() {
		return customerId;
	}
	
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	
	public List<OrderItem> getOrderItems() {
		return orderItems;
	}
	
	public void setOrderItems(List<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	@Override
	public String toString() {
		return "Order ID: " + orderId + ", Customer ID: " + customerId + ", Order Items: " + orderItems + ", Status: " + status + "\n";
	}
}
