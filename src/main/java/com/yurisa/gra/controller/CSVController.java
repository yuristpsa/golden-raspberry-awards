package com.yurisa.gra.controller;

import com.yurisa.gra.domain.csv.CSVToDatabaseComponent;
import com.yurisa.gra.domain.producer.ProducerMapper;
import com.yurisa.gra.domain.producer.ProducerService;
import com.yurisa.gra.dto.ProducerDto;
import com.yurisa.gra.dto.RangeOfAwardsDto;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/csv")
public class CSVController {

    private final CSVToDatabaseComponent csvToDatabaseComponent;
    private final ProducerMapper producerMapper;
    private final ProducerService producerService;

    public CSVController(CSVToDatabaseComponent csvToDatabaseComponent, ProducerMapper producerMapper, ProducerService producerService) {
        this.csvToDatabaseComponent = csvToDatabaseComponent;
        this.producerMapper = producerMapper;
        this.producerService = producerService;
    }

    @PostMapping("/upload")
    public void uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        csvToDatabaseComponent.readCSVFileAndSaveIntoDatabase(file.getInputStream());
    }

    @GetMapping("/awards")
    public RangeOfAwardsDto getRangeOfMaximumAndMinimumAwards() {
        List<ProducerDto> producersMinimumRange = producerService.getProducersWithMinimumIntervalBetweenAwards()
                .stream()
                .map(producerMapper::entityToProducerMinRangeDto).toList();

        List<ProducerDto> producersMaximumRange = producerService.getProducersWithMaximumIntervalBetweenAwards()
                .stream()
                .map(producerMapper::entityToProducerMaxRangeDto).toList();

        return RangeOfAwardsDto
                .builder()
                .min(producersMinimumRange)
                .max(producersMaximumRange)
                .build();
    }
}
