# Dependency injection
"Dependency injection via constructor, not field injection" — shows you know the testability/immutability trade-off, not just "I use `@Autowired`."
### Field injection (the "easy" way most beginners use)
```java
@Service
public class OrderService {
    @Autowired
    private PaymentService paymentService;

    @Autowired
    private InventoryService inventoryService;
}
```
Looks clean, but has real problems:
### Constructor injection (what interviewers want to hear you prefer)
```java
@Service
public class OrderService {
    private final PaymentService paymentService;
    private final InventoryService inventoryService;

    public OrderService(PaymentService paymentService, InventoryService inventoryService) {
        this.paymentService = paymentService;
        this.inventoryService = inventoryService;
    }
}
```
### Why constructor injection is better — the two concrete reasons
1. Immutability
With constructor injection, you can mark the fields `final`. Once the object is built, those dependencies **can never be reassigned**
2. Testability — this is the bigger one interviewers actually care about

# AOP proxy (Aspeckt)
Anytime discussing `@Transactional`, `@Async`, `@Cacheable` — shows you understand these aren't magic, they're proxy interception.

# **"N+1 query problem"** 
Anytime discussing JPA/Hibernate performance.
**The N+1 problem:** you fetch 1 list of parent entities, then Hibernate silently fires 1 extra query _per row_ to fetch each one's related child data — N+1 total queries instead of 1 or 2.
**Example:**
```java
List<Author> authors = authorRepository.findAll(); // 1 query

for (Author author : authors) {
    System.out.println(author.getBooks().size()); // triggers 1 query PER author
}
```

If there are 100 authors, that's **1 query to get authors + 100 queries to get each author's books = 101 queries**, when it could've been done in 1 or 2.

**Why it happens:** `@OneToMany`/`@ManyToOne` relationships default to **lazy loading** — Hibernate doesn't fetch `books` until you actually call `.getBooks()`, and it fetches it **one entity at a time**, not batched.
- **`JOIN FETCH`** in a JPQL query: `SELECT a FROM Author a JOIN FETCH a.books` — pulls authors + books in **one single query**.
- **`@EntityGraph`** — annotation-based way to tell Spring Data JPA to eager-fetch a specific relationship for that query.
- **Batch fetching** (`@BatchSize` or `hibernate.default_batch_fetch_size`) — instead of N individual queries, Hibernate groups them into a few `WHERE id IN (...)` queries.

# @Repository why is it diferent?
"`@Repository`, `@Service`, and `@Controller` are all specializations of `@Component` for semantic clarity — but `@Repository` is functionally different because it also enables Spring's exception translation, converting vendor-specific database exceptions into Spring's unified `DataAccessException` hierarchy, so the service layer doesn't get coupled to a specific database driver."