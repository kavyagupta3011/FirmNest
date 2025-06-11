CREATE TABLE employee (
    ssn CHAR(9) NOT NULL,
    fname VARCHAR(50) NOT NULL,
    minit CHAR(1),
    lname VARCHAR(50) NOT NULL,
    bdate DATE NOT NULL,
    address VARCHAR(255) NOT NULL,
    sex CHAR(1) NOT NULL,
    salary DECIMAL(10,2) NOT NULL,
    super_ssn CHAR(9),
    dno INT,
    CONSTRAINT employee_pk PRIMARY KEY (ssn)
);

CREATE TABLE department (
    dnumber INT NOT NULL,
    dname VARCHAR(50) NOT NULL,
    mgr_ssn CHAR(9),
    mgr_start_date DATE,
    CONSTRAINT department_pk PRIMARY KEY (dnumber)
);

CREATE TABLE dept_locations (
    dnumber INT NOT NULL,
    dlocation VARCHAR(100) NOT NULL,
    CONSTRAINT dept_locations_pk PRIMARY KEY (dnumber, dlocation)
);

CREATE TABLE project (
    pnumber INT NOT NULL,
    pname VARCHAR(100) NOT NULL,
    plocation VARCHAR(100) NOT NULL,
    dnum INT,
    CONSTRAINT project_pk PRIMARY KEY (pnumber)
);

CREATE TABLE works_on (
    essn CHAR(9) NOT NULL,
    pno INT NOT NULL,
    hours DECIMAL(5,2) NOT NULL,
    CONSTRAINT works_on_pk PRIMARY KEY (essn, pno)
);

CREATE TABLE dependent (
    essn CHAR(9) NOT NULL,
    dependent_name VARCHAR(50) NOT NULL,
    sex CHAR(1) NOT NULL,
    bdate DATE NOT NULL,
    relationship VARCHAR(50) NOT NULL,
    CONSTRAINT dependent_pk PRIMARY KEY (essn, dependent_name)
);

CREATE TABLE job_history (
    job_id INT AUTO_INCREMENT,
    ssn CHAR(9) NOT NULL,
    previous_dno INT,
    new_dno INT,
    change_date DATE NOT NULL,
    CONSTRAINT job_history_pk PRIMARY KEY (job_id)
);

CREATE TABLE attendance (
    attendance_id INT AUTO_INCREMENT,
    ssn CHAR(9) NOT NULL,
    date DATE NOT NULL,
    status VARCHAR(20) NOT NULL,
    CONSTRAINT attendance_pk PRIMARY KEY (attendance_id)
);

CREATE TABLE training_program (
    program_id INT AUTO_INCREMENT,
    title VARCHAR(100) NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    location VARCHAR(100) NOT NULL,
    CONSTRAINT training_program_pk PRIMARY KEY (program_id)
);

CREATE TABLE employee_training (
    ssn CHAR(9) NOT NULL,
    program_id INT NOT NULL,
    status VARCHAR(20) NOT NULL,
    CONSTRAINT employee_training_pk PRIMARY KEY (ssn, program_id)
);

CREATE TABLE performance_review (
    review_id INT AUTO_INCREMENT,
    ssn CHAR(9) NOT NULL,
    review_date DATE NOT NULL,
    rating INT NOT NULL,
    comments TEXT,
    CONSTRAINT performance_review_pk PRIMARY KEY (review_id)
);

CREATE TABLE salary_history (
    record_id INT AUTO_INCREMENT,
    ssn CHAR(9) NOT NULL,
    previous_salary DECIMAL(10,2) NOT NULL,
    new_salary DECIMAL(10,2) NOT NULL,
    change_date DATE NOT NULL,
    CONSTRAINT salary_history_pk PRIMARY KEY (record_id)
);

CREATE TABLE assets (
    asset_id INT AUTO_INCREMENT,
    asset_name VARCHAR(100) NOT NULL,
    assigned_to CHAR(9),
    assigned_date DATE NOT NULL,
    status VARCHAR(20) NOT NULL,
    CONSTRAINT assets_pk PRIMARY KEY (asset_id)
);

CREATE TABLE asset_maintenance (
    maintenance_id INT AUTO_INCREMENT,
    asset_id INT NOT NULL,
    maintenance_date DATE NOT NULL,
    details TEXT,
    CONSTRAINT asset_maintenance_pk PRIMARY KEY (maintenance_id)
);
