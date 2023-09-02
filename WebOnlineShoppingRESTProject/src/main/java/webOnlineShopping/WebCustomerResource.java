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
public class WebCustomerResource {

	private ServicesHelper servicesHelper = new ServicesHelper();
	private List<Customer> customers;

	public WebCustomerResource() {
		servicesHelper.addCustomers();
		customers = servicesHelper.addCustomers();
	}

	/*------- Customer Services -------*/

	// Get all Customers
	@GET
	@Path("/customers")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Customer> getAllCustomers() {
		return customers;
	}

	// Get Customer by ID
	@GET
	@Path("/customers/{customerId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCustomerById(@PathParam("customerId") int customerId) {
		for (Customer customer : customers) {
			if (customer.getCustomerId() == customerId) {
				return Response.ok(customer).build();
			}
		}
		return Response.status(Response.Status.NOT_FOUND).entity("Customer not found").build();
	}

	// Search Customer by ID
	@GET
	@Path("/searchCustomers")
	@Produces(MediaType.APPLICATION_JSON)
	public Customer searchCustomerById(@QueryParam("customerId") int customerId) {
		Customer output = null;
		for (Customer customer : customers) {
			if (customer.getCustomerId() == customerId) {
				output = customer;
				break;
			}
		}
		if (output != null) {
			return output;
		} else {
			throw new NotFoundException("Customer not found");
		}
	}

	// Create new Customer
	@POST
	@Path("/customers")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createCustomer(Customer customer) {
		customers.add(customer);
		return Response.status(Response.Status.CREATED).build();
	}

	// Create new Customer Form
	@POST
	@Path("addCustomer")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Customer> addNewCustomerInfo(@FormParam("customerId") int customerId,
											 @FormParam("customer_FName") String customer_FName, 
											 @FormParam("customer_LName") String customer_LName,
											 @FormParam("email") String email, 
											 @FormParam("address") String address) {

		Customer customer = new Customer();
		customer.setCustomerId(customerId);
		customer.setCustomer_FName(customer_FName);
		customer.setCustomer_LName(customer_LName);
		customer.setEmail(email);
		customer.setAddress(address);

		customers.add(customer);

		return customers;

	}

	// Update existing Customer
	@PUT
	@Path("/customers/{customerId}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateCustomer(@PathParam("customerId") int customerId, Customer updatedCustomer) {
		for (Customer customer : customers) {
			if (customer.getCustomerId() == customerId) {
				customer.setCustomer_FName(updatedCustomer.getCustomer_FName());
				customer.setCustomer_LName(updatedCustomer.getCustomer_LName());
				customer.setEmail(updatedCustomer.getEmail());
				customer.setAddress(updatedCustomer.getAddress());
				return Response.status(Response.Status.NO_CONTENT).build();
			}
		}
		return Response.status(Response.Status.NOT_FOUND).entity("Customer not found").build();
	}

	/// Update Customer 
	@PUT
	@Path("/updateCustomer")
	@Produces(MediaType.APPLICATION_JSON)
	public Customer updateCustomer(@QueryParam("customerId") int customerId,
							       @QueryParam("customer_FName") String customer_FName,
								   @QueryParam("customer_LName") String customer_LName,
								   @QueryParam("email") String email,
								   @QueryParam("address") String address){
		for (Customer customer : customers) {
			if (customer.getCustomerId() == customerId) {
				customer.setCustomer_FName(customer_FName);
				customer.setCustomer_LName(customer_LName);
				customer.setEmail(email);
				customer.setAddress(address);
				return customer;
			}
		}
		return null;
	}

	// Delete existing Customer
	@DELETE
	@Path("/customers/{customerId}")
	public Response deleteCustomer(@PathParam("customerId") int customerId) {
		for (Customer customer : customers) {
			if (customer.getCustomerId() == customerId) {
				customers.remove(customer);
				return Response.status(Response.Status.NO_CONTENT).build();
			}
		}
		return Response.status(Response.Status.NOT_FOUND).entity("Customer not found").build();
	}
	
	// Delete Customer 
	@DELETE
	@Path("removeCustomer")
	public List<Customer> removeCustomerInfo(@QueryParam("customerId") int customerId){
		for (Customer customer : customers) {
			if (customer.getCustomerId() == customerId) {
				customers.remove(customer);
			}
		}
		return customers;
	}
}
