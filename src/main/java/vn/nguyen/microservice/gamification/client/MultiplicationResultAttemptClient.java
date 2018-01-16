package vn.nguyen.microservice.gamification.client;

import vn.nguyen.microservice.gamification.client.dto.MultiplicationResultAttempt;

/**
 * Created by nals on 1/15/18.
 */
public interface MultiplicationResultAttemptClient {
    MultiplicationResultAttempt  retrieveMultiplicationResultAttemptById(final Long multiplicationResultAttemptId);
}
