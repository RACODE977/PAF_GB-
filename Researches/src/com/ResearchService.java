package com;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import model.Research;

@Path("/Researches")
public class ResearchService {
	Research research = new Research();
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String insertResearch(String researchData) 
	{
		
		
		//Convert the input string to a JSON object 
		 JsonObject researchObj = new JsonParser().parse(researchData).getAsJsonObject(); 
		   
		//Read the values from the JSON object
		 String name = researchObj.get("name").getAsString();
		 String author = researchObj.get("author").getAsString();
		 String desc = researchObj.get("description").getAsString();

		 String output = research.insertResearch(name, author, desc);
		 return output;
	}
	

	@GET
	@Path("/") 
	@Produces(MediaType.TEXT_HTML) 
	public String readResearche(){ 
		return research.readResearches(); 
	 } 
	
	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String updateResearch(String researchData) {
					
		//Convert the input string to a JSON object 
		 JsonObject researchObj = new JsonParser().parse(researchData).getAsJsonObject(); 
		 
		//Read the values from the JSON object
		 int id = researchObj.get("id").getAsInt();
		 String name = researchObj.get("name").getAsString();
		 String author = researchObj.get("author").getAsString();
		 String desc = researchObj.get("desc").getAsString();
		 String output = research.updateResearch(id, name, author, desc); 
		 return output;
	}
	
	@DELETE
	@Path("/") 
	@Consumes(MediaType.APPLICATION_JSON) 
	@Produces(MediaType.TEXT_PLAIN) 
	public String deleteResearch(String researchData) {
				
		//Convert the input string to a JSON object 
		 JsonObject researchObj = new JsonParser().parse(researchData).getAsJsonObject(); 
		 
		//Read the values from the JSON object
		 int id = researchObj.get("id").getAsInt();

		 String output = research.deleteResearch(id); 
		 return output;
	}

}
