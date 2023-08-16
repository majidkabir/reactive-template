package com.majid.reactivetemplate;

import com.majid.reactivetemplate.model.BaseEntity;
import org.mockito.stubbing.Answer;
import reactor.core.publisher.Mono;

import java.util.Random;

public class TestUtil {
    public static Answer<Mono<BaseEntity>> mockSaveToDB() {
        return invocation -> {
            BaseEntity toBeSavedEntity = invocation.getArgument(0);
            toBeSavedEntity.setId(new Random().nextLong());
            return Mono.just(toBeSavedEntity);
        };
    }
}
