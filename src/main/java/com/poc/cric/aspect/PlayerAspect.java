package com.poc.cric.aspect;

import java.time.LocalDateTime;
import java.util.Optional;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.poc.cric.db.entity.ApplicationEntity;
import com.poc.cric.db.entity.Player;
import com.poc.cric.exception.PlayerNotFoundException;
import com.poc.cric.repository.ApplicationEventRepository;
import com.poc.cric.repository.PlayerRepositoy;

@Aspect
@Component

/*
 * This class will audit whenever there is a change in Players API. It will log
 * time, event type, json for Delete, PUT and POST operations into
 * APPLICATION_ENTITY table.
 */
public class PlayerAspect {

    private static final Logger logger = LoggerFactory.getLogger(PlayerAspect.class);

    @Autowired
    private ApplicationEventRepository applicationEventsRepository;

    @Autowired
    private PlayerRepositoy playerRepository;

    @Pointcut("execution(* com.poc.cric.service.PlayerServiceImpl.deletePlayer(..))")
    public void serviceImplDeleteMethod() {
    }

    @Pointcut("execution(* com.poc.cric.service.PlayerServiceImpl.savePlayerInformation(..))")
    public void serviceImplSaveMethod() {

    }

    @Pointcut("execution(* com.poc.cric.service.PlayerServiceImpl.updatePlayer(..))")
    public void serviceImplUpdateMethod() {

    }

    @AfterReturning(value = "serviceImplSaveMethod()", returning = "player")
    public void afterAdvice(Object player) throws JsonProcessingException {
        savePlayerApplicationEvents((Player) player, "Create");
    }

    @AfterReturning(value = "serviceImplUpdateMethod()", returning = "player")
    public void afterAdvicePut(Object player) throws JsonProcessingException {
        savePlayerApplicationEvents((Player) player, "Put");
    }

    @Around(" serviceImplDeleteMethod() && args(id)")
    private Object aroundAdviceDelete(ProceedingJoinPoint joinPoint, int id) throws Throwable {
        logger.trace("id value is:{}", id);
        Object proceed = null;
        Optional<Player> playerRetrieved = this.playerRepository.findById(id);
        if (playerRetrieved.isPresent()) {
            proceed = joinPoint.proceed();
            logger.debug("Adding Delete event");
            savePlayerApplicationEvents(playerRetrieved.get(), "Delete");
        } else {
            throw new PlayerNotFoundException("Update Failure: Player not found with id:" + id);
        }
        return proceed;
    }

    /**
     * Writing to APLICATION ENTITY table
     * @param playerEntityNew
     * @param eventType
     * @throws JsonProcessingException
     */
    private void savePlayerApplicationEvents(Player playerEntityNew, String eventType) throws JsonProcessingException {
        ApplicationEntity applicationEntity = new ApplicationEntity();
        String playerJson = new ObjectMapper().writeValueAsString(playerEntityNew);
        applicationEntity.setPlayerId(playerEntityNew.getId());
        applicationEntity.setEventType(eventType);
        LocalDateTime loggingTime = LocalDateTime.now();
        applicationEntity.setChangeTime(loggingTime);
        applicationEntity.setJson(playerJson);
        applicationEventsRepository.save(applicationEntity);
        logger.info("Save Details added to ApplicationEntity");
    }
}
