package webOnlineShopping;

import java.util.ArrayList;
import java.util.List;

import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import jakarta.jws.soap.SOAPBinding;
import jakarta.jws.soap.SOAPBinding.Style;

@WebService
@SOAPBinding(style = Style.RPC)
public class WebProductService {

	private ServicesHelper servicesHelper = new ServicesHelper();
	private List<Product> products;
	private List<Order> orders;

	public WebProductService() {
		products = servicesHelper.addProducts();
		orders = servicesHelper.addOrders();
	}

	// SOAP update Product price by Percentage
	@WebMethod
	public Product updateProductPriceByPercentage(@WebParam(name = "productId") int productId,
			@WebParam(name = "percentage") double percentage) {

		Product output = null;
		for (Product product : products) {
			if (product.getProductId() == productId) {
				double currentPrice = product.getProductPrice();

				if (percentage == 0) {
					double defaultPrice = product.getProductPrice();
					product.setProductPrice(defaultPrice);
				} else {
					double newPrice = currentPrice * (1 + percentage / 100);
					product.setProductPrice(newPrice);
				}

				output = product;
				break;
			}
		}
		return output;
	}

	// SOAP Get Orders with Product + Details
	@WebMethod
	public String displayOrdersWithProduct(@WebParam(name = "productId") int productId) {
		StringBuilder output = new StringBuilder();

		for (Order order : orders) {
			boolean found = false;
			StringBuilder orderInfo = new StringBuilder();

			orderInfo.append("\n[Order ID: ").append(order.getOrderId()).append(", ");
			orderInfo.append("Customer ID: ").append(order.getCustomerId()).append(", ");
			orderInfo.append("Status: ").append(order.getStatus());
			orderInfo.append("] = [");

			for (OrderItem orderItem : order.getOrderItems()) {
				if (orderItem.getProduct().getProductId() == productId) {
					found = true;
					orderInfo.append("Product ID: ").append(orderItem.getProduct().getProductId()).append(", ");
					orderInfo.append("Product: ").append(orderItem.getProduct().getProductName()).append(", ");
					orderInfo.append("Quantity: ").append(orderItem.getQuantity()).append(", ");
				}
			}

			if (found) {
				orderInfo.delete(orderInfo.length() - 2, orderInfo.length()); // Remove the last comma and space
				orderInfo.append("]");
				output.append(orderInfo.toString()).append("\n");
			}
		}

		return output.toString();
	}

	// SOAP Get Product by price range
	@WebMethod
	public String getProductsByPriceRange(@WebParam(name = "minPrice") double minPrice,
	                                      @WebParam(name = "maxPrice") double maxPrice) {
		
	    List<Product> productsInPriceRange = new ArrayList<>();
	    for (Product product : products) {
	        if (product.getProductPrice() >= minPrice && product.getProductPrice() <= maxPrice) {
	            productsInPriceRange.add(product);
	        }
	    }

	    StringBuilder responseBuilder = new StringBuilder();
	    responseBuilder.append("Products within price range: $").append(minPrice).append(" - $").append(maxPrice).append("\n");

	    if (productsInPriceRange.isEmpty()) {
	        responseBuilder.append("No products found within the specified price range.");
	    } else {
	        for (Product product : productsInPriceRange) {
	            responseBuilder.append("Product ID: ").append(product.getProductId()).append("\n");
	            responseBuilder.append("Product Name: ").append(product.getProductName()).append("\n");
	            responseBuilder.append("Product Price: $").append(product.getProductPrice()).append("\n");
	            responseBuilder.append("Product Description: ").append(product.getProductDesc()).append("\n");
	            responseBuilder.append("\n");
	        }
	    }

	    return responseBuilder.toString();
	}

}
