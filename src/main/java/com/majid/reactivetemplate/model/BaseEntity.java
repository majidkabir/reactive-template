package com.majid.reactivetemplate.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter
@Setter
public abstract class BaseEntity {
    @Id
    private Long id;
}
