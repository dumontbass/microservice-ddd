package br.org.base.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("bar")
public class BarResource {
    
    @GET
    @Produces("application/json")
    public String response(){
        
        return "{nome : \"asdas\"}";
        
    }
}
