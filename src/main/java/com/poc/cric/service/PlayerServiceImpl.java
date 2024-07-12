package com.poc.cric.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poc.cric.db.entity.Player;
import com.poc.cric.exception.PlayerNotFoundException;
import com.poc.cric.repository.PlayerRepositoy;

@Service
public class PlayerServiceImpl implements PlayerService {

    private static final Logger log = LoggerFactory.getLogger(PlayerServiceImpl.class);

    @Autowired
    PlayerRepositoy playerRepository;

    public Player savePlayerInformation(Player player) {
        log.debug("Saving to Repository");
        log.trace("Player is: {}", player);
        return playerRepository.save(player);
    }

    public List<Player> getAllPlayers() {
        log.debug("Fetching all Players");
        return playerRepository.findAll();
    }

    public void deletePlayer(int id) {
        log.debug("Deleting the id: {}", id);
        Optional<Player> playerDB = this.playerRepository.findById(id);

        if (playerDB.isPresent()) {
            this.playerRepository.delete(playerDB.get());
            log.info("Successfully deleted id: {}", id);
        } else {
            log.warn("Record not found Exception");
            throw new PlayerNotFoundException("Record to be deleted not found:" + id);
        }
    }

    /**
     * 
     * @param id
     * @return
     */
    public Player getPlayerById(int id) {
        Optional<Player> player = playerRepository.findById(id);
        if (player.isPresent()) {
            return player.get();
        } else {
            log.warn("No Record found with id:{}", id);
            throw new PlayerNotFoundException("Player with record not found with id : " + id);
        }
    }

    @Override
    public Player updatePlayer(Player player) {
        log.info("Updating Player Info");
        Optional<Player> playerRetrieved = this.playerRepository.findById(player.getId());
        if (playerRetrieved.isPresent()) {
            Player updatedPlayer = playerRetrieved.get();
            updatedPlayer.setId(player.getId());
            updatedPlayer.setName(player.getName());
            updatedPlayer.setCountry(player.getCountry());
            playerRepository.save(updatedPlayer);
            return updatedPlayer;
        } else {
            throw new PlayerNotFoundException("Update Failure: Player not found with id:" + player.getId());
        }
    }
}
