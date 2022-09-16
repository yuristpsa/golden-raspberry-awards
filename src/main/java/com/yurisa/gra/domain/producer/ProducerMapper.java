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

    public ProducerDto entityToProducerMinRangeDto(Producer producer) {
        ProducerDto producerDto = ProducerDto
                .builder()
                .producer(producer.getName())
                .build();

        Optional<RangeOfAward> minRangeOfAward = producer.getMinRangeOfAwards();
        if (minRangeOfAward.isPresent()) {
            producerDto.setPreviousWin(minRangeOfAward.get().getPreviousWin());
            producerDto.setFollowingWin(minRangeOfAward.get().getFollowingWin());
            producerDto.setInterval(minRangeOfAward.get().getInterval());
        }

        return producerDto;
    }

    public ProducerDto entityToProducerMaxRangeDto(Producer producer) {
        ProducerDto producerDto = ProducerDto
                .builder()
                .producer(producer.getName())
                .build();

        Optional<RangeOfAward> maxRangeOfAward = producer.getMaxRangeOfAwards();
        if (maxRangeOfAward.isPresent()) {
            producerDto.setPreviousWin(maxRangeOfAward.get().getPreviousWin());
            producerDto.setFollowingWin(maxRangeOfAward.get().getFollowingWin());
            producerDto.setInterval(maxRangeOfAward.get().getInterval());
        }

        return producerDto;
    }
}
