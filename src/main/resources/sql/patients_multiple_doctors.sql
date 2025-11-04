-- Query 4: Show patients who have seen more than 2 different doctors
-- This query groups patients by their ID and counts distinct doctors they have appointments with

SELECT 
    p.patient_id,
    p.name AS patient_name,
    p.age,
    p.gender,
    COUNT(DISTINCT a.doctor_id) AS doctors_seen,
    GROUP_CONCAT(DISTINCT d.name ORDER BY d.name SEPARATOR ', ') AS doctor_names
FROM patients p
INNER JOIN appointments a ON p.patient_id = a.patient_id
INNER JOIN doctors d ON a.doctor_id = d.doctor_id
GROUP BY p.patient_id, p.name, p.age, p.gender
HAVING COUNT(DISTINCT a.doctor_id) > 2
ORDER BY doctors_seen DESC, p.name;