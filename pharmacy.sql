create database Pharmacy;
use Pharmacy;

create table Admin(
	id int auto_increment primary key,
    username varchar(100) not null,
    password varchar(50) not null
);

insert into Admin(username, password) VALUES ('admin', '123456');

CREATE TABLE Customer (
    id INT AUTO_INCREMENT PRIMARY KEY,
    fullName VARCHAR(255) NOT NULL,
    phoneNum VARCHAR(20) NOT NULL,
    registrationDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    -- total DOUBLE DEFAULT 0.00,
    loyaltyPoints INT DEFAULT 0
);

create table medicine(
	id int auto_increment primary key,
    medicine_id varchar(100) not null,
    productName varchar(100) not null,
    category varchar(100) not null,
    quantity int not null,
    price varchar(100) not null,
    status varchar(100) not null
);

CREATE TABLE Purchases (
    purchaseId INT PRIMARY KEY,
    customerId INT,
    medicineId VARCHAR(50),
    quantityPurchased INT,
    purchaseDate DATE,
    totalAmount DOUBLE,
    categoryPurchased VARCHAR(50),
    FOREIGN KEY (customerId) REFERENCES Customer(id),
    FOREIGN KEY (medicineId) REFERENCES medicine(medicine_id)
);

SELECT
    YEAR(purchaseDate) AS Year,
    WEEK(purchaseDate) AS Week,
    MONTH(purchaseDate) AS Month,
    QUARTER(purchaseDate) AS Quarter,
    SUM(totalAmount) AS TotalIncome,
    COUNT(purchaseId) AS TotalTransactions
FROM
    Purchases
GROUP BY
    YEAR(purchaseDate), WEEK(purchaseDate)
ORDER BY
    Year, Week;



drop table Customer;

select * from Admin;
select * from Customer;
select * from medicine;