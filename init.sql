create table if not exists departments(
id bigint not null auto_increment,
name varchar(100),
primary key (id)
)engine=InnoDB;
create table if not exists employees(
id bigint not null auto_increment,
full_name varchar(100),
date_of_birth date,
salary bigint,
department_id bigint,
primary key (id),
constraint
foreign key (department_id) references departments(id) 
on delete set null
on update set null
) engine=InnoDB;
INSERT INTO `departments` (`id`, `name`) VALUES ('1', 'Production');
INSERT INTO `departments` (`id`, `name`) VALUES ('2', 'Marketing');
INSERT INTO `departments` (`id`, `name`) VALUES ('3', 'Purchasing');
INSERT INTO `employees` (`id`, `full_name`, `date_of_birth`, `salary`, `department_id`)
VALUES ('1', 'Eddie Stewart', '1983-06-19', '7000', '1');
INSERT INTO `employees` (`id`, `full_name`, `date_of_birth`, `salary`, `department_id`)
VALUES ('2', 'Carlos Stevens', '1976-05-21', '8000', '2');
INSERT INTO `employees` (`id`, `full_name`, `date_of_birth`, `salary`, `department_id`)
VALUES ('3', 'John Vallejos', '1982-08-10', '3000', '2');
INSERT INTO `employees` (`id`, `full_name`, `date_of_birth`, `salary`, `department_id`)
VALUES ('4', 'Michael Day', '1982-11-11', '3500', '3');
INSERT INTO `employees` (`id`, `full_name`, `date_of_birth`, `salary`, `department_id`)
VALUES ('5', 'Debra Townsend', '1979-04-23', '5000', '2');
INSERT INTO `employees` (`id`, `full_name`, `date_of_birth`, `salary`, `department_id`)
VALUES ('6', 'John McIntyre', '1977-09-11', '4000', '2');
INSERT INTO `employees` (`id`, `full_name`, `date_of_birth`, `salary`, `department_id`)
VALUES ('7', 'Lucille Judd', '1955-05-12', '1500', '3');
INSERT INTO `employees` (`id`, `full_name`, `date_of_birth`, `salary`, `department_id`)
VALUES ('8', 'Elizabeth Peterson', '1975-07-18', '6500', '1');
INSERT INTO `employees` (`id`, `full_name`, `date_of_birth`, `salary`, `department_id`)
VALUES ('9', 'Samuel Doll', '1989-03-03', '13000', '1');
INSERT INTO `employees` (`id`, `full_name`, `date_of_birth`, `salary`, `department_id`)
VALUES ('10', 'Joan Torres', '1983-04-05', '6000', '2');
INSERT INTO `employees` (`id`, `full_name`, `date_of_birth`, `salary`, `department_id`)
VALUES ('11', 'Paul Phillips', '1972-10-10', '5500', '2');
INSERT INTO `employees` (`id`, `full_name`, `date_of_birth`, `salary`, `department_id`)
VALUES ('12', 'Julio Calhoun', '1988-12-23', '5500', '3');
INSERT INTO `employees` (`id`, `full_name`, `date_of_birth`, `salary`, `department_id`)
VALUES ('13', 'Janis Marker', '1967-08-24', '7000', '2');
INSERT INTO `employees` (`id`, `full_name`, `date_of_birth`, `salary`, `department_id`)
VALUES ('14', 'Dorothy Charles', '1990-08-24', '1500', '1');

