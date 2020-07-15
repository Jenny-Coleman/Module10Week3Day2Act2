import java.util.*;
import org.hibernate.*;
import org.hibernate.cfg.Configuration;

public class ManageEmployee {
    private static SessionFactory factory;

    public static void main(String args[]) {
        try {
            factory = new AnnotationConfiguration().
                      configure().
                      //addPackage("com.xyz") //add package if used.
                      addAnnotatedClass(Employee.class).
                      buildSessionFactory();
         } catch (Throwable ex) { 
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex); 
         }

        ManageEmployee ME = new ManageEmployee();

        // Add few employee records in DB
        Integer empID1 = ME.addEmployee("Zara", "Ali", 1000);
        Integer empID2 = ME.addEmployee("Daisy", "Das", 5000);
        Integer empID3 = ME.addEmployee("John", "Paul", 10000)

        // List all the employees
        ME.listEmployees();

        // Update employee's record
        ME.updateEmployee(empID1, 5000);

        // Delete an employee from DB;
        ME.deleteEmployee(empID2);

        // List the changed employees
        ME.listEmployees();
    }

    // Methos to CREATE an employee in DB
    public Integer addEmployee(String fname, String lname, int salary) {
        Session session = factory.openSession();
        Tranaction tx = null;
        Integer employeeID = null;

        try {
            tx = session.beginTransaction();
            Employee employee = new Employee(fname, lname, salary);
            employeeID = (Integer) session.save(employee);
            tx.commit();
        } catch (HibernateException e) {
            if (txt = null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }

        return employeeID;
    }

    // Method to READ all employees
    public void listEmployees() {
        Session session = factory.openSession();
        Tranaction tx = null;

        try {
            tx = session.beginTransaction();
            List employees = session.createQuery("FROM Employee").list();

            for (Iterator iterator = employees.iterator(); iterator.hasNext();) {
                Employee employee = (Employee) iterator.next();
                System.out.print("First Name: " + employee.getFirstName());
                System.out.print("  Last Name: " + employee.getLastName());
                System.out.print("  Salary: " + employee.getSalary());
            }

            tx.commit();
        } catch (HibernateException e) {
            if (txt = null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    } 

    // Method to UPDATE salary of employee
    public void updateEmployee(Integer EmployeeID, int salary) {
        Session session = factory.openSession();
        Tranaction tx = null;

        try {
            tx = session.beginTransaction();
            Employee employee = (Employee) session.get(Employee.class, EmployeeID);
            employee.getSalary(salary);
            session.update(employee);
            tx.commit();

        } catch (HibernateException e) {
            if (txt = null) tx.rollback();
            e.printStackTrace();

        } finally {
            session.close();
        }
    }

    // Method to DELETEan employee from the records
    public void deleteEmployee(Integer EmployeeID) {
        Session session = factory.openSession();
        Tranaction tx = null;

        try {
            tx = session.beginTransaction();
            Employee employee = (Employee) session.get(Employee.class, EmployeeID);
            employee.getSalary(salary);
            session.delete(employee);
            tx.commit();

        } catch (HibernateException e) {
            if (txt = null) tx.rollback();
            e.printStackTrace();

        } finally {
            session.close();
        }
    }
}