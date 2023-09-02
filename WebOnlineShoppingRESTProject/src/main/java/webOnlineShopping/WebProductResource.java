package webOnlineShopping;

import java.util.List;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("shopping")
public class WebProductResource {

	private ServicesHelper servicesHelper = new ServicesHelper();
	private List<Product> products;

	public WebProductResource() {
		products = servicesHelper.addProducts();
	}

	/*------- Product Services -------*/

	// Get all Products
	@GET
	@Path("/products")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Product> getAllProducts() {
		return products;
	}

	// Get Product by ID
	@GET
	@Path("/products/{productId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getProductById(@PathParam("productId") int productId) {
		for (Product product : products) {
			if (product.getProductId() == productId) {
				return Response.ok(product).build();
			}
		}
		return Response.status(Response.Status.NOT_FOUND).entity("Product not found").build();
	}

	// Get Product by ID FORM
	@GET
	@Path("searchProduct")
	@Produces(MediaType.APPLICATION_JSON)
	public Product searchProductInfo(@QueryParam("productId") int productId) {

		 Product output = null;
		    for (Product product : products) {
		        if (product.getProductId() == productId) {
		            output = product;
		            break;
		        }
		    }
		    return output;
	}


	// Create new Product
	@POST
	@Path("/products")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createProduct(Product product) {
	    products.add(product);
	    return Response.ok(products).build();
	}

	// Add new product form
	@POST
	@Path("addProduct")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Product> addNewProductInfo(@FormParam("productId") int productId,
										   @FormParam("productName") String productName, 
										   @FormParam("productPrice") double productPrice,
										   @FormParam("productDesc") String productDesc) {

		Product product = new Product();
		product.setProductId(productId);
		product.setProductName(productName);
		product.setProductPrice(productPrice);
		product.setProductDesc(productDesc);

		products.add(product);

		return products;

	}

	// Update existing Product
	@PUT
	@Path("/products/{productId}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateProduct(@PathParam("productId") int productId, Product updatedProduct) {
		for (Product product : products) {
			if (product.getProductId() == productId) {
				product.setProductName(updatedProduct.getProductName());
				product.setProductPrice(updatedProduct.getProductPrice());
				product.setProductDesc(updatedProduct.getProductDesc());
				return Response.status(Response.Status.NO_CONTENT).build();
			}
		}
		return Response.status(Response.Status.NOT_FOUND).entity("Product not found").build();
	}
	
	@PUT
	@Path("/updateProduct")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Product> updateProductInfo(@QueryParam("productId") int productId, 
										  @QueryParam("productName") String productName,
										  @QueryParam("productPrice") double productPrice,
										  @QueryParam("productDesc") String productDesc){
		
		for (Product record : products) {
			if (record.getProductId() == productId) {
				record.setProductName(productName);
				record.setProductPrice(productPrice);
				record.setProductDesc(productDesc);
			}
		}

		return products;
	}

	// Delete existing Product
	@DELETE
	@Path("/products/{productId}")
	public Response deleteProduct(@PathParam("productId") int productId) {
		for (Product product : products) {
			if (product.getProductId() == productId) {
				products.remove(product);
				return Response.status(Response.Status.NO_CONTENT).build();
			}
		}
		return Response.status(Response.Status.NOT_FOUND).entity("Product not found").build();
	}
	
	@DELETE
	@Path("/removeProduct")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Product> removeProductInfo(@QueryParam("productId") int productId){
		
		for (Product record : products) {
			if (record.getProductId() == productId) {
				products.remove(record);
				break;
			}
		}
		
		return products;
	}
}
