package org.custime.entertainment.killer;

import org.custime.entertainment.killer.domain.factory.PlayerFactory;
import org.custime.entertainment.killer.domain.factory.RoomFactory;
import org.custime.entertainment.killer.domain.repository.PlayerRepository;
import org.custime.entertainment.killer.domain.repository.RoomRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

    public static void main(final String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public PlayerFactory playerFactory() {
        return new PlayerFactory();
    }

    @Bean
    public PlayerRepository playerRepository() {
        return PlayerRepository.getInstance();
    }

    @Bean
    public RoomFactory roomFactory() {
        return new RoomFactory();
    }

    @Bean
    public RoomRepository roomRepository() {
        return RoomRepository.getInstance();
    }
}
