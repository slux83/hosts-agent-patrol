package de.slux83.hap;

import de.slux83.hap.configuration.ApplicationProps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
@SpringBootApplication
@EnableConfigurationProperties(value = ApplicationProps.class)
public class HostsAgentPatrolApplication {
    private static Logger LOG = LoggerFactory.getLogger(HostsAgentPatrolApplication.class);

    @Autowired
    private ApplicationProps applicationProps;

    private ScheduledExecutorService hostPatrolExecutor;

    public HostsAgentPatrolApplication() {
        this.hostPatrolExecutor = Executors.newSingleThreadScheduledExecutor(new DaemonThreadFactory("host-patrol-executor"));
    }

    public static void main(String[] args) {
        SpringApplication.run(HostsAgentPatrolApplication.class, args);
    }


    @PostConstruct
    public void post() throws Exception {
        LOG.info("Current configuration {} ", this.applicationProps.toString());
        this.hostPatrolExecutor.scheduleWithFixedDelay(
                new CheckHostsRunnable(this.applicationProps),
                10, applicationProps.getPollingPeriodSecs(),
                TimeUnit.SECONDS);

        LOG.info("Schedule task submitted to be executed every {} second(s)", applicationProps.getPollingPeriodSecs());
    }
}
