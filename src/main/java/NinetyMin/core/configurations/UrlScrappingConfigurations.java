package NinetyMin.core.configurations;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

import java.net.URL;

/**
 * Created by orlavy on 1/31/17.
 */
public class UrlScrappingConfigurations {
    @NotEmpty
    private URL bbcResults;

    @NotEmpty
    private URL bbcFixtures;

    @JsonProperty
    public URL getBbcResults() {
        return bbcResults;
    }

    @JsonProperty
    public void setBbcResults(URL bbcResults) {
        this.bbcResults = bbcResults;
    }

    @JsonProperty
    public URL getBbcFixtures() {
        return bbcFixtures;
    }

    @JsonProperty
    public void setBbcFixtures(URL bbcFixtures) {
        this.bbcFixtures = bbcFixtures;
    }
}
