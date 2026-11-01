FRAUD INVESTIGATION ASSISTANT - BACKEND
===============================================================================================================================================

1. SERVICE EXECUTION ORDER

Start the backend services in the following sequence:

   1. Service Registry (Eureka Server)
   2. Investigation Service
   3. Customer Service
   4. API Gateway
   5. Auth Server

2. EUREKA DASHBOARD

Once all services are running, access the Eureka Dashboard to verify service registration and check the status of all running microservices.

Dashboard URL:
http://localhost:8761

3. API DOCUMENTATION

Swagger UI is integrated into the backend services for API documentation and exploration.

After starting the respective services, access their Swagger UI using the following URL format:

http://localhost:<port>/swagger-ui/index.html

Note:
- Replace <port> with the port number configured for the respective service in its application.properties file.
- Each service has its own Swagger UI endpoint to explore the available APIs and their details.

4. IMPORTANT NOTES

   - Ensure MySQL is running before starting the services.
   - Verify database configurations in the application.properties files.
   - Ensure all services are running successfully before accessing the application through the API Gateway.
