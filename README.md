# inventory-management

## h2 console access : 
http://localhost:8080/h2-console

## swagger access : 
http://localhost:8080/swagger-ui.html

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
