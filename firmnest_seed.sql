INSERT INTO employee (ssn, fname, minit, lname, bdate, address, sex, salary, super_ssn) VALUES
('111223333', 'Alice', 'M', 'Johnson', '1985-06-15', '123 Main St, NY', 'F', 75000.00, NULL),
('222334444', 'Bob', 'A', 'Smith', '1980-03-22', '456 Elm St, SF', 'M', 90000.00, NULL),
('333445555', 'Carol', 'B', 'Lee', '1982-09-10', '789 Oak St, CHI', 'F', 85000.00, NULL),
('777889999', 'George', 'F', 'Martin', '1978-04-04', '112 River Rd, BOS', 'M', 95000.00, NULL),
('888990000', 'Hannah', 'G', 'Taylor', '1981-12-25', '314 Sunset Blvd, LA', 'F', 92000.00, NULL),
('444556666', 'David', 'C', 'Clark', '1990-07-18', '101 Pine St, SF', 'M', 60000.00, '222334444'),
('555667777', 'Eva', 'D', 'Martinez', '1995-01-10', '202 Maple St, NY', 'F', 55000.00, '111223333'),
('666778888', 'Frank', 'E', 'Wong', '1988-11-30', '303 Birch St, CHI', 'M', 58000.00, '333445555'),
('777889001', 'Ivy', 'H', 'Nguyen', '1993-02-20', '400 Lakeview, BOS', 'F', 61000.00, '777889999'),
('888990002', 'Jake', 'I', 'Patel', '1991-05-07', '500 Ocean Dr, LA', 'M', 63000.00, '888990000'),
('999001123', 'Liam', 'J', 'Scott', '1992-03-15', '607 Forest Rd, NY', 'M', 54000.00, '111223333'),
('123456789', 'Zara', 'K', 'Ali', '1987-09-19', '708 Ridge Ave, SF', 'F', 72000.00, '222334444');


INSERT INTO department (dnumber, dname, mgr_ssn, mgr_start_date) VALUES
(1, 'Human Resources', '111223333', '2023-01-01'),
(2, 'Engineering', '222334444', '2023-03-15'),
(3, 'Marketing', '333445555', '2022-11-10'),
(4, 'Finance', '777889999', '2023-07-20'),
(5, 'Operations', '888990000', '2023-08-01');

UPDATE employee SET dno = 1 WHERE ssn = '111223333';
UPDATE employee SET dno = 2 WHERE ssn = '222334444';
UPDATE employee SET dno = 3 WHERE ssn = '333445555';
UPDATE employee SET dno = 4 WHERE ssn = '777889999';
UPDATE employee SET dno = 5 WHERE ssn = '888990000';
UPDATE employee SET dno = 2 WHERE ssn = '444556666';
UPDATE employee SET dno = 1 WHERE ssn = '555667777';
UPDATE employee SET dno = 3 WHERE ssn = '666778888';
UPDATE employee SET dno = 4 WHERE ssn = '777889001';
UPDATE employee SET dno = 5 WHERE ssn = '888990002';
UPDATE employee SET dno = 1 WHERE ssn = '999001123';
UPDATE employee SET dno = 2 WHERE ssn = '123456789';


INSERT INTO dept_locations (dnumber, dlocation) VALUES
(1, 'New York'),
(2, 'San Francisco'),
(3, 'Chicago'),
(4, 'Boston'),
(5, 'Los Angeles');

INSERT INTO project (pnumber, pname, plocation, dnum) VALUES
(101, 'AI Chatbot', 'San Francisco', 2),
(102, 'HR System Upgrade', 'New York', 1),
(103, 'Ad Campaign 2025', 'Chicago', 3),
(104, 'Expense Tracker App', 'Boston', 4),
(105, 'Logistics Automation', 'Los Angeles', 5);

INSERT INTO works_on (essn, pno, hours) VALUES
('444556666', 101, 20),
('555667777', 102, 15),
('666778888', 103, 25),
('555667777', 103, 10),
('777889001', 104, 18),
('888990002', 105, 22),
('999001123', 102, 14),
('123456789', 101, 30);

INSERT INTO dependent (essn, dependent_name, sex, bdate, relationship) VALUES
('444556666', 'Tom', 'M', '2012-04-05', 'Son'),
('555667777', 'Lily', 'F', '2015-06-12', 'Daughter'),
('666778888', 'Nina', 'F', '2013-08-23', 'Spouse'),
('777889001', 'Leo', 'M', '2016-12-11', 'Son'),
('888990002', 'Maya', 'F', '2018-07-27', 'Daughter');

INSERT INTO attendance (ssn, date, status) VALUES
('444556666', '2025-04-22', 'Present'),
('444556666', '2025-04-23', 'Present'),
('555667777', '2025-04-22', 'Absent'),
('555667777', '2025-04-23', 'Present'),
('666778888', '2025-04-22', 'Leave'),
('999001123', '2025-04-22', 'Present'),
('123456789', '2025-04-22', 'Present');

INSERT INTO training_program (program_id, title, start_date, end_date, location) VALUES
(1, 'Python Workshop', '2025-05-01', '2025-05-05', 'New York'),
(2, 'Project Management', '2025-05-10', '2025-05-15', 'Chicago'),
(3, 'Advanced SQL', '2025-06-01', '2025-06-03', 'Boston');

INSERT INTO employee_training (ssn, program_id, status) VALUES
('555667777', 1, 'Enrolled'),
('666778888', 2, 'Completed'),
('123456789', 3, 'Enrolled');

INSERT INTO performance_review (ssn, review_date, rating, comments) VALUES
('444556666', '2025-03-31', 4, 'Excellent teamwork.'),
('555667777', '2025-03-31', 3, 'Meets expectations.'),
('666778888', '2025-03-31', 5, 'Outstanding work.'),
('123456789', '2025-03-31', 4, 'Shows initiative.'),
('999001123', '2025-03-31', 2, 'Needs improvement.');

INSERT INTO job_history (ssn, previous_dno, new_dno, change_date) VALUES
('555667777', 2, 1, '2025-01-01'),
('666778888', 1, 3, '2024-12-01'),
('123456789', 2, 2, '2023-07-01');

INSERT INTO salary_history (ssn, previous_salary, new_salary, change_date) VALUES
('444556666', 55000.00, 60000.00, '2025-02-15'),
('555667777', 50000.00, 55000.00, '2025-04-20'),
('123456789', 70000.00, 72000.00, '2025-01-01');

INSERT INTO assets (asset_id, asset_name, assigned_to, assigned_date, status) VALUES
(1001, 'Laptop Dell XPS', '555667777', '2024-12-01', 'Active'),
(1002, 'Phone iPhone 14', '444556666', '2025-01-15', 'Active'),
(1003, 'Tablet Samsung', '666778888', '2025-02-10', 'In Maintenance'),
(1004, 'Monitor LG 24"', '123456789', '2025-03-01', 'Active'),
(1005, 'Lenovo ThinkPad', '999001123', '2025-03-10', 'Active');

INSERT INTO asset_maintenance (asset_id, maintenance_date, details) VALUES
(1003, '2025-04-22', 'Screen flicker reported.'),
(1003, '2025-04-23', 'Sent to service center.'),
(1005, '2025-04-20', 'Battery replacement.');
