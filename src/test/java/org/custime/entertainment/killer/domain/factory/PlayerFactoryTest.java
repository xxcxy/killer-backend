package org.custime.entertainment.killer.domain.factory;

import org.custime.entertainment.killer.domain.model.Player;
import org.custime.entertainment.killer.domain.service.PlayerService;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

public class PlayerFactoryTest {

    @Test
    public void testCreatePlayer() {
        Player player = new PlayerFactory().createPlayer(mock(PlayerService.class));
        assertThat(player, notNullValue());
    }

    @Test(expected = NullPointerException.class)
    public void testCreatePlayerWhenPlayerServiceNull() {
        new PlayerFactory().createPlayer(null);
    }
}
