drop database if exists lab6;
create database lab6;
use lab6;

CREATE TABLE aircraft_class (
  class_name VARCHAR(50) NOT NULL,
  num_of_rockets INT DEFAULT 0,
  cost INT DEFAULT 0,
  country VARCHAR(50) DEFAULT 'Unknown',
  PRIMARY KEY(class_name));

INSERT INTO aircraft_class VALUES
                                     ('Aurora', 14, 42000, 'England'),
                                     ('Milano', 15, 14000, 'Spain'),
                                     ('Everton', 17, 35000, 'Italy'),
                                     ('Napoli', 12, 30000, 'Italy'),
                                     ('Gallery', 14, 46000, 'England'),
                                     ('Elize', 16, 22000, 'England'),
                                     ('Schalke', 13, 20000, 'Germany'),
                                     ('Inter', 18, 24000, 'Italy'),
                                     ('Leicester', 26, 42000, 'England'),
                                     ('Madrid', 10, 42000, 'Spain');

CREATE TABLE aircraft (
  pilot VARCHAR(30) NOT NULL,
  aircraft_name VARCHAR(30) NOT NULL,
  launched INT DEFAULT 0,
  class_name VARCHAR(50) DEFAULT 'Unknown',
  FOREIGN KEY (class_name) REFERENCES aircraft_class (class_name),
  PRIMARY KEY(pilot, aircraft_name));

# alter table aircraft add column ship_id int;

# alter table aircraft add unique(ship_id);

# ALTER TABLE aircraft MODIFY ship_id INTEGER NOT NULL AUTO_INCREMENT;

INSERT INTO aircraft (pilot, aircraft_name, launched, class_name)
VALUES
       ('Lohvinov', 'Tennessee',2004, NULL),
       ('De Maro', 'Kon',2008, NULL),
       ('Viridi', 'Iowa',1998, NULL),
       ('Kisilov', 'Anna', 1999, NULL),
       ('Popov', 'Yamato',2010, NULL),
       ('Columbo', 'Postige', 2005, NULL),
       ('Herak', 'Lawrence', 2007, NULL),
       ('Yolo', 'Wilenies', 1995, NULL),
       ('Vinci', 'Osaki', 2015, NULL),
       ('Jackson', 'Misito', 2003, NULL);



CREATE TABLE hangar (
  hangar_city VARCHAR(50) NOT NULL,
  num_of_aircrafts INT DEFAULT 0,
  country VARCHAR(50) DEFAULT 'Unknown',
  PRIMARY KEY(hangar_city));

INSERT INTO hangar VALUES
                           ('Boston', 25, 'USA'),
                           ('Lille', 24, 'France'),
                           ('Rammstein', 16, 'Germany'),
                           ('Darkville', 28, 'USA'),
                           ('New York', 34, 'USA'),
                           ('Brisbane', 50, 'USA'),
                           ('Beijin', 16, 'China'),
                           ('Berlin', 18, 'Germany'),
                           ('Middeterrian', 25, 'Italy'),
                           ('Amsterdam', 19, 'Netherlands');


CREATE TABLE hangaraircraft (
  pilot VARCHAR(30) NOT NULL,
  hangar_city VARCHAR(50) NOT NULL,
  FOREIGN KEY (pilot) REFERENCES aircraft (pilot),
  FOREIGN KEY (hangar_city) REFERENCES hangar (hangar_city),
  PRIMARY KEY(pilot, hangar_city));


DELIMITER //

CREATE PROCEDURE insert_hangaraircraft(
  IN pilot_in VARCHAR(30),
  IN hangar_city_in VARCHAR(30)
)

  BEGIN
    DECLARE msg VARCHAR(50);
    -- checks if pilot exists
    IF NOT EXISTS(SELECT * FROM aircraft WHERE pilot = pilot_in)
    THEN SET msg = 'There is no such pilot';
    -- checks if hangar city exists
    ELSEIF NOT EXISTS(SELECT * FROM hangar WHERE hangar_city = hangar_city_in)
      THEN SET msg = 'There is no such hangar city';
    -- checks if such combinations exists
    ELSEIF EXISTS(
        SELECT * FROM hangaraircraft WHERE
            pilot = (SELECT pilot FROM aircraft WHERE pilot = pilot_in AND hangar_city = hangar_city_in) AND
            hangar_city = (SELECT hangar_city FROM hangar WHERE hangar_city = hangar_city_in)
    ) THEN SET msg = 'This aircraft already was in this hangar city';

    ELSE INSERT INTO hangaraircraft VALUE(
                                           (SELECT pilot FROM aircraft WHERE pilot = pilot_in),
                                           (SELECT hangar_city FROM hangar WHERE hangar_city = hangar_city_in)
                                           ); SET msg = 'OK';

    END IF;

    SELECT msg AS message;

  END //

DELIMITER ;


DELIMITER //

CREATE PROCEDURE update_aircraft(
  IN old_pilot_in VARCHAR(30),
  IN old_aircraft_name_in VARCHAR(30),
  IN new_pilot_in VARCHAR(30),
  IN new_aircraft_name_in VARCHAR(30)
)

  BEGIN

    DECLARE msg VARCHAR(50);

    IF EXISTS (SELECT * FROM aircraft WHERE pilot = old_pilot_in AND aircraft_name = old_aircraft_name_in)
    THEN UPDATE aircraft SET pilot = new_pilot_in, aircraft_name = new_aircraft_name_in
         WHERE pilot = old_pilot_in AND aircraft_name = old_aircraft_name_in; SET msg = 'OK';

    ELSE SET msg = 'No such aircraft';
    END IF;
    SELECT msg AS message;
  END //

DELIMITER ;

DELIMITER //
CREATE PROCEDURE update_class(
  IN old_class_name_in VARCHAR(50),
  IN new_class_name_in VARCHAR(50)
)

  BEGIN

    DECLARE msg VARCHAR(50);

    IF EXISTS (SELECT * FROM aircraft_class WHERE class_name = old_class_name_in)
    THEN UPDATE aircraft_class SET class_name = new_class_name_in
         WHERE class_name = old_class_name_in; SET msg = 'OK';

    ELSE SET msg = 'No such aircraft class';

    END IF;

  END //

DELIMITER ;


DELIMITER //
CREATE PROCEDURE update_hangar(
  IN old_aircraft_name_in VARCHAR(50),
  IN new_aircraft_name_in VARCHAR(50)
)

  BEGIN

    DECLARE msg VARCHAR(50);

    IF EXISTS (SELECT * FROM hangar WHERE hangar_city = old_aircraft_name_in)
    THEN UPDATE hangar SET hangar_city = new_aircraft_name_in
         WHERE hangar_city = old_aircraft_name_in; SET msg = 'OK';

    ELSE SET msg = 'No such hangar';
    END IF;
  END //

DELIMITER ;