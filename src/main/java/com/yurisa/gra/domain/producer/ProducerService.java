package com.yurisa.gra.domain.producer;

import com.yurisa.gra.base.AbstractService;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProducerService extends AbstractService<Producer, ProducerRepository> {

    public ProducerService(ProducerRepository repo) {
        super(repo);
    }

    public Producer createIfNotExistsOrReturnExistingOne(Producer producer) {
        Optional<Producer> producerFound = this.repo.findByName(producer.getName());
        return producerFound.orElseGet(() -> this.save(producer));
    }

    public List<Producer> getProducersWithMinimumIntervalBetweenAwards() {
        List<Producer> allProducers = listAll();

        Optional<Producer> producer = allProducers
                .stream()
                .min(Comparator.comparingInt(Producer::getMinIntervalBetweenAwards));

        if (producer.isEmpty()) {
            return Collections.emptyList();
        }

        int minIntervalBetweenWinners = producer.get().getMinIntervalBetweenAwards();

        return allProducers
                .stream()
                .filter(f -> Objects.equals(f.getMinIntervalBetweenAwards(), minIntervalBetweenWinners))
                .toList();
    }

    public List<Producer> getProducersWithMaximumIntervalBetweenAwards() {
        List<Producer> allProducers = listAll();

        Optional<Producer> producer = allProducers
                .stream()
                .max(Comparator.comparingInt(Producer::getMaxIntervalBetweenAwards));

        if (producer.isEmpty()) {
            return Collections.emptyList();
        }

        int maxIntervalBetweenWinners = producer.get().getMaxIntervalBetweenAwards();

        return allProducers
                .stream()
                .filter(f -> Objects.equals(f.getMaxIntervalBetweenAwards(), maxIntervalBetweenWinners))
                .toList();
    }
}
