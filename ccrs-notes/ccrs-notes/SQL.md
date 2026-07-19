sql - isolation layers
idempotensy
types of indexes

# Simple table 

| Method    | Description                                                                                                                                                                                                                                                            | When to use                                                               |
| --------- | ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ------------------------------------------------------------------------- |
| LEFT JOIN | Тука се гледа какво се join-ва и по него се определя от главната таблица. Тоест ако в гланата таблица референцията към вклучващата таблица липсва то пак ще вкара данните които си избрал от главната таблица и данните от вкарващата таблица ще се изпишат като null. | Когато искаме данните от главната таблица но ще филтрираме но включващата |
|           |                                                                                                                                                                                                                                                                        |                                                                           |
![[Pasted image 20260719093143.png]]

# **Question 1 (PostgreSQL / Joins & aggregation):**
Given these tables:
```sql
CREATE TABLE customers (
    id INT PRIMARY KEY,
    name VARCHAR(100)
);

CREATE TABLE orders (
    id INT PRIMARY KEY,
    customer_id INT REFERENCES customers(id),
    total NUMERIC(10,2),
    order_date DATE
);
```
Write a SQL query to find **all customers who have never placed an order**, and explain:
1. Which JOIN type you'd use and why.
2. Why a naive `WHERE customer_id != orders.customer_id` approach (or similar) would be wrong.
3. Bonus: what's the difference between using `NOT IN`, `NOT EXISTS`, and a `LEFT JOIN ... WHERE ... IS NULL` for this exact problem — are they equivalent, and is there a subtle trap with one of them (hint: think about NULLs)?


## Answer 
```sql
SELECT c.*
FROM customers c
LEFT JOIN orders o ON c.id = o.customer_id
WHERE o.id IS NULL;
```
**`LEFT JOIN`** keeps **every row from `customers`** (the "left" table), and attaches matching `orders` rows where they exist — or `NULL` in all the `orders` columns where no match exists. Since we only care about customers with no orders, this is exactly what we need.


