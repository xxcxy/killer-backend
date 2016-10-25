package org.custime.entertainment.killer.domain.model;

import org.junit.Test;

import static org.custime.entertainment.killer.domain.model.GameState.DAY;
import static org.custime.entertainment.killer.domain.model.GameState.KILLER_TIME;
import static org.custime.entertainment.killer.domain.model.GameState.NIGHT;
import static org.custime.entertainment.killer.domain.model.GameState.PROPHET_TIME;
import static org.custime.entertainment.killer.domain.model.GameState.STARTED;
import static org.custime.entertainment.killer.domain.model.GameState.WITCH_TIME;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class GameStateTest {

    @Test
    public void testStateNext() {
        assertThat(STARTED.next(), is(NIGHT));
        assertThat(NIGHT.next(), is(KILLER_TIME));
        assertThat(KILLER_TIME.next(), is(WITCH_TIME));
        assertThat(WITCH_TIME.next(), is(PROPHET_TIME));
        assertThat(PROPHET_TIME.next(), is(DAY));
        assertThat(DAY.next(), is(NIGHT));
    }
}
