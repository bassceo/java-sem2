CREATE TABLE users(
    id serial PRIMARY KEY,
    name varchar(255) NOT NULL,
    password varchar(255) NOT NULL
);

CREATE TABLE groups (
     id serial PRIMARY KEY,
     name varchar(255),
     coordinates_x float,
     coordinates_y float,
     creation_date date,
     students_count integer,
     should_be_expelled integer,
     average_mark integer,
     form_of_education varchar(255),
     group_admin_name varchar(255),
     group_admin_height float,
     group_admin_eye_color varchar(255),
     group_admin_nationality varchar(255),
     user_id integer REFERENCES users(id)
);