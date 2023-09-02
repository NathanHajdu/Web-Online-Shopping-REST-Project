package webOnlineShopping;

import java.util.List;

import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import jakarta.jws.soap.SOAPBinding;
import jakarta.jws.soap.SOAPBinding.Style;
import jakarta.ws.rs.NotFoundException;

@WebService
@SOAPBinding(style = Style.RPC)
public class WebOrderService {

	private ServicesHelper servicesHelper = new ServicesHelper();
	private List<Order> orders;

	public WebOrderService() {
		servicesHelper.addProducts();
		servicesHelper.addCustomers();
		orders = servicesHelper.addOrders();
	}

	// SOAP Get Orders of a Customer by customer ID
	@WebMethod
	public String getOrdersByCustomerId(@WebParam(name = "customerId") int customerId) {
	    StringBuilder orderDetails = new StringBuilder();
	    boolean customerFound = false;

	    for (Order order : orders) {
	        if (order.getCustomerId() == customerId) {
	            if (!customerFound) {
	                orderDetails.append("Orders for Customer with ID ").append(customerId).append(":\n");
	                customerFound = true;
	            }

	            orderDetails.append("Order ID: ").append(order.getOrderId()).append("\n");
	            orderDetails.append("Status: ").append(order.getStatus()).append("\n");

	            List<OrderItem> orderItems = order.getOrderItems();
	            if (!orderItems.isEmpty()) {
	                orderDetails.append("Order Items:\n");
	                for (OrderItem item : orderItems) {
	                    orderDetails.append("- ").append(item.getProduct().getProductName());
	                    orderDetails.append(" (Quantity: ").append(item.getQuantity()).append(")\n");
	                }
	            }

	            orderDetails.append("\n");
	        }
	    }

	    if (!customerFound) {
	        throw new NotFoundException("Customer with ID " + customerId + " was not found or has no orders");
	    }

	    return orderDetails.toString();
	}

	
	// SOAP Update Order Product quantity
	@WebMethod
	public Order updateOrderItemQuantity(@WebParam(name = "orderId") int orderId,
	                                     @WebParam(name = "productId") int productId,
	                                     @WebParam(name = "quantity") int quantity) {
	    for (Order order : orders) {
	        if (order.getOrderId() == orderId) {
	            for (OrderItem orderItem : order.getOrderItems()) {
	                if (orderItem.getProduct().getProductId() == productId) {
	                    orderItem.setQuantity(quantity);
	                    return order;
	                }
	            }
	            break;
	        }
	    }
	    throw new NotFoundException("Order or product not found");
	}
	
	// SOAP Get total Order quantity for a Product across all Orders
	@WebMethod
	public String getProductQuantityForAllOrders (@WebParam(name = "productId") int productId)  {
	
		int totalQuantity = 0;
	    
	    for (Order order : orders) {
	        for (OrderItem orderItem : order.getOrderItems()) {
	            if (orderItem.getProduct().getProductId() == productId) {
	                totalQuantity += orderItem.getQuantity();
	            }
	        }
	    }
	    
	    return "Product ID (" + productId + ") Total: " + totalQuantity;
	}

	
}
