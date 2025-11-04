-- Query 2: Find the doctor with the most appointments in the last 30 days
-- This query counts appointments per doctor within the last 30 days and returns the top doctor

SELECT 
    d.doctor_id,
    d.name AS doctor_name,
    d.specialty,
    COUNT(a.appointment_id) AS appointment_count
FROM doctors d
INNER JOIN appointments a ON d.doctor_id = a.doctor_id
WHERE a.appointment_date >= DATE_SUB(CURDATE(), INTERVAL 30 DAY)
    AND a.appointment_date <= CURDATE()
GROUP BY d.doctor_id, d.name, d.specialty
ORDER BY appointment_count DESC
LIMIT 1;