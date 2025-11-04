SELECT emp_id, emp_name, department_id, salary, hire_date
FROM employees
WHERE hire_date >= DATEADD(month, -6, GETDATE());