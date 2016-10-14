CREATE TABLE if not exists departments(
id bigint not null auto_increment,
name varchar(100),
primary key (id)
);
create table  if not exists employees(
id bigint not null auto_increment,
full_name varchar(100),
date_of_birth date,
salary bigint,
department_id bigint,
primary key (id)
);
INSERT INTO `departments` (`id`, `name`) VALUES ('1', 'Admins');
INSERT INTO `departments` (`id`, `name`) VALUES ('2', 'Users');
INSERT INTO `employees` (`id`, `full_name`, `date_of_birth`, `salary`, `department_id`)
VALUES ('1', 'Mark Selby', '1983-06-19', '7000', '1');
INSERT INTO `employees` (`id`, `full_name`, `date_of_birth`, `salary`, `department_id`)
VALUES ('2', 'Stuart Bingham', '1976-05-21', '8000', '1');
INSERT INTO `employees` (`id`, `full_name`, `date_of_birth`, `salary`, `department_id`)
VALUES ('3', 'Shaun Murphy', '1982-08-10', '3000', '1');
INSERT INTO `employees` (`id`, `full_name`, `date_of_birth`, `salary`, `department_id`)
VALUES ('4', 'Ricky Walden', '1982-11-11', '3500', '2');
INSERT INTO `employees` (`id`, `full_name`, `date_of_birth`, `salary`, `department_id`)
VALUES ('5', 'Barry Hawkins', '1979-04-23', '5000', '2');
INSERT INTO `employees` (`id`, `full_name`, `date_of_birth`, `salary`, `department_id`)
VALUES ('6', 'Matthew Stevens', '1977-09-11', '4000', '2');
INSERT INTO `employees` (`id`, `full_name`, `date_of_birth`, `salary`, `department_id`)
VALUES ('7', 'Ali Carter', '1979-07-25', '1500', '2');
