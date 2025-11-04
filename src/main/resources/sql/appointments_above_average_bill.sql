-- Query 5: Get appointment details for patients whose bill exceeds the average bill amount
-- This query uses a subquery to calculate the average bill amount and filters appointments accordingly

SELECT 
    a.appointment_id,
    p.patient_id,
    p.name AS patient_name,
    p.age,
    p.gender,
    d.doctor_id,
    d.name AS doctor_name,
    d.specialty,
    a.appointment_date,
    a.status AS appointment_status,
    b.bill_id,
    b.amount AS bill_amount,
    b.paid_status,
    (SELECT AVG(amount) FROM bills) AS average_bill_amount
FROM appointments a
INNER JOIN patients p ON a.patient_id = p.patient_id
INNER JOIN doctors d ON a.doctor_id = d.doctor_id
INNER JOIN bills b ON a.appointment_id = b.appointment_id
WHERE b.amount > (SELECT AVG(amount) FROM bills)
ORDER BY b.amount DESC, a.appointment_date DESC;