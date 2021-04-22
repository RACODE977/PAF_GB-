package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Research {
	public Connection connect()
	{ 
		 Connection con = null; 
		 
		 try { 
			 Class.forName("com.mysql.jdbc.Driver"); 
			 con= DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/researches", "root", ""); 
			 //For testing
			 System.out.print("Successfully connected"); 
		} catch(Exception e){ 
			 e.printStackTrace(); 
		} 
		return con; 
	}
	
	public String insertResearch(String name, String author, String desc) {
		String output = ""; 
		try { 
			Connection con = connect(); 
			if (con == null) 
			{ 
				return "Error while connecting to the database"; 
			} 
			// create a prepared statement
			String query = "insert into researches(`name`,`author`,`description`) values (?,?,?)"; 
			PreparedStatement preparedStmt = con.prepareStatement(query); 
			 
			preparedStmt.setString(1, name);
			preparedStmt.setString(2, author);
			preparedStmt.setString(3, desc);
	
			//execute the statement
			 preparedStmt.execute(); 
			 con.close(); 
			 output = "Inserted successfully";
		}catch (Exception e) { 
			 output = "Error while inserting"; 
			 System.err.println(e.getMessage()); 
		} 
		return output;
	}
	
	public String readResearches() {
		String output = "";
		try {
			Connection con = connect();
			if(con == null) {
				return "Error while connecting to the database";
			}
			
			output = "<table border='1'>"
					+ "<tr>"
					+ "<th>Research Name</th>"
					+ "<th>Author</th>"
					+ "<th>Description</th>"
					+ "<th>Update</th>"
					+ "<th>Delete</th>"
					+ "</tr>";
			
			String query = "select * from researches";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			// iterate through the rows in the result set
			while(rs.next()) {
				String id = Integer.toString(rs.getInt("id"));
				String name = rs.getString("name");
				String author = rs.getString("author");
				String desc = rs.getString("description");

				//add a row into html table
				output += "<tr>";
				output += "<td>" + name  + "</td>";
				output += "<td>" + author  + "</td>";
				output += "<td>" + desc  + "</td>";
	
				//buttons
				 output += "<td><form method='post' action='#'>" 
						 + "<input name='btnUpdate' type='submit' value='Update'>"
						 + "<input name='id' type='hidden' value='" + id + "'>" 
						 + "</form></td>"
						 + "<td><form method='post' action='#'>" 
						 + "<input name='btnRemove' type='submit' value='Remove'>"
						 + "<input name='id' type='hidden' value='" + id + "'>" 
						 + "</form></td></tr>";
				
			}
			
			con.close();
			output += "</table>";
			
		}catch(Exception e) {
			output = "Error while reading";
			System.out.println(e.getMessage());
		}
		return output;
	}
	
	public String updateResearch(int id, String name, String author, String desc) {
		String output = ""; 
		try { 
			Connection con = connect(); 
			if (con == null) 
			{ 
				return "Error while connecting to the database"; 
			} 
			// create a prepared statement
			String query = "update researches set name = ?,author = ?,description = ? where id = ?"; 
			PreparedStatement preparedStmt = con.prepareStatement(query); 
			 // binding values 
			 preparedStmt.setString(1, name); 
			 preparedStmt.setString(2, author); 
			 preparedStmt.setString(3, desc); 
		     preparedStmt.setInt(4, id);
			 
			//execute the statement
			 preparedStmt.execute(); 
			 con.close(); 
			 output = "Updated successfully";
		}catch (Exception e) { 
			 output = "Error while updating"; 
			 System.err.println(e.getMessage()); 
		} 
		return output;
	}
	
	public String deleteResearch(int id)
	{ 
		 String output = ""; 
		 try
		 { 
			 Connection con = connect(); 
			 if (con == null) 
			 { 
				 return "Error while connecting to the database for deleting."; 
			 } 
			 // create a prepared statement
			 String query = "delete from researches where id=?"; 
			 PreparedStatement preparedStmt = con.prepareStatement(query); 
			 
			 // binding values
			 preparedStmt.setInt(1, id); 
			 
			 // execute the statement
			 preparedStmt.execute(); 
			 con.close(); 
			 output = "Deleted successfully"; 
		} 
		catch (Exception e) 
		{ 
			output = "Error while deleting the item."; 
			System.err.println(e.getMessage()); 
		} 
		return output;
	}
	

}
