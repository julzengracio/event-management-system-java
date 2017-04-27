package EventManagementCompany;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * @author n00145647
 * Jullian Engracio
 * Start Date: 19-10-2015
 * Algorithms & Data Structures * 
 */

public class DBConnect {
    
        static Connection sConnect;
        private static final String USERNAME = "root";
        private static final String PASSWORD = "";
        private static final String CONN_String = "jdbc:mysql://localhost:8080/year2project";
        
        
        public static Connection connect() throws SQLException {
            sConnect = null;
            
            try {
                sConnect = DriverManager.getConnection(CONN_String, USERNAME, PASSWORD);
                //System.out.println("Connected!");
            } 
            catch (SQLException ex) {
                System.err.println(ex);
            } 
            /*
            finally {
                if (sConnect != null) {
                    sConnect.close();
                }
            }
            */
            return sConnect;
        }
}
      
    /*
     
         { 

        public static Connection getInstance() throws ClassNotFoundException, SQLException {
            String host, db, user, password;

            host = "daneel";
            db = "n00145647playground";
            user = "N00145647";
            password = "N00145647";

            if (sConnection == null || sConnection.isClosed()) {
                String url = "jdbc:mysql://" + host + "/" + db;
                Class.forName("com.mysql.jdbc.Driver");
                sConnection = DriverManager.getConnection(url, user, password);
            }

            return sConnection;
        }
    }
    */
