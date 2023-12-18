
--dept_name varchar(50)
--student_name varchar(50)
--instructor_name varchar(50)
-- course_id varchar(50)
--student_id varchar(50)


create table current_session(
current_year INT NOT NULL,
current_session INT NOT NULL,
status INT NOT NULL,
primary key(current_year,current_session,status)
);

create table admin(
admin_name varchar(50) NOT NULL,
admin_id varchar(50) NOT NULL,
contact varchar(50) ,
primary key(admin_id)
);
create table department(
  dept_name varchar(50) NOT NULL,
  dept_id varchar(50) NOT NULL,
  building varchar(50),
  primary key (dept_id)
);

create table student(
   student_id varchar(50) NOT NULL,
   student_name varchar(50) NOT NULL,
   dept_id varchar(50),
   current_sem INT NOT NULL,
   contact varchar(50) NOT NULL,
   primary key(student_id),
   foreign key (dept_id) references department(dept_id)
);

create table instructor (
instructor_id varchar(50) NOT NULL,
instructor_name varchar(50),
dept_id varchar(50),
salary numeric(10,2),
contact varchar(50),
primary key (instructor_id),
foreign key (dept_id) references department(dept_id)

);

create table course(
course_id varchar(50) NOT NULL,
title varchar (50) NOT NULL,
dept_id varchar(50) NOT NULL,
req_sem INT NOT NULL,
l numeric(8,1) NOT NULL,
t numeric(8,1) NOT NULL,
p numeric(8,1) NOT NULL,
credits numeric(8,2) NOT NULL,

primary key (course_id),
foreign key (dept_id) references department(dept_id)

);
create table course_catalogue(
course_id varchar(50) NOT NULL,
title varchar (50) NOT NULL,
dept_id varchar(50) NOT NULL,
req_sem INT NOT NULL,
l numeric(8,1)NOT NULL,
t numeric(8,1) NOT NULL,
p numeric(8,1) NOT NULL,
credits numeric(8,2) NOT NULL,
course_type varchar NOT NULL,
batch int NOT NULL,

primary key (course_id,dept_id,batch),
foreign key (dept_id) references department(dept_id)

);

create table prereq(
course_id varchar(50) NOT NULL,
prereq_id varchar(50) NOT NULL,
primary key (course_id, prereq_id),
foreign key (course_id) references course(course_id),
foreign key (prereq_id) references course(course_id)
);

--create table student_record (
--student_id varchar(50) NOT NULL,
--course_id varchar(50) NOT NULL,
--credits numeric(3,2) NOT NULL,
--semester INT NOT NULL,
--primary key(student_id,course_id,credits,semester),
--foreign key(student_id) references student(student_id),
--foreign key(course_id) references course(course_id)
--
--);
--Name,Id,Role,Department,Email,Contact,Password
create table user_table(
user_id varchar(50) NOT NULL,
role varchar(50) NOT NULL,
contact varchar(50) NOT NULL,
password varchar(100),
primary key (user_id,role)
);
create table course_offering(
instructor_id varchar(50) NOT NULL,
course_id varchar(50) NOT NULL,
dept_id varchar(50) NOT NULL,
min_cgpa numeric(4,2) NOT NULL,
primary key (instructor_id,course_id,dept_id),
foreign key (instructor_id) references instructor(instructor_id),
foreign key (dept_id) references department(dept_id),
foreign key (course_id) references course(course_id)

);

create table enrolled_credits(
student_id varchar(50) NOT NULL,
credits numeric(8,2) NOT NULL,
primary key (student_id) ,
foreign key (student_id) references student(student_id)
);


create table gradCheck(
program_core_credits  numeric(10,2) NOT NULL,
elective_core_credits  numeric(10,2) NOT NULL,
engineering_core_credits  numeric(10,2) NOT NULL,
must_complete_btp varchar NOT NULL
);


CREATE TABLE user_sessions (
    id SERIAL PRIMARY KEY,
    user_id varchar NOT NULL,
    login_time TIMESTAMP NOT NULL,
    logout_time TIMESTAMP
);




--create table ticket(
-- pnr varchar(50) NOT NULL,
-- train_num INT NOT NULL,
-- for_date date not null,
-- num_seats INT NOT NULL,
-- coach_type varchar(10) NOT NULL,
-- primary key (pnr),
-- foreign key (train_num, for_date) references schedule(train_num, running_on)
--
--);
-- create table passenger(
--  pnr varchar(50) NOT NULL,
--  coach_num INT NOT NULL,
--  berth_num INT NOT NULL,
--  berth_type varchar(10) not null,
--  pas_name varchar(50) not null,
--  primary key (pnr,coach_num,berth_num),
--  foreign key (pnr) references ticket(pnr)
-- );
--
-- create table curr_avail_ac (
--  train_num int not null,
--  coach_num int not null,
--  running_on date not null,
--  avail_seat int not null,
--  primary key (train_num, running_on),
--  foreign key (train_num,running_on) references schedule (train_num, running_on)
-- );
--
--
-- create table curr_avail_sl (
--  train_num int not null,
--  coach_num int not null,
--  running_on date not null,
--  avail_seat int not null,
--  primary key (train_num, running_on),
--  foreign key (train_num,running_on) references schedule (train_num, running_on)
-- );

