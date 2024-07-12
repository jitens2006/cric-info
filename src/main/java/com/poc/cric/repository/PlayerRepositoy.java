package com.poc.cric.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.poc.cric.db.entity.Player;

@Repository
public interface PlayerRepositoy extends JpaRepository<Player, Integer> {
}
