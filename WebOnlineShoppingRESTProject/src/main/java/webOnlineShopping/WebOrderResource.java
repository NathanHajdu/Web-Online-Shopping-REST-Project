package webOnlineShopping;

import java.util.List;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("shopping")
public class WebOrderResource {

	private ServicesHelper servicesHelper = new ServicesHelper();
	private List<Order> orders;
	private List<Customer> customers;
	private int nextOrderId;

	public WebOrderResource() {
		servicesHelper.addProducts();
		servicesHelper.addCustomers();
		orders = servicesHelper.addOrders();
		customers = servicesHelper.addCustomers();
	}

	/*------- Order Services -------*/

	// Get all Orders
	@GET
	@Path("/orders")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Order> getAllOrders() {
		return orders;
	}

	// Get Order by ID
	@GET
	@Path("/orders/{orderId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Order getOrderById(@PathParam("orderId") int orderId) {
		for (Order order : orders) {
			if (order.getOrderId() == orderId) {
				return order;
			}
		}
		throw new NotFoundException("Order not found");
	}

	// Search Order by ID
	@GET
	@Path("/searchOrder")
	@Produces(MediaType.APPLICATION_JSON)
	public Order searchOrderById(@QueryParam("orderId") int orderId) {
		Order output = null;
		for (Order order : orders) {
			if (order.getOrderId() == orderId) {
				output = order;
				break;
			}
		}
		if (output != null) {
			return output;
		} else {
			throw new NotFoundException("Order not found");
		}
	}

	// Create new Order for a Customer
	@POST
	@Path("/customers/{customerId}/orders")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createOrderForClient(@PathParam("customerId") int customerId, Order order) {
		Customer customer = null;
		for (Customer existingClient : customers) {
			if (existingClient.getCustomerId() == customerId) {
				customer = existingClient;
				break;
			}
		}
		if (customer != null) {
			order.setOrderId(nextOrderId++);
			customer.addOrder(order);
			orders.add(order);
			return Response.status(Response.Status.CREATED).build();
		}
		return Response.status(Response.Status.NOT_FOUND).entity("Client not found").build();
	}

	// Add Product to Order for a Customer
	@POST
	@Path("/addProductOrder")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public List<Order> addProductOrder(@FormParam("customerId") int customerId, 
									   @FormParam("orderId") int orderId,
									   @FormParam("status") String status, 
									   @FormParam("productId") int productId,
									   @FormParam("quantity") int quantity) {

		Customer customer = null;
		for (Customer existingCustomer : customers) {
			if (existingCustomer.getCustomerId() == customerId) {
				customer = existingCustomer;
				break;
			}
		}

		if (customer != null) {
			Order order = null;
			for (Order existingOrder : customer.getOrders()) {
				if (existingOrder.getOrderId() == orderId) {
					order = existingOrder;
					break;
				}
			}

			if (order != null) {
				List<Product> products = servicesHelper.addProducts();
				Product productToAdd = null;
				for (Product product : products) {
					if (product.getProductId() == productId) {
						productToAdd = product;
						break;
					}
				}

				if (productToAdd != null) {
					OrderItem orderItem = new OrderItem(productToAdd, quantity);
					order.getOrderItems().add(orderItem);
					order.setStatus(status); // Update the status of the order

					return customer.getOrders();
				} else {
					throw new NotFoundException("Product not found");
				}
			} else {
				throw new NotFoundException("Order not found");
			}
		}

		throw new NotFoundException("Customer not found");
	}

	// Get Orders for a Customer
	@GET
	@Path("/customers/{customerId}/orders")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getOrdersForCustomer(@PathParam("customerId") int customerId) {
		Customer customer = null;
		for (Customer existingCustomer : customers) {
			if (existingCustomer.getCustomerId() == customerId) {
				customer = existingCustomer;
				break;
			}
		}
		if (customer != null) {
			List<Order> customerOrders = customer.getOrders();
			return Response.ok(customerOrders).build();
		}
		return Response.status(Response.Status.NOT_FOUND).entity("Customer not found").build();
	}
	
	// Get Orders for a Customer
	@GET
	@Path("/getCustomerOrders")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Order> getOrderForCustomer(@QueryParam("customerId") int customerId) {
	    Customer customer = null;
	    for (Customer existingCustomer : customers) {
	        if (existingCustomer.getCustomerId() == customerId) {
	            customer = existingCustomer;
	            break;
	        }
	    }
	    if (customer != null) {
	        List<Order> customerOrders = customer.getOrders();
	        return customerOrders;
	    }
	    throw new NotFoundException("Customer not found");
	}


	// Update status of existing Order
	@PUT
	@Path("/orders/{orderId}/status")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateOrderStatus(@PathParam("orderId") int orderId, Order updatedOrder) {
		for (Order order : orders) {
			if (order.getOrderId() == orderId) {
				order.setStatus(updatedOrder.getStatus());
				return Response.status(Response.Status.NO_CONTENT).build();
			}
		}
		return Response.status(Response.Status.NOT_FOUND).entity("Order not found").build();
	}
	
	/// Update status for Client
	@PUT
	@Path("/updateOrder")
	@Produces(MediaType.APPLICATION_JSON)
	public Order updateOrderStatusClient(@QueryParam("orderId") int orderId, 
	                                     @QueryParam("status") String updatedStatus) {
	    for (Order order : orders) {
	        if (order.getOrderId() == orderId) {
	            order.setStatus(updatedStatus);
	            return order; 
	        }
	    }
	    return null; 
	}


	// Cancel existing Order
	@DELETE
	@Path("/orders/{orderId}")
	public Response cancelOrder(@PathParam("orderId") int orderId) {
		for (Order order : orders) {
			if (order.getOrderId() == orderId) {
				orders.remove(order);
				return Response.status(Response.Status.NO_CONTENT).build();
			}
		}
		return Response.status(Response.Status.NOT_FOUND).entity("Order not found").build();
	}
	
	// Remove Order
	@DELETE
	@Path("/removeOrder")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Order> removeOrderInfo(@QueryParam("orderId") int orderId){
		
		for (Order order : orders) {
			if (order.getOrderId() == orderId) {
				orders.remove(order);
				break;
			}
		}
		
		return orders;
	}
}
