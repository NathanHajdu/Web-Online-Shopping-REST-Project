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

public class ClientOrderREST {

	public static void main(String[] args) {

		String url = "http://localhost:8080/WebOnlineShoppingRESTProject/rest/shopping";
		ClientConfig config = new ClientConfig();

		Client client = ClientBuilder.newClient(config);

		System.out.println("Testing WebOnlineShopping Order REST Web Services\n");

		/*------- Order Services -------*/

		// Get all Orders
		WebTarget target = client.target(url).path("/orders/");
		String response = target.request().accept(MediaType.APPLICATION_JSON).get(String.class).trim();
		System.out.println("List of all orders: " + response);

		// Get Order by ID
		Scanner input = new Scanner(System.in);
		System.out.println("\nTesting GET order by ID");
		System.out.print("Please Enter Valid Order ID: ");
		int orderId = input.nextInt();

		target = client.target(url).path("/orders/" + orderId);

		try {
			response = target.request().accept(MediaType.APPLICATION_JSON).get(String.class).trim();

			if (response != null && !response.isEmpty()) {
				System.out.println("\nOrder is: " + response);
			} else {
				System.out.println("\nOrder does not exist.");
			}
		} catch (ClientErrorException e) {
			if (e.getResponse().getStatus() == 404) {
				System.out.println("\nOrder does not exist.");
			}
		}

		// Get Order by ID FORM
		System.out.println("\nTesting GET product by ID FORM");
		System.out.print("Please Enter Valid Order ID: ");
		orderId = input.nextInt();
		target = client.target(url).path("searchOrder").queryParam("orderId", orderId);

		try {
			response = target.request().accept(MediaType.APPLICATION_JSON).get(String.class).trim();

			if (response != null && !response.isEmpty()) {
				System.out.println("\nOrder is: " + response);
			} else {
				System.out.println("\nOrder does not exist.");
			}
		} catch (ClientErrorException e2) {
			if (e2.getResponse().getStatus() == 404) {
				System.out.println("\nOrder does not exist.");
			}
		}

		// Add product to order
		System.out.println("\nTesting Adding a Product to an Order using addProductOrder Method");
		System.out.print("Customer ID: ");
		int customerId = input.nextInt();
		input.nextLine(); // Consume newline

		System.out.print("Order ID: ");
		int newOrderId = input.nextInt();
		input.nextLine(); // Consume newline

		System.out.print("Product ID: ");
		int productId = input.nextInt();
		input.nextLine(); // Consume newline

		System.out.print("Product Quantity: ");
		int quantity = input.nextInt();
		input.nextLine(); // Consume newline

		WebTarget addToOrder = client.target(url).path("addProductOrder");

		Form form = new Form();
		form.param("customerId", String.valueOf(customerId));
		form.param("orderId", String.valueOf(newOrderId));
		form.param("productId", String.valueOf(productId));
		form.param("quantity", String.valueOf(quantity));

		Response postResponse = addToOrder.request().post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED));

		System.out.println("Updated Order: " + postResponse.readEntity(String.class));

		postResponse.close();

		// Get Orders for a Customer
		System.out.println("\nTesting Get Customer Orders using getOrdersForCustomer Method");
		System.out.print("Customer ID: ");
		customerId = input.nextInt();
		input.nextLine(); // Consume newline

		WebTarget targetGetOrders = client.target(url).path("/getCustomerOrders").queryParam("customerId", customerId);

		try {
			String responseOrders = targetGetOrders.request().accept(MediaType.APPLICATION_JSON).get(String.class)
					.trim();

			if (responseOrders != null && !responseOrders.isEmpty()) {
				System.out.println("\nOrder is: " + responseOrders);
			} else {
				System.out.println("\nOrder does not exist.");
			}
		} catch (ClientErrorException e2) {
			if (e2.getResponse().getStatus() == 404) {
				System.out.println("\nOrder does not exist.");
			}
		}

		// Update status of existing Order
		System.out.println("\nTesting Update status of existing Order");
		System.out.print("Please Enter Valid Order ID: ");
		orderId = input.nextInt();
		input.nextLine(); // Consume newline

		System.out.print("Enter the new status: ");
		String updatedStatus = input.nextLine();

		WebTarget targetUpdateStatus = client.target(url).path("/updateOrder");

		response = targetUpdateStatus.queryParam("orderId", orderId)
								     .queryParam("status", updatedStatus)
								     .request()
								     .put(Entity.text(""))
								     .readEntity(String.class);

		if (response != null && !response.isEmpty()) {
			System.out.println(response);
		} else {
			System.out.println("Order does not exist. Update failed.");
		}

		// Testing Delete method removeOrder
		System.out.println("\nTesting Resource Method removeOrderInfo Using DELETE HTTP Request");
		System.out.print("\nPlease Enter Valid Order ID for removal: ");
		orderId = input.nextInt();

		// Check if order exists before proceeding with delete
		response = target.request().accept(MediaType.APPLICATION_JSON).get(String.class).trim();
		if (response != null && !response.isEmpty()) {
			WebTarget deleteTarget = client.target(url).path("removeOrder");

			response = deleteTarget.queryParam("orderId", orderId).request(MediaType.APPLICATION_JSON).delete()
					.readEntity(String.class);

			if (response != null && !response.isEmpty()) {
				System.out.println(response);
			} else {
				System.out.println("Order does not exist. Delete failed.");
			}
		} else {
			System.out.println("\nOrder does not exist.");
		}
		input.close();
	}
}
