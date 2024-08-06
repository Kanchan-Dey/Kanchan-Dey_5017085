-- Create the 'customers' table
BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE customers';
EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE != -942 THEN -- Ignore table not found error
            RAISE;
        END IF;
END;
/

CREATE TABLE customers (
    customer_id NUMBER PRIMARY KEY,
    customer_name VARCHAR2(100),
    balance NUMBER,
    is_vip CHAR(1) DEFAULT 'N' -- 'Y' for VIP, 'N' for not VIP
);

-- Insert sample data into the 'customers' table
INSERT INTO customers (customer_id, customer_name, balance)
VALUES (1, 'Alice Johnson', 12000);

INSERT INTO customers (customer_id, customer_name, balance)
VALUES (2, 'Bob Smith', 8000);

INSERT INTO customers (customer_id, customer_name, balance)
VALUES (3, 'Charlie Brown', 15000);

INSERT INTO customers (customer_id, customer_name, balance)
VALUES (4, 'Diana Prince', 5000);

COMMIT;

-- PL/SQL Block to update VIP status
BEGIN
    -- Update the 'is_vip' flag for customers with a balance over $10,000
    UPDATE customers
    SET is_vip = 'Y'
    WHERE balance > 10000;

    -- Optionally, display the updated customer information
    FOR rec IN (SELECT customer_id, customer_name, balance, is_vip
                FROM customers)
    LOOP
        DBMS_OUTPUT.PUT_LINE(
            'Customer ID: ' || rec.customer_id || 
            ', Name: ' || rec.customer_name ||
            ', Balance: ' || rec.balance ||
            ', VIP Status: ' || rec.is_vip
        );
    END LOOP;
END;
/
