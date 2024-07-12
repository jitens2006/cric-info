package com.poc.cric.service;

import java.util.List;

import com.poc.cric.db.entity.Player;

public interface PlayerService {
    Player savePlayerInformation(Player player);

    public List<Player> getAllPlayers();

    public void deletePlayer(int id);

    public Player getPlayerById(int id);

    public Player updatePlayer(Player product);
}
