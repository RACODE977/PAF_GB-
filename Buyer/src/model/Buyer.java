package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Buyer {
	private Connection connect()
	 {
		 Connection con = null;
		 try
		 {
			 Class.forName("com.mysql.cj.jdbc.Driver");
		
		 	
		 	con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/gadgetbadget", "root", "");
		 }
		 catch (Exception e)
		 {	e.printStackTrace();}
		 return con;
	 }

	public String insertItem(String userID, String productID, String quantity)
	{
	String output = "";
	try
	{
	Connection con = connect();
	if (con == null)
	{return "Error while connecting to the database for inserting."; }
	// create a prepared statement
	String query = " insert into buyer(purchaseID,userID,productID,quantity)"
	+ " values (?, ?, ?, ?)";
	PreparedStatement preparedStmt = con.prepareStatement(query);
	// binding values
	preparedStmt.setInt(1, 0);
	preparedStmt.setString(2, userID);
	preparedStmt.setString(3, productID);
	preparedStmt.setString(4, quantity);
	
	//execute the statement

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

	public String readItems()
	{
	String output = "";
	try
	{
	Connection con = connect();
	if (con == null)
	{return "Error while connecting to the database for reading."; }
	// Prepare the html table to be displayed
	output = "<table border='1'><tr><th>purchase ID</th><th>user ID</th>" +
	"<th>product ID</th>" +
	"<th>quantity</th>" +
	"<th>Update</th><th>Remove</th></tr>";

	String query = "select * from buyer";
	Statement stmt = con.createStatement();
	ResultSet rs = stmt.executeQuery(query);
	// iterate through the rows in the result set
	while (rs.next())
	{
	String purchaseID = Integer.toString(rs.getInt("purchaseID"));
	String userID = rs.getString("userID");
	String productID = rs.getString("productID");
	String quantity = rs.getString("quantity");
	
	// Add into the html table
	output += "<tr><td>" + purchaseID + "</td>";
	output += "<td>" + userID + "</td>";
	output += "<td>" + productID + "</td>";
	output += "<td>" + quantity + "</td>";
	// buttons
	output += "<td><input name='btnUpdate' type='button' value='Update' class='btn btn-secondary'></td>"
	+ "<td><form method='post' action='buyer.jsp'>"
	+ "<input name='btnRemove' type='submit' value='Remove'class='btn btn-danger'>"
	+ "<input name='itemID' type='hidden' value='" + purchaseID
	+ "'>" + "</form></td></tr>";
	}
	con.close();
	// Complete the html table
	output += "</table>";
	}
	catch (Exception e)
	{
	output = "Error while reading the record.";
	System.err.println(e.getMessage());
	}
	return output;
	}
	
	public String getItems(String userid)
	{
	String output = "";
	try
	{
	Connection con = connect();
	if (con == null)
	{return "Error while connecting to the database for reading."; }
	// Prepare the html table to be displayed
	output = "<table border='1'><tr><th>purchase ID</th><th>user ID</th>" +
	"<th>product ID</th>" +
	"<th>quantity</th>" +
	"<th>Update</th><th>Remove</th></tr>";

	String query = "select * from buyer where userID='"+userid+"'";
	Statement stmt = con.createStatement();
	ResultSet rs = stmt.executeQuery(query);
	// iterate through the rows in the result set
	while (rs.next())
	{
	String purchaseID = Integer.toString(rs.getInt("purchaseID"));
	String userID = rs.getString("userID");
	String productID = rs.getString("productID");
	String quantity = rs.getString("quantity");
	
	// Add into the html table
	output += "<tr><td>" + purchaseID + "</td>";
	output += "<td>" + userID + "</td>";
	output += "<td>" + productID + "</td>";
	output += "<td>" + quantity + "</td>";
	// buttons
	output += "<td><input name='btnUpdate' type='button' value='Update' class='btn btn-secondary'></td>"
	+ "<td><form method='post' action='buyer.jsp'>"
	+ "<input name='btnRemove' type='submit' value='Remove'class='btn btn-danger'>"
	+ "<input name='itemID' type='hidden' value='" + purchaseID
	+ "'>" + "</form></td></tr>";
	}
	con.close();
	// Complete the html table
	output += "</table>";
	}
	catch (Exception e)
	{
	output = "Error while reading the record.";
	System.err.println(e.getMessage());
	}
	return output;
	}


	public String updateItem(String purchaseID,String userID, String productID, String quantity)

	{
	String output = "";
	try
	{
	Connection con = connect();
	if (con == null)
	{return "Error while connecting to the database for updating."; }
	// create a prepared statement
	String query = "UPDATE buyer SET userID=?,productID=?,quantity=? WHERE purchaseID=?";
	PreparedStatement preparedStmt = con.prepareStatement(query);
	// binding values
	
	preparedStmt.setString(1, userID);
	preparedStmt.setString(2, productID);
	preparedStmt.setString(3, quantity);
	preparedStmt.setInt(4, Integer.parseInt(purchaseID));
	// execute the statement
	preparedStmt.execute();
	con.close();
	output = "Updated successfully";
	}
	catch (Exception e)
	{
	output = "Error while updating the record.";
	System.err.println(e.getMessage());
	}
	return output;
	}

	public String deleteItem(String purchaseID)
	{
	String output = "";
	try
	{
	Connection con = connect();
	if (con == null)
	{return "Error while connecting to the database for deleting."; }
	// create a prepared statement
	String query = "delete from buyer where purchaseID=?";
	PreparedStatement preparedStmt = con.prepareStatement(query);
	// binding values
	preparedStmt.setInt(1, Integer.parseInt(purchaseID));
	// execute the statement
	preparedStmt.execute();
	con.close();
	output = "Deleted successfully";
	}
	catch (Exception e)
	{
	output = "Error while deleting the record.";
	System.err.println(e.getMessage());
	}
	return output;
	}


}
