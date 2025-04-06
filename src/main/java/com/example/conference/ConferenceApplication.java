package com.example.conference;

import com.example.conference.api.SessionResource;
import com.example.conference.auth.ApiKeyAuthenticator;
import com.example.conference.core.User;
import com.example.conference.db.SessionDAO;
import com.example.conference.health.AppHealthCheck;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.AuthValueFactoryProvider;
import io.dropwizard.auth.basic.BasicCredentialAuthFilter;
import io.dropwizard.core.Application;
import io.dropwizard.core.setup.Environment;
import io.dropwizard.jdbi3.JdbiFactory;
import org.jdbi.v3.core.Jdbi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConferenceApplication extends Application<ConferenceConfiguration> {
    private static final Logger logger = LoggerFactory.getLogger(ConferenceApplication.class);
    public static void main(String[] args) throws Exception {
        logger.info("Starting Conference Application");
        new ConferenceApplication().run(args);
    }

    @Override
    public void run(ConferenceConfiguration config, Environment env) {
        final JdbiFactory factory = new JdbiFactory();
        final Jdbi jdbi = factory.build(env, config.getDataSourceFactory(), "mysql");
        final SessionDAO sessionDAO = jdbi.onDemand(SessionDAO.class);

        env.jersey().register(new SessionResource(sessionDAO));

        env.jersey().register(new AuthDynamicFeature(
                new BasicCredentialAuthFilter.Builder<User>()
                        .setAuthenticator(new ApiKeyAuthenticator())
                        .setRealm("API KEY AUTH")
                        .buildAuthFilter()
        ));

        env.jersey().register(new AuthValueFactoryProvider.Binder<>(User.class));

        env.healthChecks().register("app", new AppHealthCheck());

        logger.info("Application started successfully");
    }
}
