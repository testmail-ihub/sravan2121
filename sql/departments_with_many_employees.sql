SELECT d.dept_name
FROM departments d
JOIN employees e ON d.dept_id = e.department_id
GROUP BY d.dept_name
HAVING COUNT(e.emp_id) > 5;