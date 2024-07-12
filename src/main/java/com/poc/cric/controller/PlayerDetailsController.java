package com.poc.cric.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.poc.cric.db.entity.Player;
import com.poc.cric.service.PlayerService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1")
@Tag(name = "Players", description = "Player API(s)Maintain Player stats")
public class PlayerDetailsController {

    private static final Logger log = LoggerFactory.getLogger(PlayerDetailsController.class);

    @Autowired
    private PlayerService playerService;

    @Operation(summary = "Register a new Player", description="Create a new Player in System", responses = {
            @ApiResponse(responseCode = "200", description = "Returns the registered Player", 
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Player.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input")
            })
    @PostMapping(value = "/players")
    public ResponseEntity<Player> savePlayer(
            @Parameter(in = ParameterIn.DEFAULT, description = "Create a new Player", required = true, schema = @Schema()) @Valid @RequestBody Player player) {
        return ResponseEntity.ok(playerService.savePlayerInformation(player));
    }

    @Operation(summary = "Returns all Players", description = "Fetches all Players from DB", tags = {
            "Player", }, responses = {
                    @ApiResponse(responseCode = "200", description = "Returns all users", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Player.class))) })
    @GetMapping(value = "/players")
    public ResponseEntity<List<Player>> getAllPlayers() {
        return ResponseEntity.ok().body(playerService.getAllPlayers());
    }

    @Operation(summary = "Get a Player by its id", description = "Get a Player Object by specifying its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found Player Info", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Player.class)) }),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Player not found", content = @Content) })

    @Parameters({ @Parameter(name = "id", description = "Search Player by Id", required = true) })
    @GetMapping("/players/{id}")
    public ResponseEntity<Player> findPlayerById(@PathVariable("id") int id) {
        Player player = playerService.getPlayerById(id);
        return ResponseEntity.ok().body(player);
    }
       
    
    @Operation(summary = "Update a Player Detail", description = "Player Info Updations by providing id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Returns the updated Player",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Player.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Invalid PlayerID supplied", content =@Content)
    })
    @Parameters({ @Parameter(name = "id", description = "Player Identifier", required = true) })
    @PutMapping("/players/{id}")
    public ResponseEntity<Player> updatePlayer(@PathVariable int id, 
        @Valid @RequestBody Player player) {
        log.info("Updating Player for id : {}", id);
        player.setId(id);
        return ResponseEntity.ok().body(this.playerService.updatePlayer(player));
    }

    
    @DeleteMapping("/secure/players/{id}")
    @Operation(summary = "Delete a Player", description = "Delete the Player ", 
    responses = {
            @ApiResponse(responseCode = "200", description = "Successfully Delete", content = @Content),
            @ApiResponse(responseCode = "404", description = "PlayerId does not exist", content =@Content),
            @ApiResponse(responseCode = "401", description = "Invalid Credential", content =@Content),
    })
    @Parameters({ @Parameter(name = "id", description = "Player Identifier for deletion purpose", required = true) }) 
    public HttpStatus deletePlayer(@PathVariable int id) {
        this.playerService.deletePlayer(id);
        return HttpStatus.OK;
    }
}
