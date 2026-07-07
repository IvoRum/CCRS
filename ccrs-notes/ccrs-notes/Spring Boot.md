First what is the difference between Spring and Spring boog?

## String
String Is the framework of tool and infrastructure that are used for developing java applications.
- Spring JDBC
- Spring MVC
- Spring Security
- Spring AOP
- Spring ORM
- Spring Test

## String Boot
Spring Boot is a project build on top of String that simplifies the set up and development process of Spring framework. In another words Spring Boot is a extension of Spring, which eliminates boiler plate configs for setting up Spring applications. 

| Feature               | Spring                                                               | Spring Boot                                                                        |
| --------------------- | -------------------------------------------------------------------- | ---------------------------------------------------------------------------------- |
| Purpose               | A general framework for building Java applications with flexibility. | A streamlined framework for quickly creating production-ready Spring applications. |
| Configuration         | Requires manual XML or annotation-based configuration.               | Provides auto-configuration to reduce boilerplate setup.                           |
| Setup Complexity      | More complex and time-consuming to set up projects.                  | Simple setup with minimal configuration needed.                                    |
| Server Requirement    | Needs an external server like Tomcat or Jetty.                       | Comes with embedded servers; no external setup required.                           |
| Dependency Management | Developers manually manage dependencies.                             | Uses Starter POMs for simplified dependency management.                            |
| Learning Curve        | Steeper due to modular structure and manual configurations.          | Easier and faster for developers to start and deploy applications.                 |
## Spring IoC Container and Beans
Inversion of Control (IoC) principle. Dependency injection (DI) is a specialized form of IoC, whereby objects define their dependencies

The IoC container then injects those dependencies when it creates the bean.

The bean itself controlling the instantiation or location of its dependencies by using direct construction of classes or a mechanism such as the Service Locator pattern.

Links:
[Spring IoC and Beans](https://docs.spring.io/spring-framework/reference/core/beans/introduction.html)
### Beans
In Spring, the objects that form the backbone of your application and that are managed by the Spring IoC container are called beans. A bean is an object that is instantiated, assembled, and managed by a Spring IoC container.

Within the container itself, these bean definitions are represented as `BeanDefinition` objects, which contain (among other information) the following metadata:
- A package-qualified class name: typically, the actual implementation class of the bean being defined.
- Bean behavioral configuration elements, which state how the bean should behave in the container (scope, lifecycle callbacks, and so forth).
- References to other beans that are needed for the bean to do its work. These references are also called collaborators or dependencies.
- Other configuration settings to set in the newly created object — for example, the size limit of the pool or the number of connections to use in a bean that manages a connection pool.

Links:
[Bean](https://docs.spring.io/spring-framework/reference/core/beans/definition.html)

# Spring container
The container gets its instructions on the components to instantiate, configure, and assemble by reading configuration metadata.

A Spring IoC container manages one or more beans. These beans are created with the configuration metadata that you supply to the container

![[Pasted image 20260707071556.png]]

Configuration Metadata
As the preceding diagram shows, the Spring IoC container consumes a form of configuration metadata. This configuration metadata represents how you, as an application developer, tell the Spring container to instantiate, configure, and assemble the components in your application.


# Annotation Configurations

Spring _ApplicationContext_ is where Spring holds instances of objects that it has identified to be managed and distributed automatically. The context injects by TYPE.

We create **Spring beans** so that the **Spring IoC container** can manage them and inject them wherever they are needed.
## Bean
Used on Method lave to build a ready to used object. Which the Spring IoC will know how to use in other injections.
The **Spring Context** (more formally, the **Application Context**) is **the container that holds and manages all the Spring beans**.

If you are making a Spring beam from scracth. You need to put in the a @Configuration class and them create a method with the annotation @ Bean.

Links:
[Bean docs](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/context/annotation/Bean.html)

## Component
In short @Component will create a Bean for the class.

_@Component_ is an annotation that allows Spring to detect our custom beans automatically.
In other words, without having to write any explicit code, Spring will:
- Scan our application for classes annotated with _@Component_
- Instantiate them and inject any specified dependencies into them
- Inject them wherever needed
However, most developers prefer to use more specialized stereotype annotations to serve this function.

Such classes are considered as candidates for auto-detection when using annotation-based configuration and classpath scanning.
A component may optionally specify a logical component name via the [`value`](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/stereotype/Component.html#value\(\)) attribute of this annotation.

Link:
[Component docs](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/stereotype/Component.html)
[Bealdung post](https://www.baeldung.com/spring-component-annotation)
## Repository
Indicates that an annotated class is a "Repository", originally defined by [[Domain-Driven Design]] (Evans, 2003) as "a mechanism for encapsulating storage, retrieval, and search behavior which emulates a collection of objects".


Link: 
[Repository](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/stereotype/Repository.html)

## Autowired
Will provide a proxy if needed for injections.


# Injection 💉 

## Injected circular dependencies
**Why field/setter injection "fixes" it, and constructor doesn't:** This is the core technical insight you missed. Spring has a **three-level cache mechanism** for singleton beans:
- Level 1: fully initialized singletons
- Level 2: "early" bean references (raw instance, not yet dependency-injected)
- Level 3: factory objects that can produce early references (for proxying)
With **field/setter injection**, Spring can call the **no-arg constructor first**, put that half-built object into the early-reference cache, _then_ inject the dependencies afterward via reflection.So when `ServiceB` needs `ServiceA` mid-construction, Spring hands it the early (not-yet-fully-injected) reference — it works because the object _exists_ before its fields are populated. With **constructor injection**, the object literally cannot exist until the constructor completes — there's no "early reference" to hand out, since the dependency is required _at instantiation time_. That's why constructor-injected circular dependencies always fail, while field/setter-injected ones can succeed (though it's still considered a code smell).

**`@Lazy`** on one of the injected dependencies — Spring injects a proxy that only resolves the real bean on first method call, breaking the eager construction cycle

**`ApplicationContext.getBean()` lookup manually / `ObjectProvider<T>`** — defers resolution

# Transactional
Transactional creates a proxy around the bean using it. Spring implements transactions by using [[Aspect-Oriented Programming]]. When calling a @Transactional is injected it doesn't inject the raw Service but a proxy interceptor.
- Opens/joins a DB transaction (via `PlatformTransactionManager`, e.g. grabbing a `Connection` from the pool and calling `setAutoCommit(false)`)
- Delegates to the real method body
- Commits or rolls back afterward


If we have this situation:
```java
@Service
public class OrderService {

    @Autowired
    private OrderService self; // or however you'd get a proxy

    @Transactional
    public void placeOrder(Order order) {
        saveOrder(order);
        notifyWarehouse(order);
    }

    @Transactional
    public void saveOrder(Order order) {
        // saves to DB
    }

    public void notifyWarehouse(Order order) {
        // calls external service
    }
}
```
The self -invocation will bypass the proxy entirely. `this` refers to the raw target object, not the proxy wrapping it. So `saveOrder()`'s `@Transactional` annotation is **completely ignored** in that call path. To work aorun this you need to use the self property. So that the method goes through the transaction: `self.saveOrder()` then `this.saveOrder()` will fix the problem. SO the call goes through the [[Aspect-Oriented Programming|AOT]] proxy.

> [!info] Proxy needs to be invoked for variables like `REQUIRES_NEW` are used!!!!!

# Hikary
**The formula** (this is a known one, from HikariCP's own docs, based on a PostgreSQL "Pool Sizing" paper by Bob Wan/Neustadt): `pool size = Tn × (Cm - 1) + 1` where `Tn` = number of threads (i.e., how many concurrent tasks might need a connection), `Cm` = number of connections each transaction typically holds concurrently (usually 1). More practically, the widely-quoted heuristic is: `connections = ((core_count * 2) + effective_spindle_count)` The real point interviewers want: **a connection pool should be sized based on the database server's actual concurrency capacity** (CPU cores, disk I/O, max_connections setting on Postgres itself), **not on how many app threads you have**. Bigger pool ≠ better — past a certain point, more connections just cause more contention _on the DB side_ (context switching, lock contention), so blindly increasing it is also wrong. You didn't mention this — that "more pool = better" is a trap too.

Link:
[Pool Sizing and Performance Tuning](https://deepwiki.com/brettwooldridge/HikariCP/4.2-pool-sizing-and-performance-tuning)
