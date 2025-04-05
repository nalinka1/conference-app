package com.example.conference;

import com.example.conference.health.AppHealthCheck;
import io.dropwizard.core.Application;
import io.dropwizard.core.setup.Environment;

public class ConferenceApplication extends Application<ConferenceConfiguration> {
    public static void main(String[] args) throws Exception {
        new ConferenceApplication().run(args);
    }

    @Override
    public void run(ConferenceConfiguration config, Environment env) {

        env.healthChecks().register("app", new AppHealthCheck());
    }
}
