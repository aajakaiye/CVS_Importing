STUDENTS = load '$students' using PigStorage(',')
  as (user_id:chararray, user_name:chararray, state:chararray);

COURSES = load '$courses' using PigStorage(',')
  as (course_id:chararray, course_name:chararray, state:chararray);

ENROLLMENT = load '$enrollment' using PigStorage(',')
  as (user_id:chararray, course_id:chararray,state:chararray);


STUDENTS = FILTER STUDENTS BY state == 'active';
COURSES = FILTER COURSES BY state == 'active';
ENROLLMENT = FILTER ENROLLMENT BY state == 'active';
A = JOIN STUDENTS by user_id, ENROLLMENT by user_id;
B = JOIN A by course_id, COURSES by course_id;

C = foreach B generate A::STUDENTS::user_id, A::STUDENTS::user_name, COURSES::course_id, COURSES::course_name;

D = foreach (group C by COURSES::course_name) {
    value = foreach C generate A::STUDENTS::user_name;
    value = distinct value;
    generate group as key, value;
};

STORE D INTO '$OUTPUT';
