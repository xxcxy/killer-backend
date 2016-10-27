package org.custime.entertainment.killer.domain.model;

import org.junit.Test;

import static org.custime.entertainment.killer.domain.value.GameState.DAY;
import static org.custime.entertainment.killer.domain.value.GameState.KILLER_TIME;
import static org.custime.entertainment.killer.domain.value.GameState.NIGHT;
import static org.custime.entertainment.killer.domain.value.GameState.PROPHET_TIME;
import static org.custime.entertainment.killer.domain.value.GameState.STARTED;
import static org.custime.entertainment.killer.domain.value.GameState.WITCH_TIME;
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
