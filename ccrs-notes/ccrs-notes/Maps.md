
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

# Map Three
Java's map implementation backed by a **Red-Black tree** (a self-balancing binary search tree), as opposed to `HashMap` which uses hash buckets.
```java
import java.util.TreeMap;

public class Main {
    public static void main(String[] args) {
        TreeMap<Integer, String> tree = new TreeMap<>();

        tree.put(5, "five");
        tree.put(2, "two");
        tree.put(8, "eight");
        tree.put(1, "one");

        System.out.println(tree.get(2));      // "two"
        System.out.println(tree);              // {1=one, 2=two, 5=five, 8=eight} — sorted by key!
    }
}
```


|                      | HashMap                               | TreeMap                             |
| -------------------- | ------------------------------------- | ----------------------------------- |
| Underlying structure | Array of buckets + linked lists/trees | Red-Black tree (self-balancing BST) |
| Order                | No guaranteed order                   | Sorted by key                       |
| Get/Put/Remove       | O(1) average                          | **O(log n)** guaranteed             |
| Extra features       | None                                  | Range queries, min/max, etc.        |

# Frequency counting
video:
https://www.youtube.com/shorts/qfM4Ms_f1Sc?feature=share
It is used when you need counts of elements/characters.
```java
// Valid anagram 
Map<Character, Integer> freq = new HashMap<>(); 
for (char c : s.toCharArray()) freq.merge(c, 1, Integer::sum); 
for (char c : t.toCharArray()) freq.merge(c, -1, Integer::sum); 
return freq.values().stream().allMatch(v -> v == 0);
```