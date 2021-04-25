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

import model.Buyer;

@Path("/Buyer")
public class BuyerService {
	Buyer buyerObj = new Buyer();
	 @GET
	 @Path("/")
	 @Produces(MediaType.TEXT_HTML)
	 public String readItems()
	  {
	  return buyerObj.readItems();
	  }
	 
	 @GET
	 @Path("/{userID}")
	 @Produces(MediaType.TEXT_HTML)
	 public String getItems(String userid)
	  {
	  return buyerObj.getItems(userid);
	  }
	 
	 @POST
	 @Path("/")
	 @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	 @Produces(MediaType.TEXT_PLAIN)
	 public String insertItem(@FormParam("userID") String userID,
		  @FormParam("productID") String productID,
		  @FormParam("quantity") String quantity)
		  
		 {
		  String output = buyerObj.insertItem(userID, productID, quantity);
		 return output;
	 }
	 @PUT
	 @Path("/")
	 @Consumes(MediaType.APPLICATION_JSON)
	 @Produces(MediaType.TEXT_PLAIN)
	 public String updateItem(String buyerData)
	 {
		 //Convert the input string to a JSON object
		  JsonObject buyerObject = new JsonParser().parse(buyerData).getAsJsonObject();
		 //Read the values from the JSON object
		  String purchaseID = buyerObject.get("purchaseID").getAsString();
		  String userID = buyerObject.get("userID").getAsString();
		  String productID = buyerObject.get("productID").getAsString();
		  String quantity = buyerObject.get("quantity").getAsString();
	
		  String output = buyerObj.updateItem(purchaseID ,userID, productID, quantity);
		 return output;
	 }
	 @DELETE
	 @Path("/")
	 @Consumes(MediaType.APPLICATION_XML)
	 @Produces(MediaType.TEXT_PLAIN)
	 public String deleteItem(String buyerData)
	 {
		 //Convert the input string to an XML document
		  Document doc = Jsoup.parse(buyerData, "", Parser.xmlParser());
		
		 //Read the value from the element <itemID>
		  String purchaseID = doc.select("purchaseID").text();
		  String output = buyerObj.deleteItem(purchaseID);
		 return output;
	 }

}
