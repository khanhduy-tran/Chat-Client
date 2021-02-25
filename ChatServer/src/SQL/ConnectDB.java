package SQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import javax.swing.JOptionPane;

public class ConnectDB {
    private String DB_URL = "jdbc:mysql://localhost:3306/dbwolfchat";
    private String USER_NAME = "root";
    private String PASSWORD = "";
    public static Connection conn;
    public static Statement stmt;

	public ConnectDB() {
		try {

			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(DB_URL,USER_NAME,PASSWORD);
			stmt = conn.createStatement();


		}catch (Exception e) {

			JOptionPane.showMessageDialog(null, "SQL Connect Failed!");
            e.printStackTrace();
		}
	}

}
