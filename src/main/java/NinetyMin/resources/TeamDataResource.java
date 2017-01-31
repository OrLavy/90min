package NinetyMin.resources;

import NinetyMin.api.match.MatchApi;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by orlavy on 1/27/17.
 */
@Path("/{teamId : [0-9]*}")
public class TeamDataResource {

    @GET
    @Produces("application/json")
    public List<MatchApi> getTeamData(@PathParam("teamId") int teamId){

        return makeList();
    }

    private List<MatchApi> makeList(){
        List<MatchApi> matchApis = new LinkedList<>();

        MatchApi matchApi1 = new MatchApi();
        matchApi1.setAwayScore(25);
        MatchApi matchApi2 = new MatchApi();
        matchApi2.setAwayScore(29);
        matchApis.add(matchApi1);
        matchApis.add(matchApi2);
        return matchApis;
    }

}
