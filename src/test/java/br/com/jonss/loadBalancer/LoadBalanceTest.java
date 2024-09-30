package br.com.jonss.loadBalancer;

import br.com.jonss.exceptions.ServerAlreadyExists;
import br.com.jonss.exceptions.ServerLimitException;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Random;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;


class LoadBalanceTest {

    private final Random randomMock = Mockito.mock(Random.class);
    private final LoadBalance loadBalancer = new LoadBalance(randomMock);;

    @Test
    public void addInstanceToLoadBalancer() {
        loadBalancer.register("http://server1.com");

        var length = loadBalancer.getServerLength();

        assertEquals(1, length);
    }

    @Test
    public void instanceShouldBeUnique() {
        loadBalancer.register("http://serverx.com");
        assertThatThrownBy(() -> loadBalancer.register("http://serverx.com"))
                .isInstanceOf(ServerAlreadyExists.class);
    }

    @Test
    public void shouldHaveOnlyTenServers() {
        IntStream.range(0, 10).forEach(i -> loadBalancer.register("http://server" + i + ".com"));

        assertThatThrownBy(() -> loadBalancer.register("http://server" + 11 + ".com"))
                .isInstanceOf(ServerLimitException.class);
    }

    @Test
    public void shouldGetServerRandomly() {
        IntStream.range(0, 5).forEach(i -> loadBalancer.register("http://server" + i + ".com"));

        Mockito.when(randomMock.nextInt(Mockito.anyInt())).thenReturn(2);

        var server = loadBalancer.get();

        assertEquals("http://server2.com", server);
    }

}