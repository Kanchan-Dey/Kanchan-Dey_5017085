-- Drop the 'customers' table if it exists
BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE customers';
EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE != -942 THEN -- Ignore table not found error
            RAISE;
        END IF;
END;
/

-- Create the 'customers' table with the LastModified column
CREATE TABLE customers (
    customer_id NUMBER PRIMARY KEY,
    customer_name VARCHAR2(100),
    date_of_birth DATE,
    last_modified DATE
);

-- Insert sample data into the 'customers' table
INSERT INTO customers (customer_id, customer_name, date_of_birth, last_modified)
VALUES (1, 'Alice Johnson', DATE '1980-05-15', SYSDATE);

INSERT INTO customers (customer_id, customer_name, date_of_birth, last_modified)
VALUES (2, 'Bob Smith', DATE '1990-07-20', SYSDATE);

INSERT INTO customers (customer_id, customer_name, date_of_birth, last_modified)
VALUES (3, 'Charlie Brown', DATE '1975-12-10', SYSDATE);

COMMIT;

-- Create the 'UpdateCustomerLastModified' trigger
CREATE OR REPLACE TRIGGER UpdateCustomerLastModified
BEFORE UPDATE ON customers
FOR EACH ROW
BEGIN
    :NEW.last_modified := SYSDATE;
END;
/

-- Example of updating a customer record and checking the LastModified column
DECLARE
    v_last_modified DATE;
BEGIN
    -- Update a customer's record
    UPDATE customers
    SET customer_name = 'Alice Williams'
    WHERE customer_id = 1;

    -- Select the LastModified column value into the local variable
    SELECT last_modified
    INTO v_last_modified
    FROM customers
    WHERE customer_id = 1;

    -- Display the LastModified column value
    DBMS_OUTPUT.PUT_LINE('LastModified for customer with ID 1: ' || TO_CHAR(v_last_modified, 'YYYY-MM-DD HH24:MI:SS'));
END;
/
