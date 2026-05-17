create database hotel;

USE hotel;
CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100),
    surname VARCHAR(100),
	password VARCHAR(255),
    email VARCHAR(100) UNIQUE,
    phone VARCHAR(20),
    birth_date DATE,
    gender CHAR(1),
    country VARCHAR(100),
    role VARCHAR(100)
);

INSERT INTO users (
    name,
    surname,
    password,
    email,
    phone,
    birth_date,
    gender,
    country,
    role
) VALUES (
    'Juan',
    'Lopez',
    '12345678',
    'correo@gmail.com',
    '6121234567',
    '2000-05-10',
    'M',
    'Mexico',
    'Admin'
);

CREATE TABLE room_types(
    typeId INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    bedType VARCHAR(100) NOT NULL,
    capacity INT NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    imagePath TEXT,
    features TEXT,
    featured BOOLEAN DEFAULT FALSE
);

INSERT INTO room_types
(name, bedType, capacity, price, imagePath, features, featured)
VALUES
(
'Suite Deluxe',
'King Bed',
4,
28000,
'/assets/img/rooms/room1.png',
'Wifi|Desayuno|Jacuzzi|Vista al mar',
true
),

(
'Standard Room',
'Queen Bed',
3,
20000,
'/assets/img/rooms/room2.png',
'Wifi|Desayuno|TV|AC',
true
),

(
'Premium Room',
'King Bed',
3,
24000,
'/assets/img/rooms/room3.png',
'Wifi|Desayuno|Jacuzzi|Vista al mar',
true
);

CREATE TABLE rooms(
    roomId INT AUTO_INCREMENT PRIMARY KEY,
    roomNumber INT NOT NULL,
    floor INT NOT NULL,
    typeId INT NOT NULL,
    available BOOLEAN DEFAULT TRUE,
    CONSTRAINT uk_room_number UNIQUE (roomNumber),

    FOREIGN KEY (typeId)
        REFERENCES room_types(typeId)
        ON DELETE RESTRICT
        ON UPDATE CASCADE
);

INSERT INTO rooms
(roomNumber, floor, typeId, available)
VALUES

(101,1,1,true),
(102,1,1,false),

(201,2,2,true),
(202,2,2,true),

(301,3,3,true);