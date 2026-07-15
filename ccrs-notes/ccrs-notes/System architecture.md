System architecture has a lot to do with what you aim to do. If you don't know what you are doing use a decentralized module strategy to start and you will figurate out down the rode.
Programming is structuring data in a way that it fit in a architecture.


[[Domain-Driven Design]]
[[Event-Driven Architecture]]
[[Serverless-First & Edge Computing]]

# You never answered the actual short-code generation strategy

- **Pre-generated key pool**: a separate service pre-generates batches of unique base62 codes (e.g., using distributed **Snowflake-style IDs** or **UUID-derived** codes) and stores them in a "available codes" pool table/queue; app servers grab a batch (e.g., 1000 at a time) to hand out locally, drastically reducing contention on any single counter.
- **Snowflake ID**: distributed ID generation (timestamp + machine ID + sequence number) — no central coordination needed, IDs are unique across many app instances by construction. Then base62-encode the resulting numeric ID.
- **Hash-based**: hash the long URL (e.g., MD5/SHA) and take the first 6-7 chars — simple, but needs **collision handling** (check DB, if taken, add a salt/counter and rehash). Worth mentioning collision probability and retry logic if you go this route.