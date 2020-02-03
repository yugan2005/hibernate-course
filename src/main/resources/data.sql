insert into course(id, name, last_updated_time, created_time, is_deleted) values(10001, 'JPA in 50 steps', sysdate(), sysdate(), false);
insert into course(id, name, last_updated_time, created_time, is_deleted) values(10002, 'Hibernate in 50 steps', sysdate(), sysdate(), false);
insert into course(id, name, last_updated_time, created_time, is_deleted) values(10003, 'Spring in 50 steps', sysdate(), sysdate(), false);
insert into course(id, name, last_updated_time, created_time, is_deleted) values(10004, 'Microservice in 50 steps', sysdate(), sysdate(), false);
insert into course(id, name, last_updated_time, created_time, is_deleted) values(10005, 'be deleted', sysdate(), sysdate(), false);

insert into review (id, rating, description, course_id) values (30001, '5', 'great course', 10001);
insert into review (id, rating, description, course_id) values (30002, '4', 'not bad course', 10001);
insert into review (id, rating, description, course_id) values (30003, '5', 'awesome course', 10003);

insert into student (id, name) values (40001, 'Ross');
insert into student (id, name) values (40002, 'Tiffany');
insert into student (id, name) values (40003, 'Neo');
insert into student (id, name) values (40004, 'Jed');

insert into passport (student_id, number) values (40001, 'E123456');
insert into passport (student_id, number) values (40002, 'G123456');
insert into passport (student_id, number) values (40003, 'T123457');
insert into passport (student_id, number) values (40004, 'A123457');

insert into student_course_jointable(student_id, course_id) values (40001, 10001);
insert into student_course_jointable(student_id, course_id) values (40002, 10001);
insert into student_course_jointable(student_id, course_id) values (40003, 10001);
insert into student_course_jointable(student_id, course_id) values (40001, 10002);
insert into student_course_jointable(student_id, course_id) values (40004, 10002);
insert into student_course_jointable(student_id, course_id) values (40004, 10003);