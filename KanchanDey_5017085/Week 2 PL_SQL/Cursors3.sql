-- Drop existing tables if they exist
BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE loans';
EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE != -942 THEN -- Ignore table not found error
            RAISE;
        END IF;
END;
/

-- Create the 'loans' table
CREATE TABLE loans (
    loan_id NUMBER PRIMARY KEY,
    customer_id NUMBER,
    loan_amount NUMBER,
    interest_rate NUMBER,  -- Interest rate in percentage
    loan_term NUMBER -- Loan term in months
);

-- Create a sequence for loan_id
CREATE SEQUENCE loan_seq
START WITH 1
INCREMENT BY 1;

-- Insert sample data into the 'loans' table
INSERT INTO loans (loan_id, customer_id, loan_amount, interest_rate, loan_term)
VALUES (loan_seq.NEXTVAL, 101, 10000, 5.0, 24);

INSERT INTO loans (loan_id, customer_id, loan_amount, interest_rate, loan_term)
VALUES (loan_seq.NEXTVAL, 102, 15000, 4.5, 36);

INSERT INTO loans (loan_id, customer_id, loan_amount, interest_rate, loan_term)
VALUES (loan_seq.NEXTVAL, 103, 20000, 6.0, 48);

COMMIT;

-- PL/SQL block with an explicit cursor
DECLARE
    -- Define the new interest rate policy
    new_interest_rate NUMBER := 4.0;  -- New policy interest rate in percentage
    
    -- Cursor to fetch all loans
    CURSOR UpdateLoanInterestRates IS
        SELECT loan_id, interest_rate
        FROM loans;
    
    -- Variables to hold cursor data
    v_loan_id loans.loan_id%TYPE;
    v_old_interest_rate loans.interest_rate%TYPE;
BEGIN
    -- Open and fetch from the cursor
    FOR rec IN UpdateLoanInterestRates LOOP
        -- Retrieve data into variables
        v_loan_id := rec.loan_id;
        v_old_interest_rate := rec.interest_rate;
        
        -- Update the interest rate based on the new policy
        UPDATE loans
        SET interest_rate = new_interest_rate
        WHERE loan_id = v_loan_id;
        
        -- Output the loan ID, old interest rate, and new interest rate for verification
        DBMS_OUTPUT.PUT_LINE('Loan ID: ' || v_loan_id);
        DBMS_OUTPUT.PUT_LINE('Old Interest Rate: ' || TO_CHAR(v_old_interest_rate, 'FM999.00') || '%');
        DBMS_OUTPUT.PUT_LINE('New Interest Rate: ' || TO_CHAR(new_interest_rate, 'FM999.00') || '%');
        DBMS_OUTPUT.PUT_LINE('---------------------------------------------');
    END LOOP;
    
    COMMIT;
END;
/
