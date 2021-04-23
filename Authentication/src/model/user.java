package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Base64;

public class user {
	private Connection connect() {
		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");

			// Provide the correct details: DBServer/DBName, username, password
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/gb", "root", "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}
	public String readUsers() {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for reading.";
			}
			// Prepare the html table to be displayed
			output = "<table border='1'><tr><th>User name</th><th>password</th><th>email</th><th>type</th>"+ "<th>Update</th><th>Remove</th></tr>";

			String query = "select * from user";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			// iterate through the rows in the result set
			while (rs.next()) {
				String UserID = Integer.toString(rs.getInt("UserId"));
				String userName = rs.getString("userName");
				String password = rs.getString("password");
				String email = rs.getString("email");
				String type = rs.getString("type");
				// Add into the html table
				output += "<tr><td>" + userName + "</td>";
				output += "<td>" + password + "</td>";
				output += "<td>" + email + "</td>";
				output += "<td>" + type + "</td>";
				// buttons
				output += "<td><input name='btnUpdate' type='button' value='Update' class='btn btn-secondary'></td>"
						+ "<td><form method='post' action='items.jsp'>"
						+ "<input name='btnRemove' type='submit' value='Remove' class='btn btn-danger'>"
						+ "<input name='UserID' type='hidden' value='" + UserID + "'>" + "</form></td></tr>";
			}
			con.close();
			// Complete the html table
			output += "</table>";
		} catch (Exception e) {
			output = "Error while reading the items.";
			System.err.println(e.getMessage());
		}
		return output;
	}
	public String insertUsers(String name, String pwd ,String email,String type) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for inserting.";
			}
			String query1 ="select UserId from user where userName =? and password=?";
			PreparedStatement preparedStmt1 = con.prepareStatement(query1);
			preparedStmt1.setString(1, name);
			preparedStmt1.setString(2,pwd);
			
			ResultSet rs =   preparedStmt1.executeQuery();

			if(rs.next()) {
				con.close();
				
				return "username or password is already taken.Use different one";
				
			}
			else  {
				
				// create a prepared statement
				String query = " insert into user(`UserId`,`userName`,`password`,`email`,`type`)"
						+ " values (?, ?, ?,?,?)";
				PreparedStatement preparedStmt = con.prepareStatement(query);
				// binding values
				preparedStmt.setInt(1, 0);
				preparedStmt.setString(2, name);
				preparedStmt.setString(3, pwd);
				preparedStmt.setString(4, email);
				preparedStmt.setString(5, type);
				// execute the statement
				preparedStmt.execute();
				con.close();
				output = "Inserted successfully";
			}
			
			
			
		} catch (Exception e) {
			output = "Error while inserting the item.";
			System.err.println(e.getMessage());
		}
		return output;
	}
	
	public String updateUser(String userID,String name, String pwd,String email,String type) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for updating.";
			}
			// create a prepared statement
			String query = "UPDATE user SET userName=?,password=? ,email=?,type=? WHERE UserId=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setString(1, name);
			preparedStmt.setString(2, pwd);
			preparedStmt.setString(3, email);
			preparedStmt.setString(4, type);
			preparedStmt.setInt(5, Integer.parseInt(userID));
			// execute the statement
			preparedStmt.execute();
			con.close();
			output = "Updated successfully";
		} catch (Exception e) {
			output = "Error while updating the item.";
			System.err.println(e.getMessage());
		}
		return output;
	}
	public String deleteUser(String userID) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for deleting.";
			}
			// create a prepared statement
			String query = "delete from user where UserId=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, Integer.parseInt(userID));
			// execute the statement
			preparedStmt.execute();
			con.close();
			output = "Deleted successfully";
		} catch (Exception e) {
			output = "Error while deleting the item.";
			System.err.println(e.getMessage());
		}
		return output;
	}public String checkUsers(String userName, String password) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for inserting.";
			}
			// create a prepared statement
			String query = " select `userName`,`password` from user where userName=? and password=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
			
			// binding values
			preparedStmt.setString(1, userName);
			preparedStmt.setString(2,password);
			//preparedStmt.setString(2, Base64.getEncoder().encodeToString(password.getBytes()));
			
			System.out.println(preparedStmt);
			System.out.println(userName);
			System.out.println(Base64.getEncoder().encodeToString(password.getBytes()));
			
			ResultSet rs =   preparedStmt.executeQuery();

			if(rs.next()) {
				con.close();
				if(userName.equals("admin")) {
					return "You are logged in";
				}
				return "welcome "+userName+ "!! to the GadgetBadget website";
				
			}
			else  {
				con.close();
				if(userName.equals("")) {
					return "username cannot be empty";
				}
				else if(password.equals("")){
					return "password cannot be empty";
				}
				else {
					return "username or password ";
				}
			}
			
			
		} catch (Exception e) {
			output = "Error while";
			System.err.println(e.getMessage());
		}
		return output;
	}

}
