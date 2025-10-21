# Guide to run JSF Project with PostgreSQL

## Database Configuration

1. **Create PostgreSQL database:**
   ```sql
   CREATE DATABASE your_database_name;
   ```

2. **Create Mt_employee table:**
   ```sql
   CREATE TABLE Mt_employee (
       employee_code VARCHAR(50) PRIMARY KEY,
       employee_name VARCHAR(100) NOT NULL,
       employee_age INTEGER,
       date_of_birth DATE
   );
   ```

3. **Add sample data:**
   ```sql
   INSERT INTO Mt_employee (employee_code, employee_name, employee_age, date_of_birth) 
   VALUES 
   ('EMP001', 'John Doe', 25, '1999-01-15'),
   ('EMP002', 'Jane Smith', 30, '1994-05-20'),
   ('EMP003', 'Bob Johnson', 28, '1996-03-10');
   ```

## Database Connection Configuration

Edit file `src/main/java/com/example/dao/EmployeeDAO.java`:

```java
private static final String DB_URL = "jdbc:postgresql://localhost:5432/your_database_name";
private static final String DB_USER = "your_username";
private static final String DB_PASSWORD = "your_password";
```

Replace:
- `your_database_name`: Your database name
- `your_username`: PostgreSQL username
- `your_password`: PostgreSQL password

## WildFly Configuration

1. **Deploy PostgreSQL Driver:**
   - Copy file `postgresql-42.7.1.jar` to `wildfly/standalone/deployments/` folder
   - Or configure in WildFly Admin Console

2. **Configure DataSource (optional):**
   - You can configure DataSource in WildFly for better connection pool management
   - Or use direct JDBC as currently implemented

## Build and Deploy

1. **Build project:**
   ```bash
   mvn clean package
   ```

2. **Deploy to WildFly:**
   - Copy file `target/hello-world.war` to `wildfly/standalone/deployments/` folder
   - Or use WildFly Admin Console to deploy

## Access Application

Open browser and access: `http://localhost:8080/hello-world/`

## Application Features

1. **Load All Employees:** Display all employees from database
2. **Search:** Find employee by employee code
3. **Test Connection:** Check database connection
4. **Display Information:** Show detailed information of selected employee

## Notes

- Ensure PostgreSQL is running and accessible
- Check port and database connection info
- If errors occur, check WildFly logs for debugging