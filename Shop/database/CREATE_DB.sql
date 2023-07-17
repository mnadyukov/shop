CREATE DATABASE ".\SHOP.FDB" USER 'SYSDBA' PASSWORD 'masterkey' SET NAMES 'UTF8' DEFAULT CHARACTER SET UTF8;

CREATE TABLE role(
	name VARCHAR(32),
	description VARCHAR(64)
);
INSERT INTO role VALUES ('customer','Покупатель');
INSERT INTO role VALUES ('employee','Работник магазина');
INSERT INTO role VALUES ('admin','Системный администратор');

CREATE TABLE person(
	id BIGINT,
	login VARCHAR(64),
	password VARCHAR(64),
	role VARCHAR(64),
	first_name VARCHAR(64),
	second_name VARCHAR(64),
	patronymic VARCHAR(64),
	address VARCHAR(512),
	phone VARCHAR(64),
	email VARCHAR(512),
	UNIQUE(login)
);
CREATE SEQUENCE person_id;
INSERT INTO person VALUES (1,'customer','customer','customer','Иван','Иванов','Иванович','100001, РФ, г. Москва, ул. Ленина, д.1','+79991234567','customer@shop.ru');
INSERT INTO person VALUES (2,'employee','employee','employee','Петр','Петров','Петрович','100002, РФ, г. Москва, ул. Ленина, д.2','+79991234568','employee@shop.ru');
INSERT INTO person VALUES (3,'sysadmin','sysadmin','admin','Семен','Семенов','Семенович','100003, РФ, г. Москва, ул. Ленина, д.3','+79991234569','admin@shop.ru');
ALTER SEQUENCE person_id RESTART WITH 4;

CREATE TABLE product(
	id BIGINT,
	name VARCHAR(512),
	description BLOB SUB_TYPE TEXT,
	price DECIMAL(18,2),
	product_catalog BIGINT,
	status SMALLINT
);
CREATE SEQUENCE product_id;

CREATE TABLE product_status(
	id SMALLINT,
	name VARCHAR(64)
);
CREATE SEQUENCE product_status_id;
INSERT INTO product_status VALUES (1,'активен');
INSERT INTO product_status VALUES (2,'распродажа');
INSERT INTO product_status VALUES (3,'снят с производства');
INSERT INTO product_status VALUES (4,'скоро в продаже');
ALTER SEQUENCE product_status_id RESTART WITH 5;

CREATE TABLE product_catalog(
	id BIGINT,
	parent BIGINT,
	name VARCHAR(512)
);
CREATE SEQUENCE product_catalog_id;

CREATE TABLE zakaz(
	id BIGINT,
	customer BIGINT,
	zakaz_date DATE,
	delivery_date DATE,
	status SMALLINT,
	note VARCHAR(1000)
);
CREATE SEQUENCE zakaz_id;

CREATE TABLE zakaz_product(
	zakaz BIGINT,
	ordinal INTEGER,
	product BIGINT,
	price DECIMAL(18,2),
	quantity DECIMAL(18,1)
);

CREATE TABLE zakaz_status(
	id SMALLINT,
	name VARCHAR(64)
);
CREATE SEQUENCE zakaz_status_id;
INSERT INTO zakaz_status VALUES (1,'Ожидает оплаты');
INSERT INTO zakaz_status VALUES (2,'Ожидает комплектации');
INSERT INTO zakaz_status VALUES (3,'В комплектации');
INSERT INTO zakaz_status VALUES (4,'Ожидает отгрузки');
INSERT INTO zakaz_status VALUES (5,'В пути');
INSERT INTO zakaz_status VALUES (6,'Получен');
INSERT INTO zakaz_status VALUES (7,'Возвращен');
INSERT INTO zakaz_status VALUES (8,'Утерян при доставке');
INSERT INTO zakaz_status VALUES (9,'Отменен');
ALTER SEQUENCE zakaz_status_id RESTART WITH 10;