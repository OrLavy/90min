package NinetyMin.core.managed;

import NinetyMin.core.caching.MatchCacher;
import NinetyMin.core.caching.MatchesDataCacher;
import io.dropwizard.lifecycle.Managed;


/**
 * Created by orlavy on 1/31/17.
 */
public class CachaingService implements Managed {
    private final MatchesDataCacher matchesDataCacher;
    private final static CachaingService _instance = new CachaingService();

    private CachaingService(){
        this.matchesDataCacher = new MatchesDataCacher();
    }

    public static CachaingService INSTANCE(){
        return  _instance;
    }

    public MatchesDataCacher getMatchCacher(){
        return this.matchesDataCacher;
    }

    @Override
    public void start() throws Exception {

    }

    @Override
    public void stop() throws Exception {

    }


}
