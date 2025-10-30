-- Get all employees who joined in the last 6 months
SELECT emp_id, emp_name, hire_date
FROM employees
WHERE hire_date >= DATE('now', '-6 months');

-- Find the average salary per department
SELECT d.dept_name, AVG(e.salary) AS average_salary
FROM employees e
JOIN departments d ON e.department_id = d.dept_id
GROUP BY d.dept_name;

-- List employees earning more than the average salary of their department
SELECT e.emp_name, e.salary, d.dept_name
FROM employees e
JOIN departments d ON e.department_id = d.dept_id
WHERE e.salary > (SELECT AVG(salary) FROM employees WHERE department_id = e.department_id);

-- Get department names with more than 5 employees
SELECT d.dept_name
FROM departments d
JOIN employees e ON d.dept_id = e.department_id
GROUP BY d.dept_name
HAVING COUNT(e.emp_id) > 5;

-- Update salary by 10% for employees in “Engineering”
UPDATE employees
SET salary = salary * 1.10
WHERE department_id = (SELECT dept_id FROM departments WHERE dept_name = 'Engineering');