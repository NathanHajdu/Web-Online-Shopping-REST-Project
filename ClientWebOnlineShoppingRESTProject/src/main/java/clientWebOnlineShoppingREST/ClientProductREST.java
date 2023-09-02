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

public class ClientProductREST {

	public static void main(String[] args) {

		String url = "http://localhost:8080/WebOnlineShoppingRESTProject/rest/shopping";
		ClientConfig config = new ClientConfig();

		Client client = ClientBuilder.newClient(config);

		System.out.println("Testing WebOnlineShopping Product REST Web Services\n");

		/*------- Product Services -------*/

		// Get all Products
		WebTarget target = client.target(url).path("/products/");
		String response = target.request().accept(MediaType.APPLICATION_JSON).get(String.class).trim();
		System.out.println("List of all products: " + response);

		// Get Product by ID
		Scanner input = new Scanner(System.in);
		System.out.println("\nTesting GET product by ID");
		System.out.print("Please Enter Valid Product ID: ");
		int productId = input.nextInt();

		target = client.target(url).path("/products/" + productId);

		try {
			response = target.request().accept(MediaType.APPLICATION_JSON).get(String.class).trim();

			if (response != null && !response.isEmpty()) {
				System.out.println("\nProduct is: " + response);
			} else {
				System.out.println("\nProduct does not exist.");
			}
		} catch (ClientErrorException e) {
			if (e.getResponse().getStatus() == 404) {
				System.out.println("\nProduct does not exist.");
			}
		}

		// Get Product by ID FORM
		System.out.println("\nTesting GET product by ID FORM");
		System.out.print("Please Enter Valid Product ID: ");
		productId = input.nextInt();
		target = client.target(url).path("searchProduct").queryParam("productId", productId);

		try {
			response = target.request().accept(MediaType.APPLICATION_JSON).get(String.class).trim();

			if (response != null && !response.isEmpty()) {
				System.out.println("\nProduct is: " + response);
			} else {
				System.out.println("\nProduct does not exist.");
			}
		} catch (ClientErrorException e2) {
			if (e2.getResponse().getStatus() == 404) {
				System.out.println("\nProduct does not exist.");
			}

		}

		// Create new Product
		System.out.println("\nTesting Creating a New Product with AddProduct Method");
		System.out.print("Product ID: ");
		productId = input.nextInt();
		input.nextLine(); // Consume newline

		System.out.print("Product Name: ");
		String productName = input.nextLine();

		System.out.print("Product Price: ");
		double productPrice = input.nextDouble();
		input.nextLine(); // Consume newline

		System.out.print("Product Description: ");
		String productDesc = input.nextLine();

		WebTarget createProduct = client.target(url).path("addProduct");

		Form form = new Form();
		form.param("productId", String.valueOf(productId));
		form.param("productName", productName);
		form.param("productPrice", String.valueOf(productPrice));
		form.param("productDesc", productDesc);

		Response postResponse = createProduct.request()
				.post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED));

		if (postResponse.getStatus() == Response.Status.OK.getStatusCode()) {
			System.out.println("\nProduct created successfully: " + postResponse.readEntity(String.class));
		} else {
			System.out.println("\nFailed to create product.");
		}

		postResponse.close();

		// Update Product
		System.out.println("\nTesting Resource Method updateProduct Using PUT HTTP Request");
		System.out.print("Please enter a valid Product ID: ");
		productId = input.nextInt();
		input.nextLine(); // Consume newline

		target = client.target(url).path("/products/" + productId);

		Response putResponse = target.request().accept(MediaType.APPLICATION_JSON).get();

		if (putResponse.getStatus() == Response.Status.OK.getStatusCode()) {
			// Ask for new Product Name
			System.out.print("Product Name: ");
			productName = input.nextLine();

			// Ask for new Product Price
			System.out.print("Product Price: ");
			productPrice = input.nextDouble();
			input.nextLine(); // Consume newline

			// Ask for new Product Desc
			System.out.println("Product Description: ");
			productDesc = input.nextLine();

			WebTarget updateTarget = client.target(url).path("updateProduct");
			response = updateTarget.queryParam("productId", productId)
								   .queryParam("productName", productName)
								   .queryParam("productPrice", productPrice)
								   .queryParam("productDesc", productDesc)
								   .request(MediaType.APPLICATION_JSON)
								   .put(Entity.text(""))
								   .readEntity(String.class);

			if (response != null && !response.isEmpty()) {
				System.out.println(response);
			} else {
				System.out.println("Product does not exist. Update failed.");
			}

			// Testing Delete method removeCourse
			System.out.println("\nTesting Resource Method removeProductInfo Using DELETE HTTP Request");
			System.out.print("\nPlease Enter Valid Product ID for removal: ");
			productId = input.nextInt();

			// Check if course exists before proceeding with delete
			response = target.request().accept(MediaType.APPLICATION_JSON).get(String.class).trim();
			if (response != null && !response.isEmpty()) {
				WebTarget deleteTarget = client.target(url).path("removeProduct");

				response = deleteTarget.queryParam("productId", productId).request(MediaType.APPLICATION_JSON).delete()
						.readEntity(String.class);

				if (response != null && !response.isEmpty()) {
					System.out.println(response);
				} else {
					System.out.println("Product does not exist. Delete failed.");
				}
			} else {
				System.out.println("\nProduct does not exist.");
			}
			input.close();
		}
	}
}