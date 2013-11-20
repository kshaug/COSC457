
import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * This class is responsible for all communication with the database.
 *
 * @author Adam J. Conover, D.Sc. <aconover@towson.edu>
 */
public class DbAccess {

    // A String representing a simple SQL query.  Notice the "escaped" quoting for
    // SQL inside the Java Quotes.
    static final String STATEMENT =
            "SELECT AVG(SALARY) AS \"avg_salary\" FROM EMPLOYEE";
    // A String representing a SQL query which will be "pre-compiled" by the
    // Database engine leaving the '?' marks as placeholders for actual parameters.
    static final String PREPARED_STATEMENT =
            "SELECT FNAME, SUM(HOURS) as \"TOTAL_HOURS\""
            + "FROM WORKS_ON JOIN EMPLOYEE ON EMPLOYEE.SSN = WORKS_ON.ESSN "
            + "WHERE EMPLOYEE.DNO = ? AND EMPLOYEE.SEX = ? "
            + "GROUP BY FNAME";
    // A Connection to the database engine
    private Connection dbConnection;
    // An object holding a simple statement execution context.  The *actuall*
    // statement to be executed is not determined until the executeQuery
    // Method is called.
    private Statement statement;
    // An object holding a pre-comppiled prepared statement
    private PreparedStatement preparedStatement;

    /**
     * Construct a new DbAccess object
     *
     * @param JDBC_URL The string URL used to find the database
     * @throws SQLException This exception is thrown if there is an error
     * connecting to the database or setting up the prepared statement.
     */
    public DbAccess(String JDBC_URL) throws SQLException {
        this.dbConnection = DriverManager.getConnection(JDBC_URL);

        this.statement = this.dbConnection.createStatement();
        this.preparedStatement = this.dbConnection.prepareStatement(PREPARED_STATEMENT);
    }

    /**
     * Use a queue to build a list of keys yet to explore, simulating a
     * recursive query *
     */
    public List<String> allManagedQueued(List<String> list, String ssn) throws SQLException {
        Queue<String> queryList = new LinkedList<String>();
        queryList.offer(ssn);

        while (!queryList.isEmpty()) {
            String essn = queryList.remove();

            // Should be a prepared statement... but just build a string for the demonstration.
            ResultSet result = this.statement.executeQuery("SELECT * FROM EMPLOYEE where super_ssn = " + essn);

            while (result.next()) {
                String fname = result.getString("FNAME");
                String emp_ssn = result.getString("SSN");
                list.add(fname + " : " + emp_ssn + " -> " + essn);

                if (!queryList.contains(emp_ssn)) {
                    queryList.offer(emp_ssn);
                }
            }

            result.close();
        }

        return list;
    }

    /**
     * A actual recursive query... but it won't work!!! The reason is because
     * (in derby at least) a connection can have only one open result set at a
     * time! The recursive solution creates a series of "partial result sets"
     * which are not permitted.
     */
    public List<String> allManagedRecursive(List<String> list, String ssn) throws SQLException {
        if (ssn != null) {
            // Should be a prepared statement... but just build a string for the demonstration.
            ResultSet result = this.statement.executeQuery("SELECT * FROM EMPLOYEE where super_ssn = " + ssn);

            while (result.next()) {
                String fname = result.getString("FNAME");
                String emp_ssn = result.getString("SSN");
                list.add(fname + " : " + emp_ssn + " -> " + ssn);

                list = allManagedRecursive(list, emp_ssn);
            }

            result.close();
        }

        return list;
    }

    /**
     * Executes a simple query and reruns the results. This method demonstrates
     * the most basic JDBC usage.
     *
     * @return The result of "SELECT * FROM EMPLOYEE"
     * @throws SQLException Thrown if an error occurs while querying the
     * database.
     */
    public ResultSet getResultExample1() throws SQLException {
        ResultSet result = this.statement.executeQuery("SELECT * FROM EMPLOYEE");
        return result;
    }

    /**
     * Executes a simple query and reruns the results. This method is pretty
     * much the same as the previous one, except for the usage of a class
     * variable.
     *
     * @return The result the execution of of the SQL string referenced by
     * STATEMENT.
     * @throws SQLException Thrown if an error occurs while querying the
     * database.
     */
    public ResultSet getResultExample2() throws SQLException {
        ResultSet result = this.statement.executeQuery(STATEMENT);
        return result;
    }

    /**
     * An Example of a method which uses a prepared statement. In Practice,
     * prepared statements are used far more frequently than simple statements.
     * This due to the fact that: a) prepared statements are more efficient. b)
     * queries are often reused and only differ in their predicate attributes.
     *
     * @param departmentNumber The department number to search for...
     * @param gender The gender to search for...
     * @return The result of the prepared statement after the parameters are
     * substituted and the statement is executed.
     * @throws SQLException Thrown if an error occurs while querying the
     * database.
     */
    public ResultSet getResultExample3(int departmentNumber, Character gender) throws SQLException {
        this.preparedStatement.clearParameters();
        this.preparedStatement.setInt(1, departmentNumber);
        this.preparedStatement.setString(2, gender.toString());
        ResultSet result = this.preparedStatement.executeQuery();

        return result;
    }

    /**
     * Be a good citizen an close the database connection when done! (Must be
     * called manually from the main program).
     *
     * From a design pattern standpoint, this is known as a "delegate" method,
     * since it does nothing but route a call to the primary composition object
     * in this class (dbConnection).
     */
    void close() {
        try {
            if (this.dbConnection != null && !this.dbConnection.isClosed()) {
                this.dbConnection.close();
            }
        } catch (SQLException ex) {
            System.err.println("Error closing dabase connection: " + ex.getMessage());
        }
    }

    /**
     * Finalizers in Java are a strange beast, as they are not actually
     * guaranteed ever execute. They are really only useful in "automatic
     * garbage collection" scenarios... but it doesn't hurt to include one for
     * completeness.
     */
    @Override
    public void finalize() throws Throwable {
        super.finalize();
        this.close();
    }

    /**
     * ************************************************************************
     * Gets a list of departments from the database.
     *
     * This is only used in the GUI example but still serves as an example of
     * how to retrieve a single column into a list. (A relatively common
     * occurrence).
     *
     * @return A List object containing the departments.
     * ************************************************************************
     */
    List<Integer> getDepartmentList() {
        List<Integer> departments = new LinkedList<Integer>();
        try {
            ResultSet result = this.statement.executeQuery("SELECT DNUMBER FROM DEPARTMENT ORDER BY DNUMBER ASC");
            while (result.next()) {
                departments.add(result.getInt(1));
            }
        } catch (SQLException ex) {
            departments.add(Integer.MIN_VALUE);
            ex.printStackTrace();  // FOR DEBUGGING ONLY (*always* do better logging in a "real" program!)
        }

        return departments;
    }
}
