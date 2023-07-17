CONNECT ".\SHOP.FDB" USER 'SYSDBA' PASSWORD 'masterkey';

SELECT COUNT(1) FROM product WHERE product_catalog=5;