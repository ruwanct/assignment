package com.bol.mancala;

import com.bol.mancala.controller.dto.request.GameStartRequest;
import com.bol.mancala.controller.dto.request.JoinGameRequest;
import com.bol.mancala.controller.dto.response.GameDto;
import com.bol.mancala.model.Board;
import com.bol.mancala.model.Pit;
import com.bol.mancala.model.Player;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
class MancalaApplicationIntegrationTests {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    static PostgreSQLContainer postgreSQLContainer;
    private static GameDto responseGameDto;

    @BeforeAll
    static void beforeAll() {
        //Using testContainers to run a postgres container for integration testing
        postgreSQLContainer = new PostgreSQLContainer("postgres:latest")
                .withDatabaseName("mancala")
                .withUsername("testUser")
                .withPassword("testpwd");
        postgreSQLContainer.start();

        responseGameDto = GameDto.builder()
                .gameId(1L)
                .board(Board.builder()
                        .id(1L)
                        .rows(2)
                        .cols(7)
                        .pits(createPit())
                        .build())
                .stonesPerPit(4)
                .players(List.of(Player.builder()
                        .id(1L)
                        .name("Player_01")
                        .build(), Player.builder()
                        .id(2L)
                        .name("Player_02")
                        .build()))
                .started(true)
                .prevTurn(-1)
                .nextTurn(0)
                .stonesPerPit(4)
                .lastSownPit(null)
                .winner(-1)
                .build();
    }

    @DynamicPropertySource
    static void configureContainer(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
    }

    @Test
    void testStartGame_Integration() throws Exception {
        GameStartRequest request = new GameStartRequest(List.of("Player_01", "Player_02"));
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/game/start")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(responseGameDto))); // Verify the response content
    }

    @Test
    void testJoinGame_Integration() throws Exception {
        JoinGameRequest request = new JoinGameRequest(1L);
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/game/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(responseGameDto))); // Verify the response content
    }

    private static Pit[][] createPit() {
        Pit[][] pits = new Pit[2][7];

        for (int row = 0; row < 2; row++) {
            for (int col = 0; col < 7; col++) {
                pits[row][col] = Pit.builder()
                        .id(col)
                        // Set stoneCount to 0 for bigPit, 4 otherwise
                        .stoneCount(col == 6 ? 0 : 4)
                        .bigPit(col == 6)
                        .pitOwner(row)
                        .build();
            }
        }
        return pits;
    }

    @AfterAll
    static void afterAll() {
        postgreSQLContainer.stop();
    }
}