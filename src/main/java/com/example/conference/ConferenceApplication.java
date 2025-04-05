package com.example.conference;

import com.example.conference.api.SessionResource;
import com.example.conference.db.SessionDAO;
import com.example.conference.health.AppHealthCheck;
import io.dropwizard.core.Application;
import io.dropwizard.core.setup.Environment;
import io.dropwizard.jdbi3.JdbiFactory;
import org.jdbi.v3.core.Jdbi;

public class ConferenceApplication extends Application<ConferenceConfiguration> {
    public static void main(String[] args) throws Exception {
        new ConferenceApplication().run(args);
    }

    @Override
    public void run(ConferenceConfiguration config, Environment env) {
        final JdbiFactory factory = new JdbiFactory();
        final Jdbi jdbi = factory.build(env, config.getDataSourceFactory(), "mysql");
        final SessionDAO sessionDAO = jdbi.onDemand(SessionDAO.class);

        env.jersey().register(new SessionResource(sessionDAO));

        env.healthChecks().register("app", new AppHealthCheck());
    }
}
