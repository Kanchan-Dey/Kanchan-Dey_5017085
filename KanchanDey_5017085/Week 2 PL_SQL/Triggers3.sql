-- Drop existing tables if they exist
BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE transactions';
EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE != -942 THEN -- Ignore table not found error
            RAISE;
        END IF;
END;
/

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

-- Create the 'transactions' table
CREATE TABLE transactions (
    transaction_id NUMBER PRIMARY KEY,
    account_id NUMBER,
    transaction_date DATE,
    amount NUMBER,
    description VARCHAR2(255),
    CONSTRAINT fk_account FOREIGN KEY (account_id) REFERENCES accounts(account_id)
);

-- Create a sequence for transaction_id
CREATE SEQUENCE transaction_seq
START WITH 1
INCREMENT BY 1;

-- Create the 'CheckTransactionRules' trigger
CREATE OR REPLACE TRIGGER CheckTransactionRules
BEFORE INSERT ON transactions
FOR EACH ROW
DECLARE
    v_balance NUMBER;
BEGIN
    -- Retrieve the current balance of the account
    BEGIN
        SELECT balance INTO v_balance
        FROM accounts
        WHERE account_id = :NEW.account_id;
    EXCEPTION
        WHEN NO_DATA_FOUND THEN
            RAISE_APPLICATION_ERROR(-20003, 'Account does not exist.');
    END;

    -- Check if the transaction is a withdrawal (negative amount)
    IF :NEW.amount < 0 THEN
        -- Ensure the withdrawal does not exceed the account balance
        IF v_balance + :NEW.amount < 0 THEN
            RAISE_APPLICATION_ERROR(-20001, 'Withdrawal exceeds account balance.');
        END IF;

    -- Check if the transaction is a deposit (positive amount)
    ELSIF :NEW.amount > 0 THEN
        -- Deposits are valid as long as they are positive
        NULL; -- No action needed for valid deposits
    
    ELSE
        -- Handle invalid transactions (e.g., zero amount)
        RAISE_APPLICATION_ERROR(-20002, 'Invalid transaction amount. Amount must be positive for deposits and non-positive for withdrawals.');
    END IF;
END;
/

-- Insert sample data into the 'accounts' table
INSERT INTO accounts (account_id, account_holder, balance)
VALUES (1, 'Alice Johnson', 5000);

INSERT INTO accounts (account_id, account_holder, balance)
VALUES (2, 'Bob Smith', 3000);

INSERT INTO accounts (account_id, account_holder, balance)
VALUES (3, 'Charlie Brown', 2000);

COMMIT;

-- Test the trigger with valid and invalid transactions
BEGIN
    -- Valid deposit
    INSERT INTO transactions (transaction_id, account_id, transaction_date, amount, description)
    VALUES (transaction_seq.NEXTVAL, 1, SYSDATE, 500, 'Deposit');

    -- Valid withdrawal
    INSERT INTO transactions (transaction_id, account_id, transaction_date, amount, description)
    VALUES (transaction_seq.NEXTVAL, 2, SYSDATE, -500, 'Withdrawal');

    -- Invalid withdrawal (exceeds balance)
    BEGIN
        INSERT INTO transactions (transaction_id, account_id, transaction_date, amount, description)
        VALUES (transaction_seq.NEXTVAL, 3, SYSDATE, -3000, 'Withdrawal');
    EXCEPTION
        WHEN OTHERS THEN
            DBMS_OUTPUT.PUT_LINE(SQLERRM);
    END;

    -- Invalid deposit (zero amount)
    BEGIN
        INSERT INTO transactions (transaction_id, account_id, transaction_date, amount, description)
        VALUES (transaction_seq.NEXTVAL, 1, SYSDATE, 0, 'Invalid Deposit');
    EXCEPTION
        WHEN OTHERS THEN
            DBMS_OUTPUT.PUT_LINE(SQLERRM);
    END;

    COMMIT;
END;
/
