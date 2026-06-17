INSERT INTO course (course_code, course_name, credit_score) VALUES ('MTH5002', 'Pure Mathematics', 5);
INSERT INTO course (course_code, course_name, credit_score) VALUES ('PHY2009', 'Applied Physics', 2);
INSERT INTO course (course_code, course_name, credit_score) VALUES ('CS6011', 'Advanced Computing', 6);

INSERT INTO student (matric_number, first_name, last_name, date_of_admission) VALUES ('E019', 'Jennifer', 'White', '2026-01-15');
INSERT INTO student (matric_number, first_name, last_name, date_of_admission) VALUES ('B107', 'Ben', 'Brown', '2026-01-15');
INSERT INTO student (matric_number, first_name, last_name, date_of_admission) VALUES ('E724', 'Ali', 'McCoist', '2026-03-31');
INSERT INTO student (matric_number, first_name, last_name, date_of_admission) VALUES ('A771', 'Isaiah', 'Washington', '2026-01-17');

INSERT INTO students_courses (student_id, course_id) VALUES (1, 2);
INSERT INTO students_courses (student_id, course_id) VALUES (1, 1);
INSERT INTO students_courses (student_id, course_id) VALUES (2, 3);
INSERT INTO students_courses (student_id, course_id) VALUES (3, 1);
INSERT INTO students_courses (student_id, course_id) VALUES (3, 3);
INSERT INTO students_courses (student_id, course_id) VALUES (3, 2);
INSERT INTO students_courses (student_id, course_id) VALUES (4, 2);
