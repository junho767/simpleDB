package org.example;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Article {
    private long id;
    private String title;
    private String body;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    @JsonProperty("isBlind")
    private boolean isBlind;
}
