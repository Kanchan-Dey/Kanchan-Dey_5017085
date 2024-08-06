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
    account_holder VARCHAR2(100),
    balance NUMBER
);

-- Insert sample data into the 'accounts' table
INSERT INTO accounts (account_id, account_holder, balance)
VALUES (1, 'Alice Johnson', 5000);

INSERT INTO accounts (account_id, account_holder, balance)
VALUES (2, 'Bob Smith', 3000);

INSERT INTO accounts (account_id, account_holder, balance)
VALUES (3, 'Charlie Brown', 2000);

COMMIT;

-- Create the 'HasSufficientBalance' function
CREATE OR REPLACE FUNCTION HasSufficientBalance(
    p_account_id IN NUMBER,
    p_amount IN NUMBER
) RETURN BOOLEAN AS
    v_balance NUMBER;
BEGIN
    -- Retrieve the balance of the specified account
    SELECT balance INTO v_balance
    FROM accounts
    WHERE account_id = p_account_id;

    -- Check if the balance is at least the specified amount
    RETURN v_balance >= p_amount;
EXCEPTION
    WHEN NO_DATA_FOUND THEN
        -- If no data found for the given account_id, return FALSE
        RETURN FALSE;
    WHEN OTHERS THEN
        -- Handle other exceptions by returning FALSE
        RETURN FALSE;
END;
/

-- Example of calling the function
DECLARE
    v_account_id NUMBER := 1;
    v_amount NUMBER := 4000;
    v_has_sufficient_balance BOOLEAN;
BEGIN
    -- Check if the account has sufficient balance
    v_has_sufficient_balance := HasSufficientBalance(v_account_id, v_amount);
    
    IF v_has_sufficient_balance THEN
        DBMS_OUTPUT.PUT_LINE('Account ' || v_account_id || ' has sufficient balance.');
    ELSE
        DBMS_OUTPUT.PUT_LINE('Account ' || v_account_id || ' does not have sufficient balance.');
    END IF;
END;
/
