package br.org.base.resource;

import br.org.base.model.Bar;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("bar")
public class BarResource {
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Bar response(){
        
        return new Bar("value");
        
    }
}
