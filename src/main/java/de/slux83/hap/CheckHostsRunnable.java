package de.slux83.hap;

import de.slux83.hap.configuration.ApplicationProps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CheckHostsRunnable implements Runnable {
    private static final int HOST_REACHABILITY_TIMEOUT = 3000;
    private static Logger LOG = LoggerFactory.getLogger(CheckHostsRunnable.class);

    private final ApplicationProps applicationProps;
    private final RestTemplate restTemplate;

    public CheckHostsRunnable(ApplicationProps applicationProps) {
        this.applicationProps = applicationProps;
        this.restTemplate = new RestTemplate();
    }

    @Override
    public void run() {
        for (ApplicationProps.User user : this.applicationProps.getUsers()) {
            LOG.info("Checking hosts usage for user {}", user.getName());
            List<String> runningHosts = new ArrayList<>();
            for (String host : user.getHosts()) {
                try {
                    if (InetAddress.getByName(host).isReachable(HOST_REACHABILITY_TIMEOUT))
                        runningHosts.add(host);
                } catch (Exception e) {
                    LOG.error("Unexpected error while checking the host {} reachability: {}", host, e.getMessage());
                }
            }

            LOG.info("The user {} is currently running {}", user.getHosts(), runningHosts);

            if (runningHosts.size() > user.getMaxHostsRunning()) {
                notifySlackBotViaSecret(user, runningHosts);
            }
        }

    }

    /**
     * Makes a HTTP POST to the slack secret to bother the person who runs too many hosts
     *
     * @param user
     * @param runningHosts
     */
    private void notifySlackBotViaSecret(ApplicationProps.User user, List<String> runningHosts) {
        try {
            Map<String, String> slackApiData = new HashMap<>();
            slackApiData.put("text", String.format("<@%s> you are currently running %d host(s) %s. You are only allowed to run %d host(s). Please fix that asap!",
                    user.getSlackUserId(),
                    runningHosts.size(),
                    runningHosts,
                    user.getMaxHostsRunning()));
            ResponseEntity<Void> slackApiResponse = this.restTemplate.postForEntity(this.applicationProps.getSlackSecret(), slackApiData, Void.class);
            if (!slackApiResponse.getStatusCode().equals(HttpStatus.OK)) {
                LOG.error("Cannot notify user {} on Slack. HTTP status code {}", user, slackApiResponse.getStatusCode());
            } else {
                LOG.info("The user {} has been notified via Slack Bot, using the data {}", user.getName(), slackApiData);
            }
        } catch (Exception e) {
            LOG.error("Unexpected error while notifying the slack bot using the address {}", this.applicationProps.getSlackSecret(), e);
        }
    }
}
