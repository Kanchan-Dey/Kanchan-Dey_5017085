-- Optional: Create a 'loans' table for demonstration
BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE loans';
EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE != -942 THEN -- Ignore table not found error
            RAISE;
        END IF;
END;
/

CREATE TABLE loans (
    loan_id NUMBER PRIMARY KEY,
    loan_amount NUMBER,
    annual_interest_rate NUMBER,
    duration_years NUMBER
);

-- Insert sample data into the 'loans' table
INSERT INTO loans (loan_id, loan_amount, annual_interest_rate, duration_years)
VALUES (1, 10000, 5, 2);

INSERT INTO loans (loan_id, loan_amount, annual_interest_rate, duration_years)
VALUES (2, 20000, 6.5, 5);

INSERT INTO loans (loan_id, loan_amount, annual_interest_rate, duration_years)
VALUES (3, 15000, 4, 3);

COMMIT;

-- Create the 'CalculateMonthlyInstallment' function
CREATE OR REPLACE FUNCTION CalculateMonthlyInstallment(
    p_loan_amount IN NUMBER,
    p_annual_interest_rate IN NUMBER,
    p_duration_years IN NUMBER
) RETURN NUMBER AS
    v_monthly_rate NUMBER;
    v_total_payments NUMBER;
    v_monthly_installment NUMBER;
BEGIN
    -- Calculate monthly interest rate and total number of payments
    v_monthly_rate := p_annual_interest_rate / 100 / 12;
    v_total_payments := p_duration_years * 12;

    -- Calculate the monthly installment using the amortization formula
    IF v_monthly_rate > 0 THEN
        v_monthly_installment := (p_loan_amount * v_monthly_rate * POWER(1 + v_monthly_rate, v_total_payments)) / (POWER(1 + v_monthly_rate, v_total_payments) - 1);
    ELSE
        -- If the interest rate is zero, calculate simple division
        v_monthly_installment := p_loan_amount / v_total_payments;
    END IF;
    
    RETURN v_monthly_installment;
END;
/

-- Example of calling the function
DECLARE
    v_loan_amount NUMBER := 10000;
    v_annual_interest_rate NUMBER := 5;
    v_duration_years NUMBER := 2;
    v_monthly_installment NUMBER;
BEGIN
    -- Calculate the monthly installment using the function
    v_monthly_installment := CalculateMonthlyInstallment(v_loan_amount, v_annual_interest_rate, v_duration_years);
    
    DBMS_OUTPUT.PUT_LINE('Monthly installment for the loan: ' || TO_CHAR(v_monthly_installment, '999.99'));
END;
/
