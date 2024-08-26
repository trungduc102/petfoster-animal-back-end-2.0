

--chay store nay
-------------------------------------------------------------
CREATE PROCEDURE GetProductTypeRevenueByYear(@year int)
AS
BEGIN
SELECT
    COALESCE(SUM(COALESCE(od.total, 0)), 0) AS total_revenue
FROM
    product_type pt
LEFT JOIN
    product p ON pt.product_type_id = p.[type_id]
LEFT JOIN
    product_repo pr ON pr.product_id = p.product_id
LEFT JOIN
    order_detail od ON od.product_repo_id = pr.product_repo_id
LEFT JOIN (
    SELECT
        o.id,
        [status],
        YEAR(o.create_at) AS order_year
    FROM
        orders o
) AS order_year ON od.order_id = order_year.id
LEFT JOIN (
    SELECT
        o.id,
        COALESCE([status], 'default_status') AS [status]
    FROM
        orders o
) AS order_status ON od.order_id = order_status.id
WHERE
    (order_year.order_year = @year OR order_year.order_year IS NULL)
    AND (COALESCE(order_status.[status], 'default_status') NOT IN ('Cancelled By Customer', 'Cancelled By Admin', 'Waiting'))
GROUP BY
    pt.product_type_name;
END


--chay store nay
-------------------------------------------------------------
CREATE PROCEDURE GetRevenueByYear(@year INT)
AS
BEGIN
WITH AllMonths AS (
    SELECT 1 AS Month
    UNION ALL
    SELECT 2
    UNION ALL
    SELECT 3
    UNION ALL
    SELECT 4
    UNION ALL
    SELECT 5
    UNION ALL
    SELECT 6
    UNION ALL
    SELECT 7
    UNION ALL
    SELECT 8
    UNION ALL
    SELECT 9
    UNION ALL
    SELECT 10
    UNION ALL
    SELECT 11
    UNION ALL
    SELECT 12
)
SELECT COALESCE(SUM(o.total), 0)
FROM AllMonths am
LEFT JOIN orders o ON am.Month = MONTH(o.create_at) AND YEAR(o.create_at) = @year and o.[status] != 'Cancelled By Admin' and o.[status] != 'waiting' and o.[status] != 'Cancelled By Customer'
GROUP BY am.Month
END



-------------------------------------------------------------
--tong doanh thu theo loai
insert into orders values (getdate(), null, 490000, 10)

insert into order_detail values (2, 200, 140000, 133, 'PD0033')
insert into order_detail values (1, 1000, 170000, 133, 'PD0013')
insert into order_detail values (1, 1000, 250000, 133, 'PD0016')

select * from orders
select * from order_detail
select * from product_repo


select sum(a.total)
	from order_detail a
	inner join product b on b.product_id = a.product_id
	inner join product_type c on c.product_type_id = b.[type_id]
	inner join orders o on o.id = a.order_id
	where year(o.create_at) = 2023
	group by product_type_name

------------------------------
--doang thu theo ngay (truyen ngay vao)
select sum(total) from orders where convert(date, create_at) between '2023-10-01' and '2023-10-10'

select p.product_id as productid, product_name as namep, brand, size, sum(quantity) as quantity, sum(od.total) as total
from product p
inner join order_detail od on od.product_id = p.product_id
inner join orders o on o.id = od.order_id
where convert(date, o.create_at) between '2023-10-01' and '2023-10-10'
group by p.product_id, product_name, brand, size

------------------------------------------------------------
CREATE PROCEDURE GetProductTypeRevenueByYear(@year int)
AS
BEGIN
SELECT COALESCE(SUM(od.total), 0)
FROM product_type pt
LEFT JOIN product p ON pt.product_type_id = p.[type_id]
LEFT JOIN product_repo pr ON pr.product_id = p.product_id
LEFT JOIN order_detail od ON od.product_repo_id = pr.product_repo_id
LEFT JOIN (
    SELECT o.id, YEAR(o.create_at) AS order_year
    FROM orders o
) AS order_year ON od.order_id = order_year.id
WHERE order_year.order_year = 2023 OR order_year.order_year IS NULL
GROUP BY pt.product_type_name
END

select * from order_detail b 
inner join product_repo c on c.product_repo_id = b.product_repo_id
inner join product d on d.product_id = c.product_id

