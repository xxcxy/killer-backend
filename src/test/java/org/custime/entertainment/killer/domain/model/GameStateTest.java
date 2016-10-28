package org.custime.entertainment.killer.domain.model;

import org.junit.Test;

import static org.custime.entertainment.killer.domain.value.GameState.DAY;
import static org.custime.entertainment.killer.domain.value.GameState.KILLER_TIME;
import static org.custime.entertainment.killer.domain.value.GameState.NIGHT;
import static org.custime.entertainment.killer.domain.value.GameState.PROPHET_ANSWER_TIME;
import static org.custime.entertainment.killer.domain.value.GameState.PROPHET_ASK_TIME;
import static org.custime.entertainment.killer.domain.value.GameState.STARTED;
import static org.custime.entertainment.killer.domain.value.GameState.VOTE;
import static org.custime.entertainment.killer.domain.value.GameState.WITCH_POISON_TIME;
import static org.custime.entertainment.killer.domain.value.GameState.WITCH_SAVE_TIME;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class GameStateTest {

    @Test
    public void testStateNext() {
        assertThat(STARTED.next(), is(NIGHT));
        assertThat(NIGHT.next(), is(KILLER_TIME));
        assertThat(KILLER_TIME.next(), is(WITCH_SAVE_TIME));
        assertThat(WITCH_SAVE_TIME.next(), is(WITCH_POISON_TIME));
        assertThat(WITCH_POISON_TIME.next(), is(PROPHET_ASK_TIME));
        assertThat(PROPHET_ASK_TIME.next(), is(PROPHET_ANSWER_TIME));
        assertThat(PROPHET_ANSWER_TIME.next(), is(DAY));
        assertThat(DAY.next(), is(VOTE));
        assertThat(VOTE.next(), is(NIGHT));
    }
}
