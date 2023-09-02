package clientWebOnlineShoppingREST;

import java.util.Scanner;

import org.glassfish.jersey.client.ClientConfig;

import jakarta.ws.rs.ClientErrorException;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.Form;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

public class ClientCustomerREST {

	public static void main(String[] args) {

		String url = "http://localhost:8080/WebOnlineShoppingRESTProject/rest/shopping";
		ClientConfig config = new ClientConfig();

		Client client = ClientBuilder.newClient(config);

		System.out.println("Testing WebOnlineShopping Customer REST Web Services\n");

		/*------- Customer Services -------*/

		// Get all Customers
		WebTarget target = client.target(url).path("/customers/");
		String response = target.request().accept(MediaType.APPLICATION_JSON).get(String.class).trim();
		System.out.println("List of all customers: " + response);

		// Get Customer by ID
		Scanner input = new Scanner(System.in);
		System.out.println("\nTesting GET customer by ID");
		System.out.print("Please Enter Valid Customer ID: ");
		int customerId = input.nextInt();

		target = client.target(url).path("/customers/" + customerId);

		try {
			response = target.request().accept(MediaType.APPLICATION_JSON).get(String.class).trim();

			if (response != null && !response.isEmpty()) {
				System.out.println("\nCustomer is: " + response);
			} else {
				System.out.println("\nCustomer does not exist.");
			}
		} catch (ClientErrorException e) {
			if (e.getResponse().getStatus() == 404) {
				System.out.println("\nCustomer does not exist.");
			}
		}

		// Get Customer by ID Form
		System.out.println("\nTesting GET Customer by ID FORM");
		System.out.print("Please Enter Valid Order ID: ");
		customerId = input.nextInt();
		target = client.target(url).path("searchCustomers").queryParam("customerId", customerId);

		try {
			response = target.request().accept(MediaType.APPLICATION_JSON).get(String.class).trim();

			if (response != null && !response.isEmpty()) {
				System.out.println("\nCustomer is: " + response);
			} else {
				System.out.println("\nCustomer does not exist.");
			}
		} catch (ClientErrorException e2) {
			if (e2.getResponse().getStatus() == 404) {
				System.out.println("\nCustomer does not exist.");
			}
		}

		// Create new Customer Form
		System.out.println("\nTesting Creating a New Customer with addCustomer Method");
		System.out.print("Customer ID: ");
		customerId = input.nextInt();
		input.nextLine(); // Consume newline

		System.out.print("Customer First Name: ");
		String customer_FName = input.nextLine();

		System.out.print("Customer Last Name: ");
		String customer_LName = input.nextLine();

		System.out.print("Customer Email: ");
		String email = input.nextLine();
		
		System.out.print("Customer Address: ");
		String address = input.nextLine();

		WebTarget createCustomer = client.target(url).path("addCustomer");

		Form form = new Form();
		form.param("customerId", String.valueOf(customerId));
		form.param("customer_FName", customer_FName);
		form.param("customer_LName", customer_LName);
		form.param("email", email);
		form.param("address", address);

		Response postResponse = createCustomer.request()
				.post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED));

		if (postResponse.getStatus() == Response.Status.OK.getStatusCode()) {
			System.out.println("\nCustomer created successfully: " + postResponse.readEntity(String.class));
		} else {
			System.out.println("\nFailed to create Customer.");
		}

		postResponse.close();
		
		// Update existing Customer
		System.out.println("\nTesting Update Customer");
		System.out.print("Please Enter Valid Customer ID: ");
		customerId = input.nextInt();
		input.nextLine(); // Consume newline

		System.out.print("Enter Customer First Name: ");
		String updated_FName = input.nextLine();
		
		System.out.print("Enter Customer Last Name: ");
		String updated_LName = input.nextLine();
		
		System.out.print("Enter Customer Email: ");
		String updated_Email = input.nextLine();
		
		System.out.print("Enter Customer Address: ");
		String updated_Address = input.nextLine();

		WebTarget targetUpdateStatus = client.target(url).path("/updateCustomer");

		response = targetUpdateStatus.queryParam("customerId", customerId)
								     .queryParam("customer_FName", updated_FName)
								     .queryParam("customer_LName", updated_LName)
								     .queryParam("email", updated_Email)
								     .queryParam("address", updated_Address)
								     .request()
								     .put(Entity.text(""))
								     .readEntity(String.class);

		if (response != null && !response.isEmpty()) {
			System.out.println(response);
		} else {
			System.out.println("Customer does not exist. Update failed.");
		}

		// Testing Delete method removeCustomer
		System.out.println("\nTesting Resource Method removeCustomerInfo Using DELETE HTTP Request");
		System.out.print("\nPlease Enter Valid Customer ID for removal: ");
		customerId = input.nextInt();

		// Check if Customer exists before proceeding with delete
		response = target.request().accept(MediaType.APPLICATION_JSON).get(String.class).trim();
		if (response != null && !response.isEmpty()) {
			WebTarget deleteTarget = client.target(url).path("removeCustomer");

			response = deleteTarget.queryParam("customerId", customerId)
								   .request(MediaType.APPLICATION_JSON)
								   .delete()
								   .readEntity(String.class);

			if (response != null && !response.isEmpty()) {
				System.out.println(response);
			} else {
				System.out.println("Customer does not exist. Delete failed.");
			}
		} else {
			System.out.println("\nCustomer does not exist.");
		}
		input.close();
	}
}
