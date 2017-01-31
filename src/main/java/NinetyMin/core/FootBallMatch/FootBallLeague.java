package NinetyMin.core.FootBallMatch;

/**
 * Created by orlavy on 1/31/17.
 */
public enum FootBallLeague {
    PREMIER_LEAGUE("Premier League"), FA_CUP("FA Cup");

    public final String leagueName;

    FootBallLeague(String name){
        this.leagueName = name;
    }

    public String getLeagueName() {
        return leagueName;
    }
}
