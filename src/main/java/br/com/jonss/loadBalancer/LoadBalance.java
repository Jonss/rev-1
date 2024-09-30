package br.com.jonss.loadBalancer;

import br.com.jonss.exceptions.ServerAlreadyExists;
import br.com.jonss.exceptions.ServerLimitException;

import javax.naming.LimitExceededException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class LoadBalance {
    private static final int LIMIT = 10;
    private final Random random;
    private final List<String> servers = new CopyOnWriteArrayList<>();

    public LoadBalance(Random random) {
        this.random = random;
    }

    public void register(String url) {
        if (servers.contains(url)) {
            throw new ServerAlreadyExists();
        }

        if (servers.size() == LIMIT) {
            throw new ServerLimitException();
        }
        servers.add(url);
    }

    public int getServerLength() {
        return servers.size();
    }

    public String get() {
        return servers.get(random.nextInt(servers.size()));
    }
}
