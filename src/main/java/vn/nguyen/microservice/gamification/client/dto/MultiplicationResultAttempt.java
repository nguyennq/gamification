package vn.nguyen.microservice.gamification.client.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import vn.nguyen.microservice.gamification.client.MultiplicationResultAttemptDeserializer;

/**
 * Created by nals on 1/15/18.
 */
@RequiredArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
@JsonDeserialize(using = MultiplicationResultAttemptDeserializer.class)
public class MultiplicationResultAttempt {

    private final String userAlias;
    private final int multiplicationFactorNumberA;
    private final int multiplicationFactorNumberB;
    private final int resultAttempt;
    private final boolean correct;

    public MultiplicationResultAttempt() {
        userAlias = null;
        multiplicationFactorNumberA = -1;
        multiplicationFactorNumberB = -1;
        resultAttempt = -1;
        correct = false;
    }
}

