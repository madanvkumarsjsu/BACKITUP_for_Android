package com.sjsu.backitup;
import java.sql.Connection;
import java.sql.DriverManager;
//import java.sql.ResultSet;
//import java.sql.Statement;



public class DataBaseConnection {

	Connection connect = null;

	public Connection getDataBaseConnection(){
		if(connect == null){
			try {
				Class.forName("com.mysql.jdbc.Driver");// loading MySQL driver
				connect = DriverManager.getConnection("jdbc:mysql://cmpe277.c38qsf0avgvg.us-west-1.rds.amazonaws.com:3306/CMPE277?user=root&password=rootcmpe277");
				//Set up connection with DB, username, password
			} catch (Exception e) {
				System.out.println("Exception in Login::"+e.getMessage());
				e.printStackTrace();
			}
		}
		return connect;
	}

	/*public static void main(String[] args) {

		DataBaseConnection db = new DataBaseConnection();
		Statement stmt;
		String qType = "Select * from USER";
		Connection conn = db.getDataBaseConnection();
		try {
			stmt = conn.createStatement();
			// Statements allow to issue SQL queries to the database
			ResultSet rs = stmt.executeQuery(qType);
			while(rs.next()){
				System.out.println("username>>>>>>>>>>>>>>>"+rs.getString("USERNAME"));
				System.out.println("username>>>>>>>>>>>>>>>"+rs.getString("PASSWORD"));
			}
		} catch (Exception e) {
			System.out.println("Error in execute query::"+e.getMessage());
			e.printStackTrace();
		}
	}*/
}