package NinetyMin;

import NinetyMin.core.managed.ScrappingService;
import NinetyMin.resources.TeamDataResource;
import NinetyMin.resources.TeamIdentifiersResource;
import NinetyMin.resources.TournamentDataResource;
import io.dropwizard.Application;
import io.dropwizard.lifecycle.Managed;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class OrLavyApplication extends Application<OrLavyConfiguration> {

    public static void main(final String[] args) throws Exception {
        new OrLavyApplication().run(args);
    }

    @Override
    public String getName() {
        return "OrLavy";
    }

    @Override
    public void initialize(final Bootstrap<OrLavyConfiguration> bootstrap) {
        // TODO: application initialization
    }

    @Override
    public void run(final OrLavyConfiguration configuration,
                    final Environment environment) {

        environment.lifecycle().manage(new ScrappingService(configuration.getUrlScrappingConfigurations()));

        // TODO: implement application
        environment.jersey().register(new TeamDataResource());
        environment.jersey().register(new TournamentDataResource());
        environment.jersey().register(new TeamIdentifiersResource());
    }

}
