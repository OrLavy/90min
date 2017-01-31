package NinetyMin.resources;

import NinetyMin.api.match.MatchApi;
import NinetyMin.core.FootBallMatch.FootBallMatch;
import NinetyMin.core.FootBallMatch.FootBallTournament;
import NinetyMin.core.FootBallMatch.MatchStatus;
import NinetyMin.core.managed.CachaingService;
import org.apache.commons.lang3.EnumUtils;

import javax.ws.rs.*;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by orlavy on 1/27/17.
 */
@Path("/{tournamentName : [a-zA-Z_]*}")
public class TournamentDataResource {
    @GET
    @Produces("application/json")
    public List<MatchApi> getTeamData(@PathParam("tournamentName") String tournamentName,
                                      @QueryParam("match_status") String status){

        if (!EnumUtils.isValidEnum(FootBallTournament.class, tournamentName.toUpperCase())){
            return Collections.EMPTY_LIST;
        } else {
            FootBallTournament tournament = FootBallTournament.valueOf(tournamentName.toUpperCase());

            List<FootBallMatch> matches = CachaingService.INSTANCE().getMatchCacher().
                    getMatchesForTournament(tournament, MatchStatus.optionalFromString(status));

            return matches.stream().map(MatchApi::fromFootBallMatch).collect(Collectors.toList());
        }

    }
}
