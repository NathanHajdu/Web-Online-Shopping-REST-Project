package webOnlineShopping;

import java.util.ArrayList;
import java.util.List;


public class ServicesHelper {

	// Create Products
	public List<Product> addProducts() {
		
		final List<Product> products = new ArrayList<>();

		Product tv = new Product(1, "Samsung 40 inch HDTV", 400.99, "100% Colour Volume with Quantum Dot");
		Product table = new Product(2, "Glass Table", 150.99, "A sleek and modern glass table for your living room.");
		Product chair = new Product(3, "Poäng Armchair", 60.99, "A comfortable armchair with a stylish design.");
		Product computer = new Product(4, "Lenovo Ideapad", 700.99,
				"The leading gaming laptop, intel core 7, intel iris xe");

		products.add(tv);
		products.add(table);
		products.add(chair);
		products.add(computer);

		return products;
	}

	public List<Order> addOrders() {
	    List<Order> orders = new ArrayList<>();
	    List<Product> products = addProducts();

	    // Create Order 1
	    Order order1 = createOrder(1, 1, "Pending");
	    OrderItem order1Item1 = createOrderItem(products.get(0), 345);
	    order1.getOrderItems().add(order1Item1);

	    // Create Order 2  
	    Order order2 = createOrder(2, 2, "Completed");
	    OrderItem order2Item1 = createOrderItem(products.get(2), 20);
	    OrderItem order2Item2 = createOrderItem(products.get(0), 15);
	    order2.getOrderItems().add(order2Item1);
	    order2.getOrderItems().add(order2Item2);
	    
	    // Create Order 3
	    Order order3 = createOrder(3, 1, "Pending");
	    OrderItem order3Item1 = createOrderItem(products.get(1), 127);
	    order3.getOrderItems().add(order3Item1);

	    orders.add(order1);
	    orders.add(order2);
	    orders.add(order3);

	    return orders;
	}

	private Order createOrder(int orderId, int customerId, String status) {
	    return new Order(orderId, customerId, new ArrayList<>(), status);
	}

	private OrderItem createOrderItem(Product product, int quantity) {
	    return new OrderItem(product, quantity);
	}


	// Create Customers
	public List<Customer> addCustomers() {
		final List<Customer> customers = new ArrayList<>();

		// Get list of products
		List<Product> products = addProducts();

		// Get list of orders
		List<Order> orders = addOrders();

		List<Product> customer1Products = new ArrayList<>();
		customer1Products.add(products.get(0));
		customer1Products.add(products.get(2));

		List<Order> customer1Orders = new ArrayList<>();
		customer1Orders.add(orders.get(0));
		customer1Orders.add(orders.get(2));

		Customer customer1 = new Customer(1, "Nate", "Jose", "nate.joe@gmail.com", "666 Boul. St", customer1Products,
				customer1Orders);

		List<Product> customer2Products = new ArrayList<>();
		customer2Products.add(products.get(1));
		customer2Products.add(products.get(3));

		List<Order> customer2Orders = new ArrayList<>();
		customer2Orders.add(orders.get(1));

		Customer customer2 = new Customer(2, "Saria", "Oka", "saria@gmail.com", "123 Barrel Dr.", customer2Products,
				customer2Orders);

		customers.add(customer1);
		customers.add(customer2);

		return customers;
	}
}
