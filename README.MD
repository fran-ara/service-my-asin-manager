## MyAsinManager Backend Service
This service is responsible for providing the necessary functionality for the MyAsinManager platform, including fetching products from the Amazon SP API, storing and updating product information, and handling CRUD operations for tags and their relations to products. Additionally, the service serves the frontend using Server-Sent Events to keep the platform updated in real-time.

Running the Application
The spring-boot application can be run using the following command:

`mvn spring-boot:run`

## Functionality

- Fetching products from the Amazon SP API and storing them in the database:The service retrieves information about products from the Amazon SP API and stores them in the database for later use.
- Asynchronous API calls: The service makes asynchronous calls to the API in order to keep the product information up-to-date.
- CRUD operations for Tags: The service provides functionality for creating, reading, updating, and deleting tags, as well as managing their many-to-many relationship with products.
- Server-Sent Events: The service serves the frontend using Server-Sent Events, allowing the platform to stay updated in real-time.

## Dependencies
Spring Boot
Maven

