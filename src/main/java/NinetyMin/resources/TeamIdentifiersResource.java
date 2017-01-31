package NinetyMin.resources;

import NinetyMin.api.team.TeamIdentifierApi;
import NinetyMin.core.managed.CachaingService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.util.List;

/**
 * Created by orlavy on 1/31/17.
 */
@Path("/ids")
public class TeamIdentifiersResource {

    @GET
    @Produces("application/json")
    public List<TeamIdentifierApi> getTeamIdentifiers(){
        return CachaingService.INSTANCE().getMatchCacher().getTeamIdentifiers();
    }
}
