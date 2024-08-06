-- Create the 'accounts' table
BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE accounts';
EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE != -942 THEN -- Ignore table not found error
            RAISE;
        END IF;
END;
/

CREATE TABLE accounts (
    account_id NUMBER PRIMARY KEY,
    account_name VARCHAR2(100),
    balance NUMBER
);

-- Insert sample data into the 'accounts' table
INSERT INTO accounts (account_id, account_name, balance)
VALUES (1, 'Alice Johnson', 5000);

INSERT INTO accounts (account_id, account_name, balance)
VALUES (2, 'Bob Smith', 3000);

INSERT INTO accounts (account_id, account_name, balance)
VALUES (3, 'Charlie Brown', 2000);

COMMIT;

-- Create the 'SafeTransferFunds' stored procedure
CREATE OR REPLACE PROCEDURE SafeTransferFunds(
    p_from_account_id IN NUMBER,
    p_to_account_id IN NUMBER,
    p_amount IN NUMBER
) AS
    v_from_balance NUMBER;
    v_to_balance NUMBER;
BEGIN
    -- Start a transaction
    BEGIN
        -- Check if the from account exists and get the current balance
        SELECT balance INTO v_from_balance
        FROM accounts
        WHERE account_id = p_from_account_id FOR UPDATE;
        
        -- Check if the to account exists and get the current balance
        SELECT balance INTO v_to_balance
        FROM accounts
        WHERE account_id = p_to_account_id FOR UPDATE;

        -- Check if there are sufficient funds
        IF v_from_balance < p_amount THEN
            RAISE_APPLICATION_ERROR(-20001, 'Insufficient funds in the source account.');
        END IF;

        -- Perform the fund transfer
        UPDATE accounts
        SET balance = balance - p_amount
        WHERE account_id = p_from_account_id;

        UPDATE accounts
        SET balance = balance + p_amount
        WHERE account_id = p_to_account_id;

        -- Commit the transaction if no errors
        COMMIT;
        
        DBMS_OUTPUT.PUT_LINE('Transfer successful: ' || p_amount || ' transferred from account ' || p_from_account_id || ' to account ' || p_to_account_id);
    EXCEPTION
        WHEN NO_DATA_FOUND THEN
            RAISE_APPLICATION_ERROR(-20002, 'One or both accounts do not exist.');
        WHEN OTHERS THEN
            -- Rollback transaction in case of error
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('An error occurred: ' || SQLERRM);
    END;
END;
/

-- Example of calling the procedure
BEGIN
    SafeTransferFunds(3, 1, 3000); -- Transfer $1000 from Alice Johnson to Bob Smith
    SafeTransferFunds(2, 3, 500);  -- Transfer $500 from Bob Smith to Charlie Brown
END;
/
