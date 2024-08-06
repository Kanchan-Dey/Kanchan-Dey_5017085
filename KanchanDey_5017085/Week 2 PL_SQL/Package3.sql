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
    customer_id NUMBER,
    account_type VARCHAR2(50),
    balance NUMBER
);

-- Create a sequence for account_id
CREATE SEQUENCE account_seq
START WITH 1
INCREMENT BY 1;

-- Insert sample data into the 'accounts' table
INSERT INTO accounts (account_id, customer_id, account_type, balance)
VALUES (account_seq.NEXTVAL, 101, 'Savings', 3000);

INSERT INTO accounts (account_id, customer_id, account_type, balance)
VALUES (account_seq.NEXTVAL, 101, 'Checking', 1500);

INSERT INTO accounts (account_id, customer_id, account_type, balance)
VALUES (account_seq.NEXTVAL, 102, 'Savings', 2000);

COMMIT;

-- Package Specification
CREATE OR REPLACE PACKAGE AccountOperations AS
    PROCEDURE OpenAccount(p_customer_id IN NUMBER, p_account_type IN VARCHAR2, p_balance IN NUMBER);
    PROCEDURE CloseAccount(p_account_id IN NUMBER);
    FUNCTION GetTotalBalance(p_customer_id IN NUMBER) RETURN NUMBER;
END AccountOperations;
/

-- Package Body
CREATE OR REPLACE PACKAGE BODY AccountOperations AS

    PROCEDURE OpenAccount(p_customer_id IN NUMBER, p_account_type IN VARCHAR2, p_balance IN NUMBER) IS
    BEGIN
        INSERT INTO accounts (account_id, customer_id, account_type, balance)
        VALUES (account_seq.NEXTVAL, p_customer_id, p_account_type, p_balance);
        COMMIT;
    EXCEPTION
        WHEN OTHERS THEN
            DBMS_OUTPUT.PUT_LINE('Error opening account: ' || SQLERRM);
    END OpenAccount;

    PROCEDURE CloseAccount(p_account_id IN NUMBER) IS
    BEGIN
        DELETE FROM accounts
        WHERE account_id = p_account_id;
        
        IF SQL%ROWCOUNT = 0 THEN
            DBMS_OUTPUT.PUT_LINE('Account ID ' || p_account_id || ' does not exist.');
        ELSE
            COMMIT;
        END IF;
    EXCEPTION
        WHEN OTHERS THEN
            DBMS_OUTPUT.PUT_LINE('Error closing account: ' || SQLERRM);
    END CloseAccount;

    FUNCTION GetTotalBalance(p_customer_id IN NUMBER) RETURN NUMBER IS
        v_total_balance NUMBER;
    BEGIN
        SELECT SUM(balance) INTO v_total_balance
        FROM accounts
        WHERE customer_id = p_customer_id;

        RETURN NVL(v_total_balance, 0);
    EXCEPTION
        WHEN OTHERS THEN
            DBMS_OUTPUT.PUT_LINE('Error calculating total balance: ' || SQLERRM);
            RETURN 0;
    END GetTotalBalance;

END AccountOperations;
/

-- Example usage of the package

BEGIN
    -- Open a new account
    AccountOperations.OpenAccount(103, 'Checking', 2500);

    -- Close an existing account
    AccountOperations.CloseAccount(1);

    -- Get total balance for a customer
    DECLARE
        v_total_balance NUMBER;
    BEGIN
        v_total_balance := AccountOperations.GetTotalBalance(101);
        DBMS_OUTPUT.PUT_LINE('Total Balance for Customer ID 101: ' || TO_CHAR(v_total_balance, 'FM9999.00'));
    END;
END;
/
