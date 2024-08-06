-- Create the 'Customers' table
BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE Customers';
EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE != -942 THEN -- Ignore table not found error
            RAISE;
        END IF;
END;
/

CREATE TABLE Customers (
    customer_id NUMBER PRIMARY KEY,
    customer_name VARCHAR2(100),
    date_of_birth DATE
);

-- Insert sample data into the 'Customers' table
INSERT INTO Customers (customer_id, customer_name, date_of_birth)
VALUES (1, 'Alice Johnson', DATE '1980-05-15');

INSERT INTO Customers (customer_id, customer_name, date_of_birth)
VALUES (2, 'Bob Smith', DATE '1990-07-20');

INSERT INTO Customers (customer_id, customer_name, date_of_birth)
VALUES (3, 'Charlie Brown', DATE '1975-12-10');

COMMIT;

-- Create the 'AddNewCustomer' stored procedure
CREATE OR REPLACE PROCEDURE AddNewCustomer(
    p_customer_id IN NUMBER,
    p_customer_name IN VARCHAR2,
    p_date_of_birth IN DATE
) AS
BEGIN
    BEGIN
        -- Try to insert the new customer
        INSERT INTO Customers (customer_id, customer_name, date_of_birth)
        VALUES (p_customer_id, p_customer_name, p_date_of_birth);

        -- Commit the transaction if no errors
        COMMIT;

        DBMS_OUTPUT.PUT_LINE('Customer added successfully: ' || p_customer_name);
    EXCEPTION
        WHEN DUP_VAL_ON_INDEX THEN
            -- Handle the case where the customer_id already exists
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error: Customer ID ' || p_customer_id || ' already exists.');
        WHEN OTHERS THEN
            -- Handle any other exceptions
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('An error occurred: ' || SQLERRM);
    END;
END;
/

-- Example of calling the procedure
BEGIN
    AddNewCustomer(4, 'Diana Prince', DATE '1985-11-25'); -- Add a new customer
    AddNewCustomer(1, 'Emily Davis', DATE '1992-03-14'); -- Try to add a customer with an existing ID
END;
/
