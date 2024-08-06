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

-- Create the 'loans' table
BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE loans';
EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE != -942 THEN -- Ignore table not found error
            RAISE;
        END IF;
END;
/

CREATE TABLE loans (
    loan_id NUMBER PRIMARY KEY,
    customer_id NUMBER REFERENCES customers(customer_id),
    interest_rate NUMBER, -- Interest rate as a percentage
    loan_amount NUMBER
);

-- Insert sample data into the 'customers' table
INSERT INTO customers (customer_id, customer_name, date_of_birth)
VALUES (1, 'Alice Johnson', DATE '1955-05-15'); -- Age 69

INSERT INTO customers (customer_id, customer_name, date_of_birth)
VALUES (2, 'Bob Smith', DATE '1980-07-20'); -- Age 44

INSERT INTO customers (customer_id, customer_name, date_of_birth)
VALUES (3, 'Charlie Brown', DATE '1948-12-10'); -- Age 75

INSERT INTO customers (customer_id, customer_name, date_of_birth)
VALUES (4, 'Diana Prince', DATE '1975-11-25'); -- Age 48

-- Insert sample data into the 'loans' table
INSERT INTO loans (loan_id, customer_id, interest_rate, loan_amount)
VALUES (101, 1, 5.00, 10000); -- 5% interest rate

INSERT INTO loans (loan_id, customer_id, interest_rate, loan_amount)
VALUES (102, 2, 4.50, 15000); -- 4.5% interest rate

INSERT INTO loans (loan_id, customer_id, interest_rate, loan_amount)
VALUES (103, 3, 6.00, 20000); -- 6% interest rate

INSERT INTO loans (loan_id, customer_id, interest_rate, loan_amount)
VALUES (104, 4, 5.50, 12000); -- 5.5% interest rate

COMMIT;

-- PL/SQL Block to apply a 1% discount to the interest rate for customers over 60
DECLARE
    CURSOR customer_cursor IS
        SELECT c.customer_id, l.loan_id, l.interest_rate
        FROM customers c
        JOIN loans l ON c.customer_id = l.customer_id
        WHERE ADD_MONTHS(SYSDATE, -12*60) > c.date_of_birth; -- Check if age is over 60

    TYPE customer_rec IS RECORD (
        customer_id   customers.customer_id%TYPE,
        loan_id        loans.loan_id%TYPE,
        interest_rate  loans.interest_rate%TYPE
    );

    v_customer customer_rec;
BEGIN
    OPEN customer_cursor;

    LOOP
        FETCH customer_cursor INTO v_customer;
        EXIT WHEN customer_cursor%NOTFOUND;

        -- Apply a 1% discount to the interest rate
        UPDATE loans
        SET interest_rate = v_customer.interest_rate - 1
        WHERE loan_id = v_customer.loan_id;

        -- Optionally, display updated loan information
        DBMS_OUTPUT.PUT_LINE(
            'Customer ID: ' || v_customer.customer_id || 
            ', Loan ID: ' || v_customer.loan_id ||
            ', New Interest Rate: ' || TO_CHAR(v_customer.interest_rate - 1, 'FM999.00') || '%'
        );
    END LOOP;

    CLOSE customer_cursor;

EXCEPTION
    WHEN OTHERS THEN
        -- Handle any exceptions
        DBMS_OUTPUT.PUT_LINE('An error occurred: ' || SQLERRM);
        IF customer_cursor%ISOPEN THEN
            CLOSE customer_cursor;
        END IF;
END;
/
