package NinetyMin.core.caching;

import NinetyMin.api.team.TeamIdentifierApi;
import NinetyMin.core.FootBallMatch.FootBallMatch;
import NinetyMin.core.FootBallMatch.FootBallTournament;
import NinetyMin.core.FootBallMatch.MatchStatus;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by orlavy on 1/31/17.
 */
public class MatchesDataCacher implements  MatchCacher{
    private final List<FootBallMatch> matches;
    private final TeamIdManager teamIdManager;

    private final BiFunction<FootBallMatch,Optional<MatchStatus>,Boolean> _filterByStatus = (match, status) -> status.isPresent() ? match.getStatus() == status.get() : true;
    private final BiFunction<FootBallMatch,String,Boolean> _filterTeamName = (match, name) -> match.getHomeTeam().equalsIgnoreCase(name) || match.getAwayTeam().equalsIgnoreCase(name);
    private final BiFunction<FootBallMatch,FootBallTournament,Boolean> _filterByTournament = (match, tournament) -> match.getTournament() == tournament;

    public MatchesDataCacher(){
        this.matches = new ArrayList<>();
        this.teamIdManager = new TeamIdManager();
    }

    public void addMatches(Collection<FootBallMatch> newMatches) {
        this.matches.addAll(newMatches);

        for (FootBallMatch match : newMatches){
            this.matches.add(match);
            this.teamIdManager.addIfTeamDoesNotExist(match.getHomeTeam());
            this.teamIdManager.addIfTeamDoesNotExist(match.getAwayTeam());
        }
    }

    public void clearMatches(){
        this.matches.clear();
    }

    @Override
    public List<FootBallMatch> getMatchesForTeam(int teamId, Optional<MatchStatus> status) {
        String teamName = this.teamIdManager.getNameById(teamId);

        return filterByMatchStatus(filterByTeamName(matches.stream(),teamName),status).
                collect(Collectors.toList());
    }

    @Override
    public List<FootBallMatch> getMatchesForTournament(FootBallTournament footBallTournament, Optional<MatchStatus> status) {
        return filterByMatchStatus(filterByTournament(matches.stream(),footBallTournament),status).
                collect(Collectors.toList());
    }

    @Override
    public List<TeamIdentifierApi> getTeamIdentifiers() {
        return this.teamIdManager.getAllTeamIdentifiers();
    }

    private Stream<FootBallMatch> filterByMatchStatus(Stream<FootBallMatch> matches, Optional<MatchStatus> status){
        return matches.filter(m -> _filterByStatus.apply(m,status));
    }

    private Stream<FootBallMatch> filterByTeamName(Stream<FootBallMatch> matches, String teamName){
        return matches.filter(m -> _filterTeamName.apply(m,teamName));
    }

    private Stream<FootBallMatch> filterByTournament(Stream<FootBallMatch> matches, FootBallTournament tournament){
        return matches.filter(m -> _filterByTournament.apply(m,tournament));
    }

    private class TeamIdManager{
        private Map<String,Integer> nameToId;
        private Map<Integer,String> idToName;
        private AtomicInteger idCounter;

        public TeamIdManager(){
            this.resetData();
        }

        public void resetData(){
            this.nameToId = new HashMap<>();
            this.nameToId = new HashMap<>();
            this.idToName = new HashMap<>();
            this.idCounter = new AtomicInteger();  this.idToName = new HashMap<>();
            this.idCounter = new AtomicInteger();
        }

        public String getNameById(Integer id){
            return this.idToName.containsKey(id) ? this.idToName.get(id) : null;
        }

        public List<TeamIdentifierApi> getAllTeamIdentifiers() {
            List<TeamIdentifierApi> teamIdentifiers = new ArrayList<>();

            for (Map.Entry<String,Integer> entry : this.nameToId.entrySet()){
                TeamIdentifierApi newTeamIdentifierApi = new TeamIdentifierApi();
                newTeamIdentifierApi.setTeamName(entry.getKey());
                newTeamIdentifierApi.setId(entry.getValue());

                teamIdentifiers.add(newTeamIdentifierApi);
            }

            return teamIdentifiers;
        }

        public void addIfTeamDoesNotExist(String teamName){
            if (! nameToId.containsKey(teamName)){
                this.addNewTeam(teamName);
            }
        }

        private void addNewTeam(String teamName){
            Integer id = this.idCounter.getAndAdd(1);
            this.idToName.put(id,teamName);
            this.nameToId.put(teamName,id);
        }
    }
}


