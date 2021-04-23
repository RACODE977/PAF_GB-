package com;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import model.user;

@Path("/user")
public class UserAuthService {
	user userObj = new user();
	 
	 //****read users
	 @GET
	 @Path("/") 
	 @Produces(MediaType.TEXT_HTML) 
	 public String readItems() 
	  { 
	  return userObj.readUsers(); 
	  }
	 
	 //****insert users
	 @POST
	 @Path("/") 
	 @Consumes(MediaType.APPLICATION_FORM_URLENCODED) 
	 @Produces(MediaType.TEXT_PLAIN) 
	 public String insertItem(
	  @FormParam("userName") String userName, 
	  @FormParam("password") String password,
	  @FormParam("email") String email,
	  @FormParam("type") String type)

	 { 
	  String output = userObj.insertUsers(userName, password,email,type); 
	 return output; 
	 }
	 
	//****update users

	 @PUT
	 @Path("/") 
	 @Consumes(MediaType.APPLICATION_JSON) 
	 @Produces(MediaType.TEXT_PLAIN) 
	 public String updateItem(String userData) 
	 { 
	 //Convert the input string to a JSON object 
	  JsonObject userObject = new JsonParser().parse(userData).getAsJsonObject(); 
	 //Read the values from the JSON object
	  String userID = userObject.get("UserId").getAsString(); 
	  String userName = userObject.get("userName").getAsString(); 
	  String password = userObject.get("password").getAsString(); 
	  String email = userObject.get("email").getAsString();
	  String type = userObject.get("type").getAsString();
	  String output = userObj.updateUser(userID, userName, password,email,type) ;
	 return output; 
	 }
	 
	//****delete users

	 @DELETE
	 @Path("/") 
	 @Consumes(MediaType.APPLICATION_XML) 
	 @Produces(MediaType.TEXT_PLAIN) 
	 public String deleteItem(String userData) 
	 { 
	 //Convert the input string to an XML document
	  Document doc = Jsoup.parse(userData, "", Parser.xmlParser()); 
	  
	 //Read the value from the element <itemID>
	  String itemID = doc.select("UserId").text(); 
	  String output = userObj.deleteUser(itemID); 
	 return output; 
	 }
//**login user
	 @POST
	 @Path("/check") 
	 @Consumes(MediaType.APPLICATION_FORM_URLENCODED) 
	 @Produces(MediaType.TEXT_PLAIN) 
	 public String loginCheck(
	  @FormParam("userName") String userName, 
	  @FormParam("password") String password)

	 { 
	  String output = userObj.checkUsers(userName, password); 
	 return output; 
	 }
}
