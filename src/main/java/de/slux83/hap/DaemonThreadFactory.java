package de.slux83.hap;

import org.springframework.scheduling.concurrent.CustomizableThreadFactory;

public class DaemonThreadFactory extends CustomizableThreadFactory {
    public DaemonThreadFactory(String threadNamePrefix) {
        super(threadNamePrefix);
        setDaemon(true);
    }
}
