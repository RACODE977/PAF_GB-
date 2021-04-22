package com;
import model.ReserchProduct;
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



public class ReserchProductService {
	
	ReserchProduct ReserchObj = new ReserchProduct(); 
	@GET
	@Path("/") 
	@Produces(MediaType.TEXT_HTML) 
	public String readItems() 
	 { 
	     return  ReserchObj.readItems(); 
	 }
	
	@POST
	@Path("/") 
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED) 
	@Produces(MediaType.TEXT_PLAIN) 
	public String insertItem(@FormParam("itemCode") String itemCode, 
	 @FormParam("itemName") String itemName, 
	 @FormParam("itemPrice") String itemPrice, 
	 @FormParam("itemDesc") String itemDesc) 
	{ 
	    String output = ReserchObj.insertItem(itemCode, itemName, itemPrice, itemDesc); 
	 
	    return output; 
	}
	
	@PUT
	@Path("/") 
	@Consumes(MediaType.APPLICATION_JSON) 
	@Produces(MediaType.TEXT_PLAIN) 
	public String updateItem(String itemData) 
	{ 
	//Convert the input string to a JSON object 
	 JsonObject payobj = new JsonParser().parse(itemData).getAsJsonObject(); 
	 
	//Read the values from the JSON object
	 
	 	String itemID = payobj.get("itemID").getAsString(); 
	 	String itemCode = payobj.get("itemCode").getAsString(); 
	 	String itemName = payobj.get("itemName").getAsString(); 
	 	String itemPrice = payobj.get("itemPrice").getAsString(); 
	 	String itemDesc = payobj.get("itemDesc").getAsString();
	 
	 	String output = ReserchObj.updateItem(itemID, itemCode, itemName, itemPrice, itemDesc); 
	 
	   return output; 
	}
	
	@DELETE
	@Path("/") 
	@Consumes(MediaType.APPLICATION_XML) 
	@Produces(MediaType.TEXT_PLAIN) 
	public String deleteItem(String itemData) 
	{ 
	//Convert the input string to an XML document
	 Document doc = Jsoup.parse(itemData, "", Parser.xmlParser()); 
	 
	//Read the value from the element <itemID>
	 String itemID = doc.select("itemID").text(); 
	 String output = ReserchObj.deleteItem(itemID); 
	return output; 
	}
	

}
