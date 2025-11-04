UPDATE employees
SET salary = salary * 1.10
WHERE department_id = (
    SELECT dept_id FROM departments WHERE dept_name = 'Engineering'
);