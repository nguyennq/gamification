package vn.nguyen.microservice.gamification.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import vn.nguyen.microservice.gamification.service.GameService;

/**
 * Created by nals on 1/15/18.
 */
@Component
@Slf4j
public class EventHandler {

    private GameService gameService;

    public EventHandler(final GameService gameService) {
        this.gameService = gameService;
    }

    @RabbitListener(queues = "${multiplication.queue}")
    public void handleMultiplicationSolved(final MultiplicationSolvedEvent multiplicationSolvedEvent){
        log.info("Multiplication Solved Event received: {}", multiplicationSolvedEvent.getMultiplicationResultAttemptId());
        try {
            gameService.newAttemptForUser(multiplicationSolvedEvent.getUserId(),
                    multiplicationSolvedEvent.getMultiplicationResultAttemptId(),
                    multiplicationSolvedEvent.isCorrect());
        }
        catch (final Exception e){
            log.error("Error when trying to process MultiplicationSolvedEvent", e);
            throw new AmqpRejectAndDontRequeueException(e);
        }
    }
}
