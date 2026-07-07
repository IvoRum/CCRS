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


