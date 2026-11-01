
# 🛡️ Fraud Investigation Assistant

## 📌 Overview

Fraud Investigation Assistant is a web-based application designed to detect, analyze, and manage potentially fraudulent financial transactions. It evaluates transactions based on predefined fraud detection rules, calculates a risk score, and classifies transactions into three risk levels: **High, Medium, and Low**.

The application provides an investigation workflow where analysts can review suspicious transactions, analyze risk factors, make investigation decisions, and generate investigation reports in PDF format.

The system follows a **Microservices-based architecture** with JWT authentication and Role-Based Access Control (RBAC) to provide secure access to Admin and Analyst users.

## 🛠️ Tech Stack

| Category | Technologies |
|---|---|
| Frontend | React.js, HTML, CSS |
| Backend | Java, Spring Boot |
| Architecture | Microservices |
| Database | MySQL |
| Authentication | JWT |
| API Gateway | Spring Cloud Gateway |
| Service Registry | Eureka Server |
| API Documentation | Swagger / OpenAPI |
| API Testing | Postman |

## 🏗️ Microservices Overview

The application consists of two core business services and supporting infrastructure services.

**Core Services:**
- **Customer Service:** Handles customer-related information and operations.
- **Investigation Service:** Handles transaction analysis, fraud detection, risk score calculation, and investigation management.

**Supporting Services:**
- **API Gateway:** Acts as a single entry point for accessing all microservices through a common port.
- **Auth Server:** Handles user authentication and JWT token generation.
- **Service Registry:** Uses Eureka Server for service registration and discovery.

## 🔍 Fraud Detection Engine

The Fraud Detection Engine is the core component of the application. It analyzes transactions using six predefined fraud indicators:

- Unknown Device
- Unusual City
- Foreign Transaction
- Unusual Transaction Amount
- Multiple Failed Login Attempts
- High Transaction Frequency

Based on the detected indicators and their assigned risk scores, the system calculates an overall risk score and classifies transactions into:

- 🔴 **High Risk:** 70–100
- 🟡 **Medium Risk:** 30–69
- 🟢 **Low Risk:** 0–29

Transactions are then used to create investigations for further review by analysts.

## 🔐 Authentication & Authorization

The application implements **JWT-based authentication** to secure user access and communication with protected APIs.

Role-Based Access Control (RBAC) is implemented with two roles:

- **Admin:** Responsible for importing customer, device, and transaction data, analyzing transactions, and managing investigations.
- **Analyst:** Responsible for reviewing investigations, analyzing risk factors, making decisions (Approve, Block, or Escalate), and adding investigation comments.

## 📖 API Documentation & Testing

- **Swagger / OpenAPI:** Used for API documentation and interactive exploration of REST endpoints.
- **Postman:** Used for testing REST APIs, validating responses, and verifying backend functionality.
