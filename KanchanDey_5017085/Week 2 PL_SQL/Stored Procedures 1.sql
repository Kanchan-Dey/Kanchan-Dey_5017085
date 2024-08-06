-- Create the 'savings_accounts' table
BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE savings_accounts';
EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE != -942 THEN -- Ignore table not found error
            RAISE;
        END IF;
END;
/

CREATE TABLE savings_accounts (
    account_id NUMBER PRIMARY KEY,
    account_holder VARCHAR2(100),
    balance NUMBER
);

-- Insert sample data into the 'savings_accounts' table
INSERT INTO savings_accounts (account_id, account_holder, balance)
VALUES (1, 'Alice Johnson', 1000);

INSERT INTO savings_accounts (account_id, account_holder, balance)
VALUES (2, 'Bob Smith', 1500);

INSERT INTO savings_accounts (account_id, account_holder, balance)
VALUES (3, 'Charlie Brown', 2000);

COMMIT;

-- Create the 'ProcessMonthlyInterest' stored procedure
CREATE OR REPLACE PROCEDURE ProcessMonthlyInterest AS
BEGIN
    -- Apply 1% interest to all savings accounts
    UPDATE savings_accounts
    SET balance = balance * 1.01;

    -- Commit the transaction if no errors
    COMMIT;

    DBMS_OUTPUT.PUT_LINE('Monthly interest applied successfully to all savings accounts.');
EXCEPTION
    WHEN OTHERS THEN
        -- Rollback transaction in case of error
        ROLLBACK;
        DBMS_OUTPUT.PUT_LINE('An error occurred: ' || SQLERRM);
END;
/

-- Example of calling the procedure
BEGIN
    ProcessMonthlyInterest; -- Apply interest to all accounts
END;
/
