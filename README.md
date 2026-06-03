# POSsystem

## Overview

`POSsystem` is a Spring Boot-based Point of Sale (POS) backend that manages stores, branches, products, inventory, orders, refunds, shift reports, customers, and employee users. The project uses JWT authentication and role-based request authorization to protect API operations.

The backend exposes RESTful endpoints for store and branch administration, product and inventory management, sales order lifecycle, refund tracking, cashier shift reporting, and customer management.

## Technology Stack

- Java 21
- Spring Boot 3.5.14
- Spring Web
- Spring Data JPA
- Spring Security
- Spring Validation
- Spring Boot Actuator
- Spring Boot DevTools
- MySQL Connector/J
- PostgreSQL Driver
- JWT via `io.jsonwebtoken:jjwt`
- Lombok for model and DTO boilerplate
- Razorpay Java SDK for payment integration

## Configuration

The application uses `src/main/resources/application.properties` for configuration. Default values in this project include:

- `server.port=5000`
- `spring.datasource.url=jdbc:mysql://localhost:3306/pos_yt`
- `spring.datasource.username=root`
- `spring.datasource.password=root`
- `spring.jpa.hibernate.ddl-auto=update`
- `spring.jpa.show-sql=true`

> Update the datasource settings to match your local or production database.

## Running Locally

1. Ensure Java 21 is installed.
2. Configure `application.properties` with the correct database connection.
3. Build and run with Maven wrapper:

```bash
./mvnw spring-boot:run
```

4. The API will be available at `http://localhost:5000`.

## Architecture and Logic

The application follows a layered architecture:

- `controller` layer handles REST requests and maps endpoints.
- `service` layer implements business logic such as product creation, order processing, refund calculation, and shift report generation.
- `models` represent database entities and persistence relationships.
- `payload.dto` contains DTOs used for request/response payloads.
- `payload.response` contains shared response wrappers like `ApiResponse` and `AuthResponse`.
- `configuration` contains security and JWT filter logic.
- `mapper` classes convert between entities and DTOs.

### Authentication and Security

- `AuthController` exposes `/auth/signup` and `/auth/login`.
- JWT tokens are returned in `AuthResponse.jwt` after successful authentication.
- `SecurityConfig` protects endpoints under `/api/**` and requires authentication.
- `/auth/**` is public, while `/api/**` requires a valid JWT.
- `StoreController` includes endpoints that depend on user roles and authenticated user context.

## API Catalog

### Authentication

#### POST `/auth/signup`
- Request body: `UserDto`
- Response: `AuthResponse`
- Registers a new user and issues a JWT.

#### POST `/auth/login`
- Request body: `UserDto`
- Response: `AuthResponse`
- Authenticates a user and returns a JWT.

### User

#### GET `/api/users/profile`
- Header: `Authorization: Bearer <token>`
- Response: `UserDto`
- Returns profile information for the authenticated user.

#### GET `/api/users/{id}`
- Header: `Authorization: Bearer <token>`
- Response: `UserDto`
- Returns data for the specified user ID.

### Store

#### POST `/api/store/create`
- Header: `Authorization: Bearer <token>`
- Request body: `StoreDto`
- Response: `StoreDto`
- Creates a new store associated with the authenticated user.

#### GET `/api/store`
- Header: `Authorization: Bearer <token>`
- Response: `List<StoreDto>`
- Returns all stores.

#### GET `/api/store/admin`
- Header: `Authorization: Bearer <token>`
- Response: `StoreDto`
- Returns the store linked to the authenticated admin.

#### GET `/api/store/emmployee`
- Header: `Authorization: Bearer <token>`
- Response: `StoreDto`
- Returns the store linked to the authenticated employee.

#### PUT `/api/store/{id}`
- Request body: `StoreDto`
- Response: `StoreDto`
- Updates store information.

#### DELETE `/api/store/{id}`
- Response: `ApiResponse`
- Deletes a store.

#### PUT `/api/store/{id}/moderate`
- Request param: `StoreStatus status`
- Response: `StoreDto`
- Moderates store status by ID.

#### GET `/api/store/{id}`
- Header: `Authorization: Bearer <token>`
- Response: `StoreDto`
- Returns a store by ID.

### Branch

#### POST `/api/branches`
- Request body: `BranchDto`
- Response: `BranchDto`
- Creates a new branch.

#### GET `/api/branches/{id}`
- Response: `BranchDto`
- Retrieves branch details by ID.

#### GET `/api/branches/store/{storeId}`
- Response: `List<BranchDto>`
- Lists branches for a store.

#### PUT `/api/branches/{id}`
- Request body: `BranchDto`
- Response: `BranchDto`
- Updates a branch.

#### DELETE `/api/branches/{id}`
- Response: `ApiResponse`
- Deletes a branch.

### Category

#### POST `/api/categories`
- Request body: `CategoryDto`
- Response: `CategoryDto`
- Creates a category for a store.

#### GET `/api/categories/store/{storeId}`
- Response: `List<CategoryDto>`
- Lists categories in a store.

#### PUT `/api/categories/{id}`
- Request body: `CategoryDto`
- Response: `CategoryDto`
- Updates a category.

#### DELETE `/api/categories/{id}`
- Response: `ApiResponse`
- Deletes a category.

### Product

#### POST `/api/products`
- Header: `Authorization: Bearer <token>`
- Request body: `ProductDto`
- Response: `ProductDto`
- Creates a new product.

#### GET `/api/products/store/{storeId}`
- Header: `Authorization: Bearer <token>`
- Response: `List<ProductDto>`
- Retrieves products by store.

#### PATCH `/api/products/{id}`
- Header: `Authorization: Bearer <token>`
- Request body: `ProductDto`
- Response: `ProductDto`
- Updates product details.

#### GET `/api/products/store/{storeId}/search?keyword=...`
- Header: `Authorization: Bearer <token>`
- Response: `List<ProductDto>`
- Searches products by keyword inside a store.

#### DELETE `/api/products/{id}`
- Header: `Authorization: Bearer <token>`
- Response: `ApiResponse`
- Deletes a product.

### Inventory

#### POST `/api/inventories`
- Request body: `InventoryDto`
- Response: `InventoryDto`
- Creates a new inventory record.

#### PUT `/api/inventories/{id}`
- Request body: `InventoryDto`
- Response: `InventoryDto`
- Updates inventory quantity or product assignment.

#### DELETE `/api/inventories/{id}`
- Response: `ApiResponse`
- Deletes an inventory record.

#### GET `/api/inventories/branch/{branchId}`
- Response: `List<InventoryDto>`
- Lists inventory by branch.

#### GET `/api/inventories/branch/{branchId}/product/{productId}`
- Response: `InventoryDto`
- Retrieves a single inventory item by branch and product.

### Customer

#### POST `/api/customers`
- Request body: `Customer`
- Response: `Customer`
- Creates a new customer.

#### PUT `/api/customers/{id}`
- Request body: `Customer`
- Response: `Customer`
- Updates a customer.

#### DELETE `/api/customers/{id}`
- Response: `ApiResponse`
- Deletes a customer.

#### GET `/api/customers`
- Response: `List<Customer>`
- Lists all customers.

#### GET `/api/customers/{id}`
- Response: `Customer`
- Retrieves a customer by ID.

#### GET `/api/customers/search?q=...`
- Response: `List<Customer>`
- Searches customers by keyword.

### Employee

#### POST `/api/employee/store/{storeId}`
- Request body: `UserDto`
- Response: `UserDto`
- Creates an employee for a store.

#### POST `/api/employee/branch/{branchId}`
- Request body: `UserDto`
- Response: `UserDto`
- Creates an employee for a branch.

#### PUT `/api/employee/{id}`
- Request body: `UserDto`
- Response: `Users`
- Updates employee user data.

#### DELETE `/api/employee/{id}`
- Response: `ApiResponse`
- Deletes an employee.

#### GET `/api/employee/store/{storeId}`
- Response: `List<UserDto>`
- Lists store employees with optional role filter.

#### GET `/api/employee/branch/{branchId}`
- Response: `List<UserDto>`
- Lists branch employees with optional role filter.

### Orders

#### POST `/api/orders`
- Request body: `OrderDto`
- Response: `OrderDto`
- Creates a new order with items, cashier, customer, payment type, and branch details.

#### GET `/api/orders/{id}`
- Response: `OrderDto`
- Retrieves a single order by ID.

#### GET `/api/orders/branch/{branchId}`
- Query params: `customerId`, `cashierId`, `paymentType`, `status`
- Response: `List<OrderDto>`
- Lists orders by branch with optional filters.

#### GET `/api/orders/cashier/{cashierId}`
- Response: `List<OrderDto>`
- Lists orders created by a cashier.

#### GET `/api/orders/today/branch/{id}`
- Response: `List<OrderDto>`
- Lists today’s orders for a branch.

#### GET `/api/orders/customer/{id}`
- Response: `List<OrderDto>`
- Lists orders placed by a customer.

#### GET `/api/orders/recent/{id}`
- Response: `List<OrderDto>`
- Retrieves the 5 most recent orders for a branch.

### Refunds

#### POST `/api/refunds`
- Request body: `RefundDto`
- Response: `RefundDto`
- Creates a refund record.

#### GET `/api/refunds`
- Response: `List<RefundDto>`
- Lists all refunds.

#### GET `/api/refunds/cashier/{id}`
- Response: `List<RefundDto>`
- Lists refunds processed by a cashier.

#### GET `/api/refunds/branch/{id}`
- Response: `List<RefundDto>`
- Lists refunds for a branch.

#### DELETE `/api/refunds/{id}`
- Response: `ApiResponse`
- Deletes a refund.

#### GET `/api/refunds/{id}`
- Response: `RefundDto`
- Retrieves a refund by ID.

#### GET `/api/refunds/shift/{id}`
- Response: `List<RefundDto>`
- Lists refunds associated with a shift report.

#### GET `/api/refunds/cashier/{id}/range?from=<datetime>&to=<datetime>`
- Response: `List<RefundDto>`
- Lists refunds by cashier within a date range.

### Shift Reports

#### POST `/api/shiftReports/start`
- Response: `ShiftReportDto`
- Starts a new cashier shift.

#### PATCH `/api/shiftReports/end`
- Response: `ShiftReportDto`
- Ends a shift and finalizes shift totals.

#### GET `/api/shiftReports/current`
- Response: `ShiftReportDto`
- Returns the current shift progress.

#### GET `/api/shiftReports/cashier/{cashierId}/by-date?date=<date>`
- Response: `ShiftReportDto`
- Retrieves a shift report for a cashier on a specific date.

#### GET `/api/shiftReports/cashier/{cashierId}`
- Response: `List<ShiftReportDto>`
- Lists all shift reports for a cashier.

#### GET `/api/shiftReports/branch/{branchId}`
- Response: `List<ShiftReportDto>`
- Lists shift reports for a branch.

#### GET `/api/shiftReports/{id}`
- Response: `ShiftReportDto`
- Retrieves a shift report by ID.

## Domain Models

### Store

- `id`
- `brand`
- `description`
- `storeType`
- `status` (`StoreStatus`)
- `contact` (`StoreContact`)
- `storeAdmin` and associated store/branch relationships
- `createdAt`, `updatedAt`

### Branch

- `id`
- `name`, `address`, `phone`, `email`
- `workingDays`, `openingTime`, `closingTime`
- `store` association
- `manager`
- `createdAt`, `updatedAt`

### Product

- `id`, `name`, `sku`, `description`
- `mrp`, `sellingPrice`, `brand`, `imageUrl`
- `category`, `store`, status metadata
- track creation and update timestamps

### Category

- `id`, `name`
- `store` association

### Inventory

- `id`, `branch`, `product`, `quantity`
- `lastUpdated`

### Order

- `id`, `totalAmount`, `createdAt`
- `branch`, `cashier`, `customer`
- `paymentType`, `status` (`OrderStatus`)
- `items` list of `OrderItem`

### OrderItem

- `id`, `quantity`, `price`
- `product`, `order`

### Customer

- `id`, `fullName`, `email`, `phone`
- creation and update timestamps

### User

- `id`, `username`, `email`, `phone`
- `role` (`UserRole`)
- `password`
- links to `storeId`, `branchId`
- timestamps for creation, update, last login

### Refund

- `id`, `reason`, `amount`
- associated `order`, `shiftReport`, `cashier`, `branch`
- `paymentType`, `createdAt`

### ShiftReport

- `id`, `shiftStart`, `shiftEnd`
- `totalSales`, `totalRefunds`, `netSales`, `totalOrders`
- `cashier`, `branch`
- `paymentSummaries`, `topSellingProducts`, `recentOrders`, `refunds`

### PaymentSummary

- `paymentType`, `totalAmount`, `transactionCount`, `percentage`

### StoreContact

- `address`, `phone`, `email`

## Notable Logic

- Orders can be filtered by branch, cashier, customer, payment type, and order status.
- Products are scoped to stores and support keyword search within a store.
- Inventory is stored per branch and per product.
- Refunds can be queried by cashier, branch, shift report, and date range.
- Shift reports track running totals and can be queried by cashier or branch.
- User authentication is JWT-based and enforced across `/api/**` endpoints.
- Passwords are encoded with BCrypt via `PasswordEncoder`.

## Project Modules

- `com.sarthak.POSsystem.controller` — REST endpoint definitions.
- `com.sarthak.POSsystem.service` — business operations.
- `com.sarthak.POSsystem.models` — persistence entities.
- `com.sarthak.POSsystem.payload.dto` — data transfer objects.
- `com.sarthak.POSsystem.payload.response` — common API response wrappers.
- `com.sarthak.POSsystem.configuration` — security and request filtering.
- `com.sarthak.POSsystem.mapper` — entity/DTO conversions.

## Future Enhancements

- Add request validation annotations for DTOs and request bodies.
- Add global exception handling with standardized error payloads.
- Add Swagger/OpenAPI documentation.
- Add tenant isolation if multiple stores are managed by different organizations.
- Add role-specific access control for branch/store administration endpoints.

## Notes

- The current configuration uses MySQL by default but also includes the PostgreSQL JDBC driver.
- The app runs on port `5000`.
- JWT token enforcement is configured in `SecurityConfig` and validated by `JwtValidator`.
- Update `application.properties` before connecting to a production database.
