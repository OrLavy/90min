package NinetyMin.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

/**
 * Created by orlavy on 1/27/17.
 */
@Path("/{tournamentName : [a-zA-Z]*}")
public class TournamentDataResource {
    @GET
    @Produces("text/html")
    public String getTeamData(@PathParam("tournamentName") String tournamentName){
        return "Tournament name is " + tournamentName;
    }
}
