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
    customer_id NUMBER PRIMARY KEY,
    customer_name VARCHAR2(100),
    loan_id NUMBER,
    due_date DATE
);

-- Insert sample data into the 'loans' table
INSERT INTO loans (customer_id, customer_name, loan_id, due_date)
VALUES (1, 'Alice Johnson', 101, SYSDATE + 15);

INSERT INTO loans (customer_id, customer_name, loan_id, due_date)
VALUES (2, 'Bob Smith', 102, SYSDATE + 25);

INSERT INTO loans (customer_id, customer_name, loan_id, due_date)
VALUES (3, 'Charlie Brown', 103, SYSDATE + 35);

INSERT INTO loans (customer_id, customer_name, loan_id, due_date)
VALUES (4, 'Diana Prince', 104, SYSDATE + 10);

COMMIT;

-- PL/SQL Block to fetch and print reminder messages
DECLARE
    -- Define a cursor to fetch loans due in the next 30 days
    CURSOR due_loan_cursor IS
        SELECT customer_id, customer_name, loan_id, due_date
        FROM loans
        WHERE due_date BETWEEN SYSDATE AND SYSDATE + 30;

    -- Define a record type to hold cursor data
    loan_record due_loan_cursor%ROWTYPE;

BEGIN
    -- Open the cursor and fetch data
    OPEN due_loan_cursor;

    -- Loop through the results
    LOOP
        FETCH due_loan_cursor INTO loan_record;
        EXIT WHEN due_loan_cursor%NOTFOUND;

        -- Print reminder message for each customer
        DBMS_OUTPUT.PUT_LINE(
            'Reminder: Loan ID ' || loan_record.loan_id || 
            ' for customer ' || loan_record.customer_name ||
            ' (ID: ' || loan_record.customer_id || ') ' ||
            ' is due on ' || TO_CHAR(loan_record.due_date, 'YYYY-MM-DD') || 
            '. Please make sure to make the payment.'
        );
    END LOOP;

    -- Close the cursor
    CLOSE due_loan_cursor;

EXCEPTION
    WHEN OTHERS THEN
        -- Handle any exceptions
        DBMS_OUTPUT.PUT_LINE('An error occurred: ' || SQLERRM);
        IF due_loan_cursor%ISOPEN THEN
            CLOSE due_loan_cursor;
        END IF;
END;
/
