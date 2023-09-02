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
public class WebCustomerService {

	private ServicesHelper servicesHelper = new ServicesHelper();
	private List<Customer> customers;

	public WebCustomerService() {
		servicesHelper.addCustomers();
		customers = servicesHelper.addCustomers();
	}

	// SOAP search Customer by Full Name
	@WebMethod
	public Customer getCustomerByName(@WebParam(name = "customer_FName") String customer_FName,
									  @WebParam(name = "customer_LName") String customer_LName) {
		Customer output = null;
		for (Customer customer : customers) {
			if (customer.getCustomer_FName().equalsIgnoreCase(customer_FName) && 
				customer.getCustomer_LName().equalsIgnoreCase(customer_LName)) {
				output = customer;
				break;
			}
		}
		if (output != null) {
			return output;
		} else {
			throw new NotFoundException("Customer named " + customer_FName + " " + customer_LName + " was not found");
		}
	}
	
	// SOAP search Customer by Email 
	@WebMethod
	public Customer getCustomerByEmail(@WebParam(name = "email") String email) {
		
		Customer output = null;
		for (Customer customer : customers) {
			if (customer.getEmail().equalsIgnoreCase(email)) {
				output = customer;
				break;
			}
		}
		if (output != null) {
			return output;
		} else {
			throw new NotFoundException("Customer with email (" + email + ") is not found");
		}
	}

	// SOAP create Customer
	@WebMethod
	public Customer addNewCustomerSoap(@WebParam(name="customerId") int customerId,
	                                   @WebParam(name="customer_FName") String customer_FName,
	                                   @WebParam(name="customer_LName") String customer_LName,
	                                   @WebParam(name="email") String email,
	                                   @WebParam(name="address") String address) {
	    
		String lowerCaseEmail = email.toLowerCase(); 

	    for (Customer record : customers) {
	        if (record.getCustomerId() == customerId) {
	            throw new IllegalArgumentException("Customer with ID " + customerId + " already exists");
	        }
	        if (record.getEmail().toLowerCase().equals(lowerCaseEmail)) {
	            throw new IllegalArgumentException("The email '" + email + "' already exists");
	        }
	    }
	    
	    Customer customer = new Customer();
	    customer.setCustomerId(customerId);
	    customer.setCustomer_FName(customer_FName);
	    customer.setCustomer_LName(customer_LName);
	    customer.setEmail(email);
	    customer.setAddress(address);

	    customers.add(customer);

	    return customer;
	}

}
