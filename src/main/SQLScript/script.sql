CREATE TABLE productTB(
                          id serial UNIQUE NOT NULL ,
                          product_name varchar(50) NOT NULL ,
                          unit_price numeric(10,2) NOT NULL ,
                          quantity int NOT NULL,
                          import_date DATE DEFAULT current_timestamp
);
INSERT INTO productTB(product_name, unit_price, quantity) VALUES ('coke',2.22,5),
                                                                 ('pepsi',1.25, 5),
                                                                 ('toiletpaper',2.5,10),
                                                                 ('pen',1,20);