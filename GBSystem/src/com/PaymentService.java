package com;
import model.payment; 

//For REST Service
import javax.ws.rs.*; 
import javax.ws.rs.core.MediaType; 
//For JSON
import com.google.gson.*; 
//For XML
import org.jsoup.*; 
import org.jsoup.parser.*; 
import org.jsoup.nodes.Document;

@Path("/pay") 
public class PaymentService {
	payment paymentObj = new payment(); 
	@GET
	@Path("/") 
	@Produces(MediaType.TEXT_HTML) 
	public String readItems() 
	 { 
	 return  paymentObj.readItems(); 
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
	    String output = paymentObj.insertItem(itemCode, itemName, itemPrice, itemDesc); 
	 
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
	 
	 	String output = paymentObj.updateItem(itemID, itemCode, itemName, itemPrice, itemDesc); 
	 
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
	 String output = paymentObj.deleteItem(itemID); 
	return output; 
	}

}
