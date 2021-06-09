package de.slux83.hap.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties
public class ApplicationProps {
    private String slackSecret;
    private Long pollingPeriodSecs;
    private List<User> users;


    public String getSlackSecret() {
        return slackSecret;
    }

    public void setSlackSecret(String slackSecret) {
        this.slackSecret = slackSecret;
    }

    public Long getPollingPeriodSecs() {
        return pollingPeriodSecs;
    }

    public void setPollingPeriodSecs(Long pollingPeriodSecs) {
        this.pollingPeriodSecs = pollingPeriodSecs;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "ApplicationProps{" +
                "slackSecret='" + slackSecret + '\'' +
                ", pollingPeriodSecs=" + pollingPeriodSecs +
                ", users=" + users +
                '}';
    }

    public static class User {
        private String name;
        private String slackUserId;
        private Integer maxHostsRunning;
        private List<String> hosts;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSlackUserId() {
            return slackUserId;
        }

        public void setSlackUserId(String slackUserId) {
            this.slackUserId = slackUserId;
        }

        public Integer getMaxHostsRunning() {
            return maxHostsRunning;
        }

        public void setMaxHostsRunning(Integer maxHostsRunning) {
            this.maxHostsRunning = maxHostsRunning;
        }

        public List<String> getHosts() {
            return hosts;
        }

        public void setHosts(List<String> hosts) {
            this.hosts = hosts;
        }

        @Override
        public String toString() {
            return "User{" +
                    "name='" + name + '\'' +
                    ", slackUserId='" + slackUserId + '\'' +
                    ", maxHostsRunning=" + maxHostsRunning +
                    ", hosts=" + hosts +
                    '}';
        }
    }
}
