package com.yurisa.gra.domain.csv;

import com.yurisa.gra.domain.movie.Movie;
import com.yurisa.gra.domain.movie.MovieMapper;
import com.yurisa.gra.domain.movie.MovieService;
import com.yurisa.gra.domain.producer.Producer;
import com.yurisa.gra.domain.producer.ProducerMapper;
import com.yurisa.gra.domain.producer.ProducerService;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

@Component
public class CSVToDatabaseComponent {

    private final static char DELIMITER = ';';
    private final MovieMapper movieMapper;
    private final ProducerMapper producerMapper;
    private final MovieService movieService;
    private final ProducerService producerService;

    public CSVToDatabaseComponent(MovieMapper movieMapper,
                                  ProducerMapper producerMapper,
                                  MovieService movieService,
                                  ProducerService producerService) {
        this.movieMapper = movieMapper;
        this.producerMapper = producerMapper;
        this.movieService = movieService;
        this.producerService = producerService;
    }

    @Transactional
    public void readCSVFileAndSaveIntoDatabase(InputStream is) throws IOException {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
             CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withDelimiter(DELIMITER).withTrim())) {
            csvParser.getRecords().forEach(this::saveToDatabase);
        }
    }

    private void saveToDatabase(CSVRecord csvRecord) {
        Movie movie = movieService.save(movieMapper.recordCSVToEntity(csvRecord));

        producerMapper.recordCSVToEntities(csvRecord)
                .forEach(f -> {
                    Producer producer = producerService.createIfNotExistsOrReturnExistingOne(f);
                    producer.addMovie(movie);

                    producerService.save(producer);
                });
    }
}
