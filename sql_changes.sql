-- 1. List patients who have unpaid bills
SELECT DISTINCT p.patient_id, p.name, p.age, p.gender
FROM patients p
JOIN appointments a ON p.patient_id = a.patient_id
JOIN bills b ON a.appointment_id = b.appointment_id
WHERE b.paid_status = 'Unpaid';

-- 2. Find the doctor with the most appointments in the last 30 days
SELECT d.doctor_id, d.name AS doctor_name, d.specialty, COUNT(a.appointment_id) AS total_appointments
FROM doctors d
JOIN appointments a ON d.doctor_id = a.doctor_id
WHERE a.appointment_date >= CURRENT_DATE - INTERVAL '30 days'
GROUP BY d.doctor_id, d.name, d.specialty
ORDER BY total_appointments DESC
LIMIT 1;

-- 3. Calculate total revenue per doctor
SELECT d.doctor_id, d.name AS doctor_name, d.specialty, SUM(b.amount) AS total_revenue
FROM doctors d
JOIN appointments a ON d.doctor_id = a.doctor_id
JOIN bills b ON a.appointment_id = b.appointment_id
WHERE b.paid_status = 'Paid'
GROUP BY d.doctor_id, d.name, d.specialty
ORDER BY total_revenue DESC;

-- 4. Show patients who have seen more than 2 different doctors
SELECT p.patient_id, p.name AS patient_name, COUNT(DISTINCT a.doctor_id) AS distinct_doctors_seen
FROM patients p
JOIN appointments a ON p.patient_id = a.patient_id
GROUP BY p.patient_id, p.name
HAVING COUNT(DISTINCT a.doctor_id) > 2
ORDER BY distinct_doctors_seen DESC;

-- 5. Get the appointment details for patients whose bill exceeds the average bill amount
SELECT a.appointment_id, p.name AS patient_name, d.name AS doctor_name, a.appointment_date, b.amount AS bill_amount
FROM appointments a
JOIN patients p ON a.patient_id = p.patient_id
JOIN doctors d ON a.doctor_id = d.doctor_id
JOIN bills b ON a.appointment_id = b.appointment_id
WHERE b.amount > (SELECT AVG(amount) FROM bills)
ORDER BY b.amount DESC;