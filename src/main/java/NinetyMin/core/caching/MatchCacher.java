package NinetyMin.core.caching;

import NinetyMin.api.team.TeamIdentifierApi;
import NinetyMin.core.FootBallMatch.FootBallTournament;
import NinetyMin.core.FootBallMatch.FootBallMatch;
import NinetyMin.core.FootBallMatch.MatchStatus;

import java.util.List;
import java.util.Optional;

/**
 * Created by orlavy on 1/31/17.
 */
public interface MatchCacher {
    public List<FootBallMatch> getMatchesForTeam(int teamId, Optional<MatchStatus> status);

    public List<FootBallMatch> getMatchesForTournament(FootBallTournament footBallTournament, Optional<MatchStatus> status);

    public List<TeamIdentifierApi> getTeamIdentifiers();
}
