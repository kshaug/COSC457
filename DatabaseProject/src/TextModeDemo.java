
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Adam J. Conover, D.Sc. <aconover@towson.edu>
 */
public class TextModeDemo {
    // IMPORTANT:  This has to be set to the proper location!
    static final String JDBC_URL = "jdbc:derby:./COSC457-Chapter3-DB";

    /**
     * Main method to demonstrate the methods in the DbAccess Sample. Note that
     * this class does not do any DIRECT communication with the RDBMS.
     *
     * @param args N/A
     */
    public static void main(String[] args) {
        try {
            DbAccess dba = new DbAccess(JDBC_URL);

            System.out.println("********** DEMO 1 **********");
            StatementDemo1(dba);

            System.out.println("********** DEMO 2 **********");
            StatementDemo2(dba);

            System.out.println("********** DEMO 3 **********");
            PreparedStatementDemo1(dba);

            System.out.println("********** Recursive DEMO **********");
            statementDemoRecursive(dba);

            dba.close();

        } catch (SQLException ex) {
            ex.printStackTrace();  // FOR DEBUGGING ONLY (*always* do better logging in a "real" program!)
        }
    }

    /**
     * Demonstrate "getStatemetResult1()" in DbAccess
     *
     * @param dba The Database Access object
     * @throws SQLException thrown if an error occurs in a while processing a
     * transaction.
     */
    private static void StatementDemo1(DbAccess dba) throws SQLException {
        ResultSet results = dba.getResultExample1();

        while (results.next()) {
            String firstName = results.getString("FNAME");
            String lastName = results.getString("LNAME");
            String mi = results.getString("MINT");
            Date bDate = results.getDate("BDATE");

            // Format a date string based on a locale-specific date format.
            String fmtDate = DateFormat.getDateInstance(DateFormat.LONG).format(bDate);

            System.out.printf("%s, %s %s. was born %s.\n", lastName, firstName, mi, fmtDate);
        }

        results.close();
    }

    /**
     * Demonstrate "getStatemetResult2()" in DbAccess
     *
     * @param dba The Database Access object
     * @throws SQLException thrown if an error occurs in a while processing a
     * transaction.
     */
    private static void StatementDemo2(DbAccess dba) throws SQLException {
        NumberFormat nf = NumberFormat.getCurrencyInstance();

        ResultSet results = dba.getResultExample2();

        // Since this query produces an aggregate result, it produces one (and only
        // one) result row.
        results.next();

        float avgSalary = results.getFloat("avg_salary");
        System.out.println("Average Salary is: " + nf.format(avgSalary));

        results.close();
    }

    /**
     * Demonstrate "getPreparedStatementResult()" in DbAccess
     *
     * @param dba The Database Access object
     * @throws SQLException thrown if an error occurs in a while processing a
     * transaction.
     */
    private static void PreparedStatementDemo1(DbAccess dba) throws SQLException {
        ResultSet results = dba.getResultExample3(4, 'F');
        while (results.next()) {
            float hours = results.getFloat("total_hours");
            String name = results.getString("fname");
            System.out.printf("%s worked %.2f hours.\n", name, hours);
        }
        results.close();
    }

    /**
     * The query will build a list of all employees managed by 888665555 Though
     * the query is technically a "recursive query", actual recursion will often
     * not work, so it must be simulated with stacks or queues.
     *
     */
    private static void statementDemoRecursive(DbAccess dba) throws SQLException {
        List<String> names = dba.allManagedQueued(new LinkedList<String>(), "888665555");

        // This one will fail!
        // List<String> names = dba.allManagedRecursive(new LinkedList<String>(), "888665555");

        // Print the list of found items.
        for (String name : names) {
            System.out.println("Found: " + name);
        }
    }
}
