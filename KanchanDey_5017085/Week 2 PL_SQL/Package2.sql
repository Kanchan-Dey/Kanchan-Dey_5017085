-- Drop existing tables if they exist
BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE employees';
EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE != -942 THEN -- Ignore table not found error
            RAISE;
        END IF;
END;
/

-- Create the 'employees' table
CREATE TABLE employees (
    employee_id NUMBER PRIMARY KEY,
    employee_name VARCHAR2(100),
    department VARCHAR2(50),
    monthly_salary NUMBER
);

-- Create a sequence for employee_id
CREATE SEQUENCE employee_seq
START WITH 1
INCREMENT BY 1;

-- Insert sample data into the 'employees' table
INSERT INTO employees (employee_id, employee_name, department, monthly_salary)
VALUES (employee_seq.NEXTVAL, 'Alice Johnson', 'HR', 4000);

INSERT INTO employees (employee_id, employee_name, department, monthly_salary)
VALUES (employee_seq.NEXTVAL, 'Bob Smith', 'IT', 5000);

INSERT INTO employees (employee_id, employee_name, department, monthly_salary)
VALUES (employee_seq.NEXTVAL, 'Charlie Brown', 'Finance', 4500);

COMMIT;

-- Package Specification
CREATE OR REPLACE PACKAGE EmployeeManagement AS
    PROCEDURE HireEmployee(p_name IN VARCHAR2, p_department IN VARCHAR2, p_monthly_salary IN NUMBER);
    PROCEDURE UpdateEmployeeDetails(p_employee_id IN NUMBER, p_name IN VARCHAR2, p_department IN VARCHAR2, p_monthly_salary IN NUMBER);
    FUNCTION CalculateAnnualSalary(p_employee_id IN NUMBER) RETURN NUMBER;
END EmployeeManagement;
/

-- Package Body
CREATE OR REPLACE PACKAGE BODY EmployeeManagement AS

    PROCEDURE HireEmployee(p_name IN VARCHAR2, p_department IN VARCHAR2, p_monthly_salary IN NUMBER) IS
    BEGIN
        INSERT INTO employees (employee_id, employee_name, department, monthly_salary)
        VALUES (employee_seq.NEXTVAL, p_name, p_department, p_monthly_salary);
        COMMIT;
    EXCEPTION
        WHEN OTHERS THEN
            DBMS_OUTPUT.PUT_LINE('Error hiring employee: ' || SQLERRM);
    END HireEmployee;

    PROCEDURE UpdateEmployeeDetails(p_employee_id IN NUMBER, p_name IN VARCHAR2, p_department IN VARCHAR2, p_monthly_salary IN NUMBER) IS
    BEGIN
        UPDATE employees
        SET employee_name = p_name,
            department = p_department,
            monthly_salary = p_monthly_salary
        WHERE employee_id = p_employee_id;
        
        IF SQL%ROWCOUNT = 0 THEN
            DBMS_OUTPUT.PUT_LINE('Employee ID ' || p_employee_id || ' does not exist.');
        ELSE
            COMMIT;
        END IF;
    EXCEPTION
        WHEN OTHERS THEN
            DBMS_OUTPUT.PUT_LINE('Error updating employee details: ' || SQLERRM);
    END UpdateEmployeeDetails;

    FUNCTION CalculateAnnualSalary(p_employee_id IN NUMBER) RETURN NUMBER IS
        v_monthly_salary employees.monthly_salary%TYPE;
        v_annual_salary NUMBER;
    BEGIN
        SELECT monthly_salary INTO v_monthly_salary
        FROM employees
        WHERE employee_id = p_employee_id;

        v_annual_salary := v_monthly_salary * 12;

        RETURN v_annual_salary;
    EXCEPTION
        WHEN NO_DATA_FOUND THEN
            DBMS_OUTPUT.PUT_LINE('Employee ID ' || p_employee_id || ' does not exist.');
            RETURN NULL;
        WHEN OTHERS THEN
            DBMS_OUTPUT.PUT_LINE('Error calculating annual salary: ' || SQLERRM);
            RETURN NULL;
    END CalculateAnnualSalary;

END EmployeeManagement;
/

-- Example usage of the package

BEGIN
    -- Hire a new employee
    EmployeeManagement.HireEmployee('David Wilson', 'Marketing', 3500);

    -- Update existing employee details
    EmployeeManagement.UpdateEmployeeDetails(2, 'Robert Smith', 'IT', 5500);

    -- Calculate annual salary
    DECLARE
        v_annual_salary NUMBER;
    BEGIN
        v_annual_salary := EmployeeManagement.CalculateAnnualSalary(1);
        DBMS_OUTPUT.PUT_LINE('Annual Salary for Employee ID 1: ' || TO_CHAR(v_annual_salary, 'FM99999.00'));
    END;
END;
/
