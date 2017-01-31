package NinetyMin;

import NinetyMin.core.configurations.UrlScrappingConfigurations;
import io.dropwizard.Configuration;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.*;
import javax.validation.constraints.*;

public class OrLavyConfiguration extends Configuration {
    // TODO: implement service configuration

    @NotNull
    private UrlScrappingConfigurations urlScrappingConfigurations;

    @JsonProperty
    public UrlScrappingConfigurations getUrlScrappingConfigurations() {
        return urlScrappingConfigurations;
    }
    @JsonProperty
    public void setUrlScrappingConfigurations(UrlScrappingConfigurations urlScrappingConfigurations) {
        this.urlScrappingConfigurations = urlScrappingConfigurations;
    }
}
