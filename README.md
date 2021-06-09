# hosts-agent-patrol
Small Sprint Boot application that checks a set of configured hosts assigned to people and alerts a Slack bot via HTTP Post when the person is running more than one assigned VM. This is necessary to control Virtual Machines resources in my company

## Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.5.0/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/2.5.0/maven-plugin/reference/html/#build-image)

## Configuration
The file src/main/resources/application_example.yml can be used as starting point for configuring your users and hosts. The file has to be passed to the running spring boot instance `--spring.config.location=PATH`.

An example of the configuration file, is reported below:
```
spring:
  application:
    name: host-agent-patrol

slack-secret: https://hooks.slack.com/services/XXXXXXXX/YYYYYYYYYYY/ZZZZZZZZZZZZZ
polling-period-secs: 300

users:
  - name: Alice
    slack-user-id: ABCDEF00
    max-hosts-running: 1
    hosts:
      - 192.168.1.124
      - 192.168.1.210
      - host-name-1
  - name: Bob
    slack-user-id: XFZML123
    max-hosts-running: 1
    hosts:
      - 192.168.1.52
      - host-name-2
      - 192.168.1.1
```
