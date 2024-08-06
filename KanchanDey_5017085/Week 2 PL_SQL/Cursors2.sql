-- Drop existing tables if they exist
BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE accounts';
EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE != -942 THEN -- Ignore table not found error
            RAISE;
        END IF;
END;
/

-- Create the 'accounts' table
CREATE TABLE accounts (
    account_id NUMBER PRIMARY KEY,
    account_holder VARCHAR2(100),
    balance NUMBER
);

-- Create a sequence for account_id
CREATE SEQUENCE account_seq
START WITH 1
INCREMENT BY 1;

-- Insert sample data into the 'accounts' table
INSERT INTO accounts (account_id, account_holder, balance)
VALUES (account_seq.NEXTVAL, 'Alice Johnson', 5000);

INSERT INTO accounts (account_id, account_holder, balance)
VALUES (account_seq.NEXTVAL, 'Bob Smith', 3000);

INSERT INTO accounts (account_id, account_holder, balance)
VALUES (account_seq.NEXTVAL, 'Charlie Brown', 2000);

COMMIT;

-- PL/SQL block with an explicit cursor
DECLARE
    -- Define the annual maintenance fee
    annual_fee NUMBER := 50;
    
    -- Cursor to fetch all accounts
    CURSOR ApplyAnnualFee IS
        SELECT account_id, balance
        FROM accounts;
    
    -- Variables to hold cursor data
    v_account_id accounts.account_id%TYPE;
    v_balance accounts.balance%TYPE;
BEGIN
    -- Open and fetch from the cursor
    FOR rec IN ApplyAnnualFee LOOP
        -- Retrieve data into variables
        v_account_id := rec.account_id;
        v_balance := rec.balance;
        
        -- Update the balance by deducting the annual fee
        UPDATE accounts
        SET balance = v_balance - annual_fee
        WHERE account_id = v_account_id;
        
        -- Output the account and updated balance for verification
        DBMS_OUTPUT.PUT_LINE('Account ID: ' || v_account_id);
        DBMS_OUTPUT.PUT_LINE('Old Balance: ' || TO_CHAR(v_balance, 'FM9999.00'));
        DBMS_OUTPUT.PUT_LINE('New Balance: ' || TO_CHAR(v_balance - annual_fee, 'FM9999.00'));
        DBMS_OUTPUT.PUT_LINE('---------------------------------------------');
    END LOOP;
    
    COMMIT;
END;
/
