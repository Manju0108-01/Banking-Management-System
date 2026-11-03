# Banking Management System

## Project Overview
The Banking Management System is a console-based Java application that simulates basic banking operations. It allows users to create accounts, securely log in using a PIN, manage their balances, transfer money, and view transaction history.

## Features
- Create new bank accounts
- Secure PIN-based authentication
- PIN hashing using SHA-256
- Check account balance
- Deposit money
- Withdraw money
- Transfer money between accounts
- View transaction history
- Input validation and error handling
- Store account and transaction data in files
- Automatically load saved data when the application restarts

## Technologies Used
- Java
- Object-Oriented Programming (OOP)
- ArrayList
- BigDecimal
- File Handling
- Java Collections Framework
- SHA-256 Hashing
- LocalDateTime

## Project Structure

Banking-Management-System/
│
├── src/
│   ├── model/
│   │   ├── BankAccount.java
│   │   └── Transaction.java
│   │
│   ├── service/
│   │   └── BankService.java
│   │
│   ├── util/
│   │   └── BankFileManager.java
│   │
│   └── Main.java
│
└── README.md


## How to Run the Project

1. Clone the repository:

git clone YOUR_GITHUB_REPOSITORY_LINK


2. Open the project folder.

3. Navigate to the source directory:

cd src

4. Compile the Java files:

javac -d . Main.java model/*.java util/*.java service/*.java


5. Run the application:

java Main


## Sample Login Credentials

| Account Number | PIN  |

| 1001           | 1234 |
| 1002           | 2345 |
| 1003           | 3456 |

*These sample accounts are created only when no saved accounts exist. Your saved account data may differ.*

## Concepts Demonstrated
- Classes and Objects
- Encapsulation
- Constructors and Methods
- ArrayList
- Packages
- Exception Handling
- File Handling
- Data Persistence
- Date and Time API
- BigDecimal for monetary calculations

## Future Enhancements
- Database integration using PostgreSQL
- GUI using JavaFX
- Admin dashboard
- Account statements
- Improved authentication and security

## Author
**Manju Bhargavi**

B.Tech – Computer Science and Engineering

