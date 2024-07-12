package com.poc.cric.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.poc.cric.db.entity.ApplicationEntity;

@Repository
public interface ApplicationEventRepository extends JpaRepository<ApplicationEntity, Integer> {

}
