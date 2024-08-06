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

-- Insert sample data into the 'accounts' table
INSERT INTO accounts (account_id, account_holder, balance)
VALUES (1, 'Alice Johnson', 5000);

INSERT INTO accounts (account_id, account_holder, balance)
VALUES (2, 'Bob Smith', 3000);

INSERT INTO accounts (account_id, account_holder, balance)
VALUES (3, 'Charlie Brown', 2000);

-- Insert sample data into the 'transactions' table
INSERT INTO transactions (transaction_id, account_id, transaction_date, amount, description)
VALUES (transaction_seq.NEXTVAL, 1, DATE '2024-08-01', 200, 'Deposit');

INSERT INTO transactions (transaction_id, account_id, transaction_date, amount, description)
VALUES (transaction_seq.NEXTVAL, 1, DATE '2024-08-15', -100, 'Withdrawal');

INSERT INTO transactions (transaction_id, account_id, transaction_date, amount, description)
VALUES (transaction_seq.NEXTVAL, 2, DATE '2024-08-10', -150, 'Withdrawal');

INSERT INTO transactions (transaction_id, account_id, transaction_date, amount, description)
VALUES (transaction_seq.NEXTVAL, 3, DATE '2024-07-25', 300, 'Deposit');

COMMIT;

-- PL/SQL block with an explicit cursor
DECLARE
    -- Cursor to fetch transactions for the current month
    CURSOR GenerateMonthlyStatements IS
        SELECT t.transaction_id, t.account_id, a.account_holder, t.transaction_date, t.amount, t.description
        FROM transactions t
        JOIN accounts a ON t.account_id = a.account_id
        WHERE EXTRACT(MONTH FROM t.transaction_date) = EXTRACT(MONTH FROM SYSDATE)
          AND EXTRACT(YEAR FROM t.transaction_date) = EXTRACT(YEAR FROM SYSDATE);

    -- Variables to hold cursor data
    v_transaction_id transactions.transaction_id%TYPE;
    v_account_id transactions.account_id%TYPE;
    v_account_holder accounts.account_holder%TYPE;
    v_transaction_date transactions.transaction_date%TYPE;
    v_amount transactions.amount%TYPE;
    v_description transactions.description%TYPE;
BEGIN
    -- Open and fetch from the cursor
    FOR rec IN GenerateMonthlyStatements LOOP
        -- Retrieve data into variables
        v_transaction_id := rec.transaction_id;
        v_account_id := rec.account_id;
        v_account_holder := rec.account_holder;
        v_transaction_date := rec.transaction_date;
        v_amount := rec.amount;
        v_description := rec.description;
        
        -- Print statement for each transaction
        DBMS_OUTPUT.PUT_LINE('Transaction ID: ' || v_transaction_id);
        DBMS_OUTPUT.PUT_LINE('Account ID: ' || v_account_id);
        DBMS_OUTPUT.PUT_LINE('Account Holder: ' || v_account_holder);
        DBMS_OUTPUT.PUT_LINE('Transaction Date: ' || TO_CHAR(v_transaction_date, 'DD-Mon-YYYY'));
        DBMS_OUTPUT.PUT_LINE('Amount: ' || TO_CHAR(v_amount, 'FM9999.00'));
        DBMS_OUTPUT.PUT_LINE('Description: ' || v_description);
        DBMS_OUTPUT.PUT_LINE('---------------------------------------------');
    END LOOP;
END;
/
