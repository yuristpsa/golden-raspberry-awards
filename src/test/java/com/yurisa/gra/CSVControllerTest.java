package com.yurisa.gra;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yurisa.gra.dto.ProducerDto;
import com.yurisa.gra.dto.RangeOfAwardsDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@WebAppConfiguration
@SpringBootTest
class CSVControllerTest {

    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext context;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        this.mockMvc = webAppContextSetup(this.context).build();
    }

    @Nested
    class GIVEN_csv_file_uploaded {

        @BeforeEach
        private void setup() throws Exception {
            File fIle = getFileFromResource();
            byte[] fileContent = Files.readAllBytes(fIle.toPath());

            mockMvc.perform(multipart("/api/csv/upload")
                            .file("file", fileContent)
                            .contentType("text/csv")
                            .characterEncoding("UTF-8"))
                    .andExpect(status().isOk());
        }

        @Nested
        class WHEN_get_interval_between_awards {

            private RangeOfAwardsDto rangeOfAwardsDto;

            @BeforeEach
            private void setup() throws Exception {
                String response = mockMvc.perform(get("/api/csv/awards"))
                        .andExpect(status().isOk())
                        .andReturn().getResponse().getContentAsString();

                rangeOfAwardsDto = objectMapper.readValue(response, RangeOfAwardsDto.class);
            }

            @Test
            void THEN_must_return_producers_that_have_min_and_max_interval_between_awards() {
                assertEquals(1, rangeOfAwardsDto.getMin().size());
                ProducerDto producerMinimumInterval = rangeOfAwardsDto.getMin().get(0);
                assertEquals("Joel Silver", producerMinimumInterval.getProducer());
                assertEquals(1990, producerMinimumInterval.getPreviousWin());
                assertEquals(1991, producerMinimumInterval.getFollowingWin());
                assertEquals(1, producerMinimumInterval.getInterval());

                assertEquals(1, rangeOfAwardsDto.getMax().size());
                ProducerDto producerMaximumInterval = rangeOfAwardsDto.getMax().get(0);
                assertEquals("Matthew Vaughn", producerMaximumInterval.getProducer());
                assertEquals(2002, producerMaximumInterval.getPreviousWin());
                assertEquals(2015, producerMaximumInterval.getFollowingWin());
                assertEquals(13, producerMaximumInterval.getInterval());
            }
        }
    }

    private File getFileFromResource() throws URISyntaxException {
        ClassLoader classLoader = getClass().getClassLoader();
        URL resource = classLoader.getResource("csv/movielist.csv");
        return new File(resource.toURI());
    }
}
