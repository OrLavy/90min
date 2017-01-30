package NinetyMin.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

/**
 * Created by orlavy on 1/27/17.
 */
@Path("/{teamId : [0-9]*}")
public class TeamDataResource {

    @GET
    @Produces("text/html")
    public String getTeamData(@PathParam("teamId") int teamId){
        return "Team Id is " + Integer.toString(teamId);
    }
}
