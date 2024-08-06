-- Create the 'Transactions' table
BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE Transactions';
EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE != -942 THEN -- Ignore table not found error
            RAISE;
        END IF;
END;
/

CREATE TABLE Transactions (
    transaction_id NUMBER PRIMARY KEY,
    transaction_date DATE,
    amount NUMBER,
    description VARCHAR2(255)
);

-- Create the 'AuditLog' table
BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE AuditLog';
EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE != -942 THEN -- Ignore table not found error
            RAISE;
        END IF;
END;
/

CREATE TABLE AuditLog (
    log_id NUMBER PRIMARY KEY,
    transaction_id NUMBER,
    log_date DATE,
    action VARCHAR2(50),
    amount NUMBER,
    description VARCHAR2(255)
);

-- Create a sequence for log_id
CREATE SEQUENCE audit_log_seq
START WITH 1
INCREMENT BY 1;

-- Create the 'LogTransaction' trigger
CREATE OR REPLACE TRIGGER LogTransaction
AFTER INSERT ON Transactions
FOR EACH ROW
BEGIN
    INSERT INTO AuditLog (
        log_id,
        transaction_id,
        log_date,
        action,
        amount,
        description
    ) VALUES (
        audit_log_seq.NEXTVAL,
        :NEW.transaction_id,
        SYSDATE,
        'INSERT',
        :NEW.amount,
        :NEW.description
    );
END;
/

-- Insert sample data into the 'Transactions' table
INSERT INTO Transactions (transaction_id, transaction_date, amount, description)
VALUES (1, SYSDATE, 1000, 'Deposit');

INSERT INTO Transactions (transaction_id, transaction_date, amount, description)
VALUES (2, SYSDATE, 2000, 'Withdrawal');

INSERT INTO Transactions (transaction_id, transaction_date, amount, description)
VALUES (3, SYSDATE, 1500, 'Transfer');

COMMIT;

-- Verify the trigger by checking the 'AuditLog' table
SELECT * FROM AuditLog;
/

-- Example of inserting a transaction and checking the AuditLog
DECLARE
    v_transaction_id NUMBER := 4;
BEGIN
    INSERT INTO Transactions (transaction_id, transaction_date, amount, description)
    VALUES (v_transaction_id, SYSDATE, 500, 'Fee');

    -- Verify the 'AuditLog' entry
    FOR rec IN (SELECT * FROM AuditLog WHERE transaction_id = v_transaction_id) LOOP
        DBMS_OUTPUT.PUT_LINE('LogID: ' || rec.log_id ||
                             ', TransactionID: ' || rec.transaction_id ||
                             ', LogDate: ' || TO_CHAR(rec.log_date, 'YYYY-MM-DD HH24:MI:SS') ||
                             ', Action: ' || rec.action ||
                             ', Amount: ' || rec.amount ||
                             ', Description: ' || rec.description);
    END LOOP;
END;
/
