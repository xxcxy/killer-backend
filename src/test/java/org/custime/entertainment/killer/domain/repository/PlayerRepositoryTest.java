package org.custime.entertainment.killer.domain.repository;

import org.custime.entertainment.killer.domain.model.Player;
import org.custime.entertainment.killer.domain.model.Utils;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class PlayerRepositoryTest {

    @Test
    public void testSave() {
        Player player = Utils.getPlayer();
        String playerId = "01";
        assertThat(PlayerRepository.getInstance().save(playerId, player), is(true));
    }

    @Test
    public void testGetOneAfterSave() {
        Player player = Utils.getPlayer();
        String playerId = "02";
        PlayerRepository.getInstance().save(playerId, player);
        assertThat(PlayerRepository.getInstance().getOne(playerId).isPresent(), is(true));
    }

    @Test
    public void testGetOneBeforeSave() {
        String playerId = "03";
        assertThat(PlayerRepository.getInstance().getOne(playerId).isPresent(), is(false));
    }

    @Test
    public void testRemoveAfterSave() {
        Player player = Utils.getPlayer();
        String playerId = "04";
        PlayerRepository.getInstance().save(playerId, player);
        assertThat(PlayerRepository.getInstance().remove(playerId), is(true));
        assertThat(PlayerRepository.getInstance().getOne(playerId).isPresent(), is(false));
    }

    @Test
    public void testRemoveBeforeSave() {
        String playerId = "05";
        assertThat(PlayerRepository.getInstance().remove(playerId), is(false));
    }
}
