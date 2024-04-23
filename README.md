# Banking Application System README

## Overview
This document provides a detailed outline of the banking application system, explaining the data management, user processes, program menus, and admin-specific functionalities. This ensures clear understanding and efficient interaction with the system for both users and administrators.

## Files and Data Management
- **Account Requests File**: Contains new user applications with details such as name, username, initial deposit, age, and credit score.
- **Existing Accounts File**: Includes all data on current users, like account balances and transaction histories.
- **Account Deletion Requests File**: Features accounts marked for deletion by their ID.
- **Serialized Password Keeper**: An encrypted file storing username-password pairs, preventing unauthorized data access.

## Admin Authentication
- **Pre-configured Credentials**: Administrator credentials are hard-coded into the system for secure authentication without user input during runtime.

## User Processes and Interface
- **New User Submission**: Captures and stores data from new user submissions in the Account Requests File with a unique ID crucial for administrative operations.

## Program Menus

### Main Menu

Welcome to the best banking app!
(1) New user registration
(2) Existing user login
(3) Admin access
(4) Exit program

### New User Registration Form

Please complete the registration form:
Username:
Password:
Full Name:
Initial Deposit (minimum $100):
Age:
Credit Score:


Submission redirects to the main menu.

### Existing User Login

Please enter your login details:
Username:
Password:

Successful login leads to the user-specific menu.

### Admin Login

Administrator Login:
Username:
Password:

## Error Handling
- **Invalid Credentials**:

- Incorrect username or password.
Redirecting to the main menu...

## User Navigation and Functionality

### Existing User Functional Menu

Account Management:
(1) Withdraw funds
(2) Deposit funds
(3) View transaction history
(4) Request account deletion
(5) Logout and return to main menu



- **Withdraw Funds**: Check balance before withdrawal; if sufficient, update balance and history.
- **Deposit Funds**: Add specified deposit amount to balance and update history.
- **View Transactions**: Display all transaction records with an option to return to the menu.
- **Request Deletion**: Confirm deletion intent, log out user and add ID to deletion file.
- **Logout**: Terminate session and navigate back to the main menu.

## Admin-Specific Functions

### Admin Functional Menu

Administrative Tasks:
(1) Manage account creation requests
(2) Process account deletion requests
(3) Review all existing accounts
(4) Logout

- **Manage Account Creation**: Approve or reject new accounts displayed by ID.
- **Process Account Deletion**: Opt to delete accounts or return to menu.
- **Review Existing Accounts**: View detailed information about all accounts.
- **Logout**: End session and return to main menu.

This README provides comprehensive guidance on interacting with our banking application system, ensuring clarity for all user actions and administrative functions.
