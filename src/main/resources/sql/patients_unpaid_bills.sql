-- Query 1: List patients who have unpaid bills
-- This query joins patients, appointments, and bills tables to find patients with unpaid bills

SELECT DISTINCT 
    p.patient_id,
    p.name AS patient_name,
    p.age,
    p.gender,
    b.bill_id,
    b.amount,
    a.appointment_date
FROM patients p
INNER JOIN appointments a ON p.patient_id = a.patient_id
INNER JOIN bills b ON a.appointment_id = b.appointment_id
WHERE b.paid_status = 'unpaid'
ORDER BY p.name, a.appointment_date DESC;