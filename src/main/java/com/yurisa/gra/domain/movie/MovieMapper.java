package com.yurisa.gra.domain.movie;

import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class MovieMapper {

    public Movie recordCSVToEntity(CSVRecord csvRecord) {
        return Movie.builder()
                .title(csvRecord.get("title"))
                .releaseYear(Integer.parseInt(csvRecord.get("year")))
                .winner(isWinner(csvRecord.get("winner")))
                .build();
    }

    private boolean isWinner(String value) {
        return Objects.equals(value, "yes");
    }
}
