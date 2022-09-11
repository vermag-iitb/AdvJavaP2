// Disclaimer: RowSet is used for Select queries only

package AdvanceJava2;

import com.sun.rowset.CachedRowSetImpl;
import com.sun.rowset.JdbcRowSetImpl;
import com.sun.rowset.WebRowSetImpl;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.JdbcRowSet;
import javax.sql.rowset.WebRowSet;
import java.io.FileOutputStream;
import java.sql.ResultSetMetaData;

// Note:
// 1. ROWSET types: JdbcRowSet, CachedRowSet, WebRowSet, FilteredRowSet, JoinRowSet
// (all are sub-interfaces to ResultSet Interface)
// 2. ROWSET properties: Java bean, disconnected (except JdbcRowSet), Serializable
// 3. Java bean properties: class is public, all instance variables are private (accessed
// using setters and getters), implements java.io.Serializable, only default constructor,
// defined inside a package
// 4. Serializable object: it means converting object to bits/bytes, hence being able to
// read/write to file and network, because streams need this format to work
// 5. Every database vendor has their own rowset classes implementing above rowset interfaces.
// 6. Oracle DB has: OracleJDBCRowset, OracleCachedRowset, OracleWebRowset, OracleFilteredRowset, OracleJoinRowset
// 7. SQLServer DB has: JDBCRowsetImpl, CachedRowsetImpl, WebRowsetImpl, FilteredRowsetImpl, JoinRowsetImpl

public class RowSetDemo
{
    public static void main(String[] args) {
        try {
            String url, name, pwd;
            url = "jdbc:jtds:sqlserver://localhost:1433;encrypt=true;trustServerCertificate=true;";
            name = "sa";
            pwd = "reallyStrongPwd123";
// ********************************************************************************************************
// ********************************* JdbcRowSet ***********************************************************
// ********************************************************************************************************
            JdbcRowSet jrs = new JdbcRowSetImpl();
            jrs.setUrl(url);
            jrs.setUsername(name);
            jrs.setPassword(pwd);
            jrs.setCommand("select std_id, sname, Maths from [Demo2Conn].ID_1.Student");
            jrs.execute();
            ResultSetMetaData rm1 = jrs.getMetaData();
            int n1 = rm1.getColumnCount();
            System.out.println("\nUsing JdbcRowSet");
            System.out.println("--------------------");
            for (int i = 1; i <= n1; i++) {
                // To print 'rs' metadata (column names)
                System.out.print(rm1.getColumnName(i) + "\t");
            }
            System.out.println("\n--------------------");
            while (jrs.next()) {
                // To print 'rs' records (actual content)
                for (int i = 1; i <= n1; i++) {
                    // getString() -> gets any datatype content, but in string format
                    System.out.print(jrs.getString(i) + "\t\t");
                }
                System.out.println(); // to print next record in new line
            }
            System.out.println("--------------------");
// ********************************************************************************************************
// ********************************* CachedRowSet *********************************************************
// ********************************************************************************************************
            // Note: CachedRowSet dumps data into cache memory
            CachedRowSet crs = new CachedRowSetImpl();
            crs.setUrl(url);
            crs.setUsername(name);
            crs.setPassword(pwd);
            crs.setCommand("select std_id, sname, Maths from [Demo2Conn].ID_1.Student");
            crs.execute();
            ResultSetMetaData rm2 = jrs.getMetaData();
            int n2 = rm2.getColumnCount();
            System.out.println("\nUsing CachedRowSet");
            System.out.println("--------------------");
            for (int i = 1; i <= n2; i++) {
                // To print 'rs' metadata (column names)
                System.out.print(rm2.getColumnName(i) + "\t");
            }
            System.out.println("\n--------------------");
            while (crs.next()) {
                // To print 'rs' records (actual content)
                for (int i = 1; i <= n2; i++) {
                    // getString() -> gets any datatype content, but in string format
                    System.out.print(crs.getString(i) + "\t\t");
                }
                System.out.println(); // to print next record in new line
            }
            System.out.println("--------------------");
// ********************************************************************************************************
// ********************************* WebRowSet ************************************************************
// ********************************************************************************************************
            // Note:
            // 1. WebRowSet methods to generate xml files (additional feature above CachedRowSet):
            // a. public abstract void writeXml(Writer) throws SQLException;                    ==> used for character stream
            // b. public abstract void writeXml(OutputStream) throws SQLException, IOException; ==> used for byte stream
            // 2. Complete path of the file (with directory) is to be provided where xml file is to be generated
            WebRowSet wrs = new WebRowSetImpl();
            wrs.setUrl(url);
            wrs.setUsername(name);
            wrs.setPassword(pwd);
            wrs.setCommand("select std_id, sname, Maths from [Demo2Conn].ID_1.Student");
            wrs.execute();
            FileOutputStream fos = new FileOutputStream("/Users/vermag5/IntelliJProjects/AdvJavaP2/Data/student.xml");
            wrs.writeXml(fos);
            System.out.println("WebRowSet data is written to student.xml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
// Note: FilteredRowSet is similar to CachedRowSet but allows filtering operations on RowSet.
// Note: JoinRowSet is similar to CachedRowSet but allows joining two or more RowSets data into one.