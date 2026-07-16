
# What is a collision?

A hash table works by taking a key, running it through a **hash function**, and using the result to decide which "bucket" (slot in the underlying array) to store the value in.

```
hash("apple") -> 42  -> goes in bucket 42
hash("banana") -> 17 -> goes in bucket 17
```

A **collision** happens when two _different_ keys hash to the **same bucket**:

```
hash("apple") -> 42
hash("grape") -> 42   <-- collision! both want bucket 42
```
Since the hash table only has a fixed number of buckets (much smaller than the space of all possible keys), collisions are **mathematically unavoidable** eventually — this is basically the pigeonhole principle. A good hash function just tries to make them rare and spread out.
### Why do collisions happen?

- The hash function maps a huge space of possible keys (e.g., all strings) into a small number of buckets (e.g., 16 or 32 slots).
- Even a great hash function will eventually produce the same output for two different inputs once you have enough keys.
**One-liner for the interview:** _"A collision is when two keys hash to the same bucket — it's unavoidable but minimized with a good hash function and low load factor, and handled via chaining or open addressing when it happens."
