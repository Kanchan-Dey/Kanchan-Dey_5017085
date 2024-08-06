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
    date_of_birth DATE
);

-- Insert sample data into the 'customers' table
INSERT INTO customers (customer_id, customer_name, date_of_birth)
VALUES (1, 'Alice Johnson', DATE '1980-05-15');

INSERT INTO customers (customer_id, customer_name, date_of_birth)
VALUES (2, 'Bob Smith', DATE '1990-07-20');

INSERT INTO customers (customer_id, customer_name, date_of_birth)
VALUES (3, 'Charlie Brown', DATE '1975-12-10');

COMMIT;

-- Create the 'CalculateAge' function
CREATE OR REPLACE FUNCTION CalculateAge(
    p_date_of_birth IN DATE
) RETURN NUMBER AS
    v_age NUMBER;
    v_current_date DATE := SYSDATE;
BEGIN
    -- Calculate the age
    v_age := EXTRACT(YEAR FROM v_current_date) - EXTRACT(YEAR FROM p_date_of_birth);
    
    -- Adjust if the current date is before the birthday in the current year
    IF (EXTRACT(MONTH FROM v_current_date) < EXTRACT(MONTH FROM p_date_of_birth)) OR
       (EXTRACT(MONTH FROM v_current_date) = EXTRACT(MONTH FROM p_date_of_birth) AND 
        EXTRACT(DAY FROM v_current_date) < EXTRACT(DAY FROM p_date_of_birth)) THEN
        v_age := v_age - 1;
    END IF;
    
    RETURN v_age;
END;
/

-- Example of calling the function
DECLARE
    v_customer_dob DATE;
    v_age NUMBER;
BEGIN
    -- Get the date of birth for a customer
    SELECT date_of_birth INTO v_customer_dob
    FROM customers
    WHERE customer_id = 1;
    
    -- Calculate the age using the function
    v_age := CalculateAge(v_customer_dob);
    
    DBMS_OUTPUT.PUT_LINE('Age of customer with ID 1: ' || v_age);
END;
/
