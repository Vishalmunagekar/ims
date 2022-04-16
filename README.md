# inventory-management

## h2 console access : 
http://localhost:8080/ims/h2-console

## swagger access : 
http://localhost:8080/ims/swagger-ui.html

## Database properties
    url: jdbc:mysql://localhost:3306/InventoryManagement
    username: root
    password: manager
    driverClassName:
    
    url: jdbc:h2:mem:InventoryManagement
    username: sa
    password: password
    driverClassName: org.h2.Driver

    url: jdbc:db2://localhost:50000/db
    username: tpsuser
    password: s3cret
    driverClassName: com.ibm.db2.jcc.DB2Driver
    
    database-platform: org.hibernate.dialect.DB2Dialect
    database-platform: org.hibernate.dialect.H2Dialect

## Spring boot application run on tomcat
```
# Buil Spring boot application
mvn clean install

# Run Spring Boot app using Maven:
mvn spring-boot:run

# Run Spring Boot app with java -jar command
java -jar target/ims-0.0.1-SNAPSHOT.jar
```

## Initialize a Database Using Basic SQL Scripts
Spring JDBC has a DataSource initializer feature. Spring Boot enables it by default and loads SQL from the standard locations schema.sql and data.sql (in the root of the classpath). In addition Spring Boot will load the schema-${platform}.sql and data-${platform}.sql files (if present).

- You may define the platform with: spring.datasource.platform=oracle.
- You may change the name of the sql script to load with: spring.datasource.data=myscript.sql.
- Along with data.sql, Spring-boot also loads schema.sql (before data.sql).
- You could also have an "update or insert" logic in your data.sql: oracle sql: update if exists else insert

## Password validation using regexp
    (?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}
- (?=.*[0-9]) a digit must occur at least once
- (?=.*[a-z]) a lower case letter must occur at least once
- (?=.*[A-Z]) an upper case letter must occur at least once
- (?=.*[@#$%^&+=]) a special character must occur at least once
- (?=\\S+$) no whitespace allowed in the entire string
- .{8,} at least 8 characters