package NinetyMin.resources;

import NinetyMin.api.match.MatchApi;
import NinetyMin.core.FootBallMatch.FootBallMatch;
import NinetyMin.core.FootBallMatch.FootBallTournament;
import NinetyMin.core.managed.CachaingService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by orlavy on 1/27/17.
 */
@Path("/{tournamentName : [a-zA-Z]*}")
public class TournamentDataResource {
    @GET
    @Produces("application/json")
    public List<MatchApi> getTeamData(@PathParam("tournamentName") String tournamentName){
        FootBallTournament tournament = FootBallTournament.valueOf(tournamentName);

        List<FootBallMatch> matches = CachaingService.INSTANCE().getMatchCacher().
                getMatchesForTournament(tournament, java.util.Optional.empty());

        return matches.stream().map(MatchApi::fromFootBallMatch).collect(Collectors.toList());
    }
}
