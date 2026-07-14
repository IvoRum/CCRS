[MVCC](https://www.youtube.com/shorts/rr3E7H31Sws)
[vacuume](https://www.youtube.com/shorts/HIcW2584Sd4) 

# Indexing

```sql
CREATE TABLE orders (
    id BIGSERIAL PRIMARY KEY,
    customer_id BIGINT NOT NULL,
    status VARCHAR(20) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    total NUMERIC(10,2)
);
```

```sql
SELECT * FROM orders
WHERE customer_id = 12345
AND status = 'PENDING'
ORDER BY created_at DESC
LIMIT 20;
```

This is the proper INDEX for optimizing the result
```sql
CREATE INDEX idx_orders_customer_status_created 
ON orders(customer_id, status, created_at DESC);
```

The rule: **equality-filter columns go first** (`customer_id`, `status` — both use `=`), and the **sort/range column goes last** (`created_at`). This is because a B-tree index is fundamentally a sorted structure — once Postgres narrows down to the `(customer_id, status)` prefix, the remaining entries in that sub-range are _already sorted by `created_at`_ within the index itself.

> [!info] The order of the index maters because it is related to how postgres will create the b-tree.
> First by `customer_id`, then within each `customer_id` group by `status`, then within each `(customer_id, status)` group by `created_at`.

**Covering index — you didn't answer this part.** A **covering index** is one that includes _all_ columns needed by the query (via `INCLUDE`), so Postgres never needs to touch the heap (table) at all — it can answer the whole query from the index alone. Since this query does `SELECT *`, a true covering index would need every column, which usually isn't worth it. But if the real query only needed a few columns (say, `id, total`), you could do:

```sql
CREATE INDEX idx_orders_covering 
ON orders(customer_id, status, created_at DESC) INCLUDE (total);
```

This enables an **Index Only Scan** (a specific plan node — worth knowing that name) instead of Index Scan + heap fetch. **Trade-off:** covering indexes are larger (more disk, slower writes/updates since more data must be maintained per index), and `SELECT *` defeats the purpose anyway since you'd need to include every column, at which point you've nearly duplicated the table.