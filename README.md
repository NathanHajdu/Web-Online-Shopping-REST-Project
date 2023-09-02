# WebOnlineShoppingREST

WebOnlineShoppingREST is a Java Maven project that implements RESTful web services for managing customers, orders, and products in an online shopping system. The project consists of server-side and client-side code to handle various operations through web services.

## Project Structure

The project is organized into three main components:

1. **Customer Services:** These services handle customer-related operations, such as creating, retrieving, updating, and deleting customers.

2. **Order Services:** These services manage order placement, tracking, and cancellation. They allow for creating new orders, retrieving order details, updating order statuses, and canceling orders.

3. **Product Services:** This service is responsible for managing the available products. It provides endpoints for retrieving a list of products, searching for products based on criteria, adding new products, updating existing products, and deleting products from the system.

## RESTful Endpoints

### Customer Services

- `GET /customers`: Retrieve a list of all customers.
- `GET /customers/{customerId}`: Retrieve a specific customer by their ID.
- `POST /customers`: Create a new customer.
- `PUT /customers/{customerId}`: Update an existing customer.
- `DELETE /customers/{customerId}`: Delete an existing customer.

### Order Services

- `GET /orders`: Retrieve a list of all orders.
- `GET /orders/{orderId}`: Retrieve a specific order by its ID.
- `POST /customers/{customerId}/orders`: Create a new order for a customer.
- `GET /customers/{customerId}/orders`: Retrieve all orders for a specific customer.
- `PUT /orders/{orderId}/status`: Update the status of an existing order.
- `DELETE /orders/{orderId}`: Cancel an existing order.

### Product Services

- `GET /products`: Retrieve a list of all products.
- `GET /products/{productId}`: Retrieve a specific product by its ID.
- `POST /products`: Create a new product.
- `PUT /products/{productId}`: Update an existing product.
- `DELETE /products/{productId}`: Delete an existing product.

## Usage

This section provides an overview of how to use the client-side code to interact with the RESTful web services. Detailed examples and explanations are available in the project's source code.

### Client Customer Class

The `ClientCustomerREST` class in the `clientWebOnlineShoppingREST` package provides examples of how to use the customer-related services. It demonstrates actions such as:

- Retrieving all customers.
- Retrieving a customer by ID.
- Creating a new customer.
- Updating an existing customer.
- Deleting a customer.

Please refer to the source code for more details on these operations.

### Client Order Class

The `ClientOrderREST` class in the `clientWebOnlineShoppingREST` package demonstrates how to interact with the order-related services of the WebOnlineShoppingREST project. It provides examples of various actions related to orders, including:

- Retrieving all orders.
- Retrieving an order by its ID.
- Adding a product to an order.
- Retrieving orders for a specific customer.
- Updating the status of an existing order.
- Deleting an order.

Please refer to the source code for detailed examples and explanations of these operations.

### Client Product Class

The `ClientProductREST` class, located in the `clientWebOnlineShoppingREST` package, demonstrates how to interact with the product-related services of the WebOnlineShoppingREST project. It provides examples of various actions related to products, including:

- Retrieving a list of all products.
- Retrieving a product by its ID.
- Creating a new product.
- Updating an existing product.
- Deleting a product.
  
Please refer to the source code for detailed examples and explanations of these operations.

## Getting Started

To get started with this project, follow these steps:

1. Clone the project repository.
2. Build and deploy the server-side code.
3. Run the client-side code to interact with the RESTful web services.

## Dependencies

- Java Maven
- Jersey Client
- Jakarta EE (Java EE)

