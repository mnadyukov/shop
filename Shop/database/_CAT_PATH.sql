CONNECT ".\SHOP.FDB" USER 'SYSDBA' PASSWORD 'masterkey';

WITH RECURSIVE groups (id,name,parent) AS (
	SELECT 
		id,
		name,
		parent 
	FROM 
		product_catalog  
	WHERE 
		id=7 
	UNION ALL
	SELECT
		pc.id,
		pc.name,
		pc.parent 
	FROM 
		product_catalog pc join groups g on g.parent=pc.id 
)
SELECT name from groups;