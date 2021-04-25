package model;
import java.sql.*;
public class ReserchProduct {
	private Connection connect() 
	 { 
	 Connection con = null; 
	 try
	 { 
	     Class.forName("com.mysql.cj.jdbc.Driver"); 
	 
	 //Provide the correct details: DBServer/DBName, username, password 
	     con = DriverManager.getConnection("jdbc:mysql://localhost:8080/reserchproduct", "root", ""); 
	 } 
	      catch (Exception e) 
	 {
		 
		 e.printStackTrace();
	 
	 } 
	     return con;
	     
	 } 
	
	//*************inserting******************
	public String insertProduct(String productCode, String productName, String productPrice, String productDesc) 
	 { 
	       String output = ""; 
	 try
	 { 
	      Connection con = connect(); 
	      if (con == null) 
	 {
		 
		 return "Error while connecting to the database for inserting.";
		 
	 } 
	 // create a prepared statement
	      
	 String query = " insert into producttable(`rid`,`productCode`,`productName`,`productPrice`,`productDesc`)"+ " values (?, ?, ?, ?, ?)"; 
	 
	 PreparedStatement preparedStmt = con.prepareStatement(query); 
	 // binding values
	     preparedStmt.setInt(1, 0); 
	     preparedStmt.setString(2, productCode); 
	     preparedStmt.setString(3,productName); 
	     preparedStmt.setDouble(4, Double.parseDouble(productPrice)); 
	     preparedStmt.setString(5,productDesc); 
	// execute the statement3
	     preparedStmt.execute(); 
	     con.close();
	     
	    output = "Inserted successfully"; 
	 } 
	 catch (Exception e) 
	 
	 { 
	     output = "Error while inserting the item."; 
	     
	     System.err.println(e.getMessage()); 
	 } 
	 
	      return output; 
	 } 
	//********************read products***********************
	public String readProducts() 
	 { 
		String output = ""; 
	 try
	 { 
	    Connection con = connect(); 
	    if (con == null) 
	 {
	    	return "Error while connecting to the database for reading."; 
	    	
	 }
	 // Prepare the html table to be displayed
	    
	 output = "<table border='1'> <tr><th>Product Code</th><th>Product Name</th>" +
	 "<th>Product Price</th>" + 
	 "<th>Product Description</th>" +
	 "<th>Update</th><th>Remove</th></tr>"; 
	 
	 String query = "select * from producttable"; 
	 Statement stmt = con.createStatement(); 
	 ResultSet rs = stmt.executeQuery(query); 
	 
	 // iterate through the rows in the result set
	 
	 while (rs.next()) 
	 { 
		 String rid= Integer.toString(rs.getInt("rid")); 
		 String productCode = rs.getString("productCode"); 
		 String productName = rs.getString("productName"); 
		 String productPrice = Double.toString(rs.getDouble("productPrice")); 
		 String productDesc = rs.getString("productDesc"); 
	 // Add into the html table
		 output += "<tr><td>" + productCode + "</td>"; 
		 output += "<td>" + productName + "</td>"; 
		 output += "<td>" +productPrice + "</td>"; 
		 output += "<td>" + productDesc + "</td>"; 
	 // buttons
	 output += "<td><input name='btnUpdate' type='button' value='Update' class='btn btn-secondary'></td>"
	 + "<td><form method='post' action='products.jsp'>"
	 + "<input name='btnRemove' type='submit' value='Remove' class='btn btn-danger'>"
	 + "<input name='rid' type='hidden' value='" + rid  + "'>" + "</form></td></tr>"; 
	 } 
	 	con.close(); 
	 // Complete the html table
	 	output += "</table>"; 
	 } 
	 catch (Exception e) 
	 { 
		 
		 output = "Error while reading the products."; 
	 	System.err.println(e.getMessage()); 
	 	
	 } 
	 	return output; 
	 } 

	
	//***************update******************
	public String updateProducts(String rid, String productCode, String productName, String productPrice, String productDesc)
	 { 
	 String output = ""; 
	 try
	 { 
	 Connection con = connect(); 
	 if (con == null) 
	 {return "Error while connecting to the database for updating."; } 
	 // create a prepared statement
	 String query = "UPDATE producttable SET productCode=?,productName=?,productPrice=?,productDesc=? WHERE rid=?"; 
	 PreparedStatement preparedStmt = con.prepareStatement(query); 
	 // binding values
	 preparedStmt.setString(1, productCode); 
	 preparedStmt.setString(2, productName); 
	 preparedStmt.setDouble(3, Double.parseDouble(productPrice)); 
	 preparedStmt.setString(4, productDesc); 
	 preparedStmt.setInt(5, Integer.parseInt(rid)); 
	 // execute the statement
	 preparedStmt.execute(); 
	 con.close(); 
	     output = "Updated successfully"; 
	 } 
	 catch (Exception e) 
	 { 
	      output = "Error while updating the item."; 
	      System.err.println(e.getMessage()); 
	 } 
	 return output; 
	 } 
	//*****Delete Product**************
	public String deleteProduct(String rid) 
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
	 	String query = "delete from producttable where rid=?"; 
	 	PreparedStatement preparedStmt = con.prepareStatement(query); 
	 // binding values
	 	preparedStmt.setInt(1, Integer.parseInt(rid)); 
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
