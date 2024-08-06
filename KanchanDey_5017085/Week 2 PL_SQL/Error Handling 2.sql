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
    salary NUMBER
);

-- Insert sample data into the 'employees' table
INSERT INTO employees (employee_id, employee_name, salary)
VALUES (1, 'Alice Johnson', 50000);

INSERT INTO employees (employee_id, employee_name, salary)
VALUES (2, 'Bob Smith', 60000);

INSERT INTO employees (employee_id, employee_name, salary)
VALUES (3, 'Charlie Brown', 70000);

COMMIT;

-- Create the 'UpdateSalary' stored procedure
CREATE OR REPLACE PROCEDURE UpdateSalary(
    p_employee_id IN NUMBER,
    p_percentage IN NUMBER
) AS
    v_current_salary NUMBER;
    v_new_salary NUMBER;
BEGIN
    -- Start a transaction
    BEGIN
        -- Check if the employee exists and get the current salary
        SELECT salary INTO v_current_salary
        FROM employees
        WHERE employee_id = p_employee_id;

        -- Calculate the new salary with the given percentage increase
        v_new_salary := v_current_salary + (v_current_salary * p_percentage / 100);

        -- Update the salary
        UPDATE employees
        SET salary = v_new_salary
        WHERE employee_id = p_employee_id;

        -- Commit the transaction if no errors
        COMMIT;

        DBMS_OUTPUT.PUT_LINE('Salary updated successfully for employee ID ' || p_employee_id || '. New salary: ' || TO_CHAR(v_new_salary, '99999.99'));
    EXCEPTION
        WHEN NO_DATA_FOUND THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error: Employee ID ' || p_employee_id || ' does not exist.');
        WHEN OTHERS THEN
            -- Rollback transaction in case of error
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('An error occurred: ' || SQLERRM);
    END;
END;
/

-- Example of calling the procedure
BEGIN
    UpdateSalary(1, 10); -- Increase salary of employee ID 1 by 10%
    UpdateSalary(4, 5);  -- Try to increase salary of non-existing employee ID 4
END;
/
