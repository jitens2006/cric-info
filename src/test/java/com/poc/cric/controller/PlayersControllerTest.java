package com.poc.cric.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.poc.cric.db.entity.Player;
import com.poc.cric.repository.PlayerRepositoy;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class PlayersControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PlayerRepositoy playerRepository;

    @Test
    public void testCreatePlayer() throws Exception {

        Player player = Player.builder().name("Virat").country("India").build();
        String playerJson = objectMapper.writeValueAsString(player);
        mockMvc.perform(post("/api/v1/players").contentType(MediaType.APPLICATION_JSON).content(playerJson))
                .andExpect(status().isOk()).andExpect(jsonPath("$.name").value("Virat"));
    }

    @Test
    public void testFindPlayerById() throws Exception {

        playerRepository.deleteAll();
        Player player = Player.builder().name("Rohit").country("India").build();
        Player pl = playerRepository.save(player);
        System.out.println("Id is:{}" + pl.getId());
        System.out.println("Player received from Repository is {}" + pl);
        ResultActions response = mockMvc.perform(get("/api/v1/players/{id}", pl.getId()));
        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Rohit"))
                .andExpect(jsonPath("$.country").value("India"));
    }

    @Test
    public void testGetAllPlayers() throws Exception {

        List<Player> listOfPlayers = new ArrayList<>();
        playerRepository.deleteAll();

        Player player1 = Player.builder().id(2).name("Rohit").country("India").build();
        Player player2 = Player.builder().id(3).name("Pant").country("India").build();

        listOfPlayers.add(player1);
        listOfPlayers.add(player2);

        playerRepository.saveAll(listOfPlayers);

        ResultActions response = mockMvc.perform(get("/api/v1/players/"));

        response.andExpect(status().isOk()).andExpect(jsonPath("$.size()", is(listOfPlayers.size())));
    }

    @Test
    public void testUpdatePlayer() throws Exception {

        playerRepository.deleteAll();
        Player player = Player.builder().name("Rohit").country("India").runs(5000).wickets(5).build();
        Player savedPlayer = playerRepository.save(player);

        Player updatedPlayer = new Player();
        updatedPlayer.setName("Virat");
        updatedPlayer.setCountry("India");

        ResultActions response = mockMvc.perform(put("/api/v1/players/{id}", savedPlayer.getId())
                .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(updatedPlayer)));

        response.andExpect(status().isOk())
                 .andDo(print()).andExpect(jsonPath("$.name", is(updatedPlayer.getName())))
                .andExpect(jsonPath("$.country", is(updatedPlayer.getCountry())));
    }
}