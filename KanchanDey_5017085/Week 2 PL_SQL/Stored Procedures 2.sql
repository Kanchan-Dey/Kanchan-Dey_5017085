-- Create the 'employees' table
BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE employees';
EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE != -942 THEN -- Ignore table not found error
            RAISE;
        END IF;
END;
/

CREATE TABLE employees (
    employee_id NUMBER PRIMARY KEY,
    employee_name VARCHAR2(100),
    department_id NUMBER,
    salary NUMBER
);

-- Insert sample data into the 'employees' table
INSERT INTO employees (employee_id, employee_name, department_id, salary)
VALUES (1, 'Alice Johnson', 10, 50000);

INSERT INTO employees (employee_id, employee_name, department_id, salary)
VALUES (2, 'Bob Smith', 10, 60000);

INSERT INTO employees (employee_id, employee_name, department_id, salary)
VALUES (3, 'Charlie Brown', 20, 70000);

INSERT INTO employees (employee_id, employee_name, department_id, salary)
VALUES (4, 'Diana Prince', 20, 80000);

COMMIT;

-- Create the 'UpdateEmployeeBonus' stored procedure
CREATE OR REPLACE PROCEDURE UpdateEmployeeBonus(
    p_department_id IN NUMBER,
    p_bonus_percentage IN NUMBER
) AS
BEGIN
    -- Start a transaction
    BEGIN
        -- Update the salary of employees in the given department by adding the bonus percentage
        UPDATE employees
        SET salary = salary + (salary * p_bonus_percentage / 100)
        WHERE department_id = p_department_id;

        -- Commit the transaction if no errors
        COMMIT;

        DBMS_OUTPUT.PUT_LINE('Bonus applied successfully to department ' || p_department_id);
    EXCEPTION
        WHEN OTHERS THEN
            -- Rollback transaction in case of error
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('An error occurred: ' || SQLERRM);
    END;
END;
/

-- Example of calling the procedure
BEGIN
    UpdateEmployeeBonus(10, 5); -- Apply a 5% bonus to all employees in department 10
    UpdateEmployeeBonus(20, 10); -- Apply a 10% bonus to all employees in department 20
END;
/
