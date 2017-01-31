package NinetyMin.resources;

import NinetyMin.api.match.MatchApi;
import NinetyMin.core.FootBallMatch.FootBallMatch;
import NinetyMin.core.FootBallMatch.MatchStatus;
import NinetyMin.core.managed.CachaingService;

import javax.ws.rs.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.sun.xml.internal.ws.policy.sourcemodel.wspolicy.XmlToken.Optional;

/**
 * Created by orlavy on 1/27/17.
 */
@Path("/{teamId : [0-9]*}")
public class TeamDataResource {

    @GET
    @Produces("application/json")
    public List<MatchApi> getTeamData(@PathParam("teamId") int teamId, @QueryParam("match_status") String status){
        List<FootBallMatch> matches = CachaingService.INSTANCE().getMatchCacher().
                getMatchesForTeam(teamId, MatchStatus.optionalFromString(status));
        return matches.stream().map(MatchApi::fromFootBallMatch).collect(Collectors.toList());
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
