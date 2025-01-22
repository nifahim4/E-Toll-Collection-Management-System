
CREATE TABLE etoll (
    id INT AUTO_INCREMENT PRIMARY KEY,
    driverName VARCHAR(50),
    driverLisence VARCHAR(50),
    vehicleType VARCHAR(50),
    vehicleNumber VARCHAR(50),
    vehicleModel VARCHAR(50),
    date DATETIME DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO etoll (driverName, driverLisence, vehicleType, vehicleNumber, vehicleModel)
VALUES ('rami', 'dasda', 'rush', 'dasds3231', 'Toyota rush');

select * from etoll;

SELECT id, driverName, driverLisence, vehicleType, vehicleNumber, vehicleModel, 
DATE_FORMAT(create_at, '%c/%e/%Y') AS formatted_date
FROM etoll;

SELECT * 
FROM etoll
WHERE create_at BETWEEN STR_TO_DATE('1/9/2025', '%c/%e/%Y') AND STR_TO_DATE('1/30/2025', '%c/%e/%Y');

USE nakibul;
SELECT * FROM etoll;

DELETE FROM etoll where id>0;

TRUNCATE TABLE etoll;

ALTER TABLE etoll RENAME COLUMN create_at TO date;

