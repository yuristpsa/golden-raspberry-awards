package com.yurisa.gra.domain.producer;

import com.yurisa.gra.dto.ProducerDto;
import com.yurisa.gra.util.StringUtil;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ProducerMapper {

    public Producer recordCSVToEntity(CSVRecord csvRecord) {
        return Producer.builder()
                .name(csvRecord.get("producers"))
                .build();
    }

    public List<Producer> recordCSVToEntities(CSVRecord csvRecord) {
        return Arrays.stream(csvRecord.get("producers").split(";|,| and "))
                .filter(f -> Objects.nonNull(f) && !f.isEmpty())
                .map(studioName -> Producer.builder()
                        .name(removeInitialAndEndSpaces(studioName))
                        .build())
                .collect(Collectors.toList());
    }

    private String removeInitialAndEndSpaces(String name) {
        String nameWithoutSpacesInTheBegginingAndEnd = StringUtil.rightTrim(name);
        nameWithoutSpacesInTheBegginingAndEnd = StringUtil.leftTrim(name);

        return nameWithoutSpacesInTheBegginingAndEnd;
    }

    public List<ProducerDto> entityToProducersMinRangeDto(Producer producer) {
        return producer
                .getRangeOfAwards()
                .stream()
                .filter(f -> Objects.equals(f.getInterval(), producer.getMinIntervalBetweenAwards()))
                .map(range -> ProducerDto
                        .builder()
                        .producer(producer.getName())
                        .previousWin(range.getPreviousWin())
                        .followingWin(range.getFollowingWin())
                        .interval(range.getInterval())
                        .build())
                .toList();
    }

    public List<ProducerDto> entityToProducersMaxRangeDto(Producer producer) {
        return producer
                .getRangeOfAwards()
                .stream()
                .filter(f -> Objects.equals(f.getInterval(), producer.getMaxIntervalBetweenAwards()))
                .map(range -> ProducerDto
                        .builder()
                        .producer(producer.getName())
                        .previousWin(range.getPreviousWin())
                        .followingWin(range.getFollowingWin())
                        .interval(range.getInterval())
                        .build())
                .toList();
    }
}
