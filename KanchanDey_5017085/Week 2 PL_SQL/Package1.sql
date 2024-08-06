-- Drop existing tables if they exist
BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE customers';
EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE != -942 THEN -- Ignore table not found error
            RAISE;
        END IF;
END;
/

-- Create the 'customers' table
CREATE TABLE customers (
    customer_id NUMBER PRIMARY KEY,
    customer_name VARCHAR2(100),
    email VARCHAR2(100),
    balance NUMBER
);

-- Create a sequence for customer_id
CREATE SEQUENCE customer_seq
START WITH 1
INCREMENT BY 1;

-- Insert sample data into the 'customers' table
INSERT INTO customers (customer_id, customer_name, email, balance)
VALUES (customer_seq.NEXTVAL, 'Alice Johnson', 'alice.johnson@example.com', 5000);

INSERT INTO customers (customer_id, customer_name, email, balance)
VALUES (customer_seq.NEXTVAL, 'Bob Smith', 'bob.smith@example.com', 3000);

INSERT INTO customers (customer_id, customer_name, email, balance)
VALUES (customer_seq.NEXTVAL, 'Charlie Brown', 'charlie.brown@example.com', 2000);

COMMIT;

-- Package Specification
CREATE OR REPLACE PACKAGE CustomerManagement AS
    PROCEDURE AddCustomer(p_name IN VARCHAR2, p_email IN VARCHAR2, p_balance IN NUMBER);
    PROCEDURE UpdateCustomerDetails(p_customer_id IN NUMBER, p_name IN VARCHAR2, p_email IN VARCHAR2);
    FUNCTION GetCustomerBalance(p_customer_id IN NUMBER) RETURN NUMBER;
END CustomerManagement;
/

-- Package Body
CREATE OR REPLACE PACKAGE BODY CustomerManagement AS

    PROCEDURE AddCustomer(p_name IN VARCHAR2, p_email IN VARCHAR2, p_balance IN NUMBER) IS
    BEGIN
        INSERT INTO customers (customer_id, customer_name, email, balance)
        VALUES (customer_seq.NEXTVAL, p_name, p_email, p_balance);
        COMMIT;
    EXCEPTION
        WHEN OTHERS THEN
            DBMS_OUTPUT.PUT_LINE('Error adding customer: ' || SQLERRM);
    END AddCustomer;

    PROCEDURE UpdateCustomerDetails(p_customer_id IN NUMBER, p_name IN VARCHAR2, p_email IN VARCHAR2) IS
    BEGIN
        UPDATE customers
        SET customer_name = p_name,
            email = p_email
        WHERE customer_id = p_customer_id;
        
        IF SQL%ROWCOUNT = 0 THEN
            DBMS_OUTPUT.PUT_LINE('Customer ID ' || p_customer_id || ' does not exist.');
        ELSE
            COMMIT;
        END IF;
    EXCEPTION
        WHEN OTHERS THEN
            DBMS_OUTPUT.PUT_LINE('Error updating customer details: ' || SQLERRM);
    END UpdateCustomerDetails;

    FUNCTION GetCustomerBalance(p_customer_id IN NUMBER) RETURN NUMBER IS
        v_balance NUMBER;
    BEGIN
        SELECT balance INTO v_balance
        FROM customers
        WHERE customer_id = p_customer_id;

        RETURN v_balance;
    EXCEPTION
        WHEN NO_DATA_FOUND THEN
            DBMS_OUTPUT.PUT_LINE('Customer ID ' || p_customer_id || ' does not exist.');
            RETURN NULL;
        WHEN OTHERS THEN
            DBMS_OUTPUT.PUT_LINE('Error retrieving customer balance: ' || SQLERRM);
            RETURN NULL;
    END GetCustomerBalance;

END CustomerManagement;
/

-- Example usage of the package

BEGIN
    -- Add a new customer
    CustomerManagement.AddCustomer('David Wilson', 'david.wilson@example.com', 4000);

    -- Update existing customer details
    CustomerManagement.UpdateCustomerDetails(2, 'Robert Smith', 'robert.smith@example.com');

    -- Get customer balance
    DECLARE
        v_balance NUMBER;
    BEGIN
        v_balance := CustomerManagement.GetCustomerBalance(1);
        DBMS_OUTPUT.PUT_LINE('Balance for Customer ID 1: ' || TO_CHAR(v_balance, 'FM9999.00'));
    END;
END;
/
