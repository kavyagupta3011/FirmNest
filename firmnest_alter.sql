ALTER TABLE employee ADD CONSTRAINT employee_super_ssn_fk FOREIGN KEY (super_ssn) REFERENCES employee(ssn);
ALTER TABLE employee ADD CONSTRAINT employee_dno_fk FOREIGN KEY (dno) REFERENCES department(dnumber);



ALTER TABLE department ADD CONSTRAINT department_mgr_ssn_fk FOREIGN KEY (mgr_ssn) REFERENCES employee(ssn);



ALTER TABLE dept_locations ADD CONSTRAINT dept_locations_dnumber_fk FOREIGN KEY (dnumber) REFERENCES department(dnumber);



ALTER TABLE project ADD CONSTRAINT project_dnum_fk FOREIGN KEY (dnum) REFERENCES department(dnumber);



ALTER TABLE works_on ADD CONSTRAINT works_on_essn_fk FOREIGN KEY (essn) REFERENCES employee(ssn);
ALTER TABLE works_on ADD CONSTRAINT works_on_pno_fk FOREIGN KEY (pno) REFERENCES project(pnumber);



ALTER TABLE dependent ADD CONSTRAINT dependent_essn_fk FOREIGN KEY (essn) REFERENCES employee(ssn);



ALTER TABLE job_history ADD CONSTRAINT job_history_ssn_fk FOREIGN KEY (ssn) REFERENCES employee(ssn);
ALTER TABLE job_history ADD CONSTRAINT job_history_prev_dno_fk FOREIGN KEY (previous_dno) REFERENCES department(dnumber);
ALTER TABLE job_history ADD CONSTRAINT job_history_new_dno_fk FOREIGN KEY (new_dno) REFERENCES department(dnumber);



ALTER TABLE attendance ADD CONSTRAINT attendance_ssn_fk FOREIGN KEY (ssn) REFERENCES employee(ssn);



ALTER TABLE employee_training ADD CONSTRAINT employee_training_ssn_fk FOREIGN KEY (ssn) REFERENCES employee(ssn);
ALTER TABLE employee_training ADD CONSTRAINT employee_training_program_id_fk FOREIGN KEY (program_id) REFERENCES training_program(program_id);



ALTER TABLE performance_review ADD CONSTRAINT performance_review_ssn_fk FOREIGN KEY (ssn) REFERENCES employee(ssn);



ALTER TABLE salary_history ADD CONSTRAINT salary_history_ssn_fk FOREIGN KEY (ssn) REFERENCES employee(ssn);



ALTER TABLE assets ADD CONSTRAINT assets_assigned_to_fk FOREIGN KEY (assigned_to) REFERENCES employee(ssn);



ALTER TABLE asset_maintenance ADD CONSTRAINT asset_maintenance_asset_id_fk FOREIGN KEY (asset_id) REFERENCES assets(asset_id);


ALTER TABLE dept_change_history ADD CONSTRAINT fk_dept_change_ssn FOREIGN KEY (ssn) REFERENCES employee(ssn);
ALTER TABLE dept_change_history ADD CONSTRAINT fk_dept_change_old_dno FOREIGN KEY (old_dno) REFERENCES department(dnumber);
ALTER TABLE dept_change_history ADD CONSTRAINT fk_dept_change_new_dno FOREIGN KEY (new_dno) REFERENCES department(dnumber);


ALTER TABLE salary_change_history ADD CONSTRAINT salary_change_ssn_fk FOREIGN KEY (ssn) REFERENCES employee(ssn);


ALTER TABLE supervisor_change_history ADD CONSTRAINT supervisor_change_ssn_fk FOREIGN KEY (ssn) REFERENCES employee(ssn);
ALTER TABLE supervisor_change_history ADD CONSTRAINT supervisor_change_old_fk FOREIGN KEY (old_super_ssn) REFERENCES employee(ssn);
ALTER TABLE supervisor_change_history ADD CONSTRAINT supervisor_change_new_fk FOREIGN KEY (new_super_ssn) REFERENCES employee(ssn);


ALTER TABLE leave_requests ADD CONSTRAINT fk_leave_employee FOREIGN KEY (ssn) REFERENCES employee(ssn) ON DELETE CASCADE;
