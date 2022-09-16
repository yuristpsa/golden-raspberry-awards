package com.yurisa.gra.domain.producer;

import com.yurisa.gra.base.BaseEntity;
import com.yurisa.gra.domain.movie.Movie;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "producer")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Producer implements BaseEntity {

    @Id
    @GeneratedValue(generator = "producer", strategy = GenerationType.TABLE)
    @TableGenerator(name = "producer", table = "sequence")
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinTable(name = "producer_has_movie", joinColumns = @JoinColumn(name = "producer_id"), inverseJoinColumns = @JoinColumn(name = "movie_id"))
    private List<Movie> movies = new ArrayList<>();

    public void addMovie(Movie movie) {
        if (Objects.isNull(this.movies)) {
            this.movies = new ArrayList<>();
        }

        this.movies.add(movie);
    }

    private List<RangeOfAward> getRangeOfAwards() {
        List<Integer> winnerYears = this.movies.stream()
                .filter(Movie::isWinner)
                .map(Movie::getReleaseYear)
                .sorted()
                .toList();

        List<RangeOfAward> rangeOfAwards = new ArrayList<>();
        RangeOfAward currentRangeOfAward = null;

        for (int year : winnerYears) {

            if (Objects.isNull(currentRangeOfAward)) {
                currentRangeOfAward = RangeOfAward.builder().previousWin(year).build();
            } else {
                currentRangeOfAward.setFollowingWin(year);
                rangeOfAwards.add(currentRangeOfAward);
                currentRangeOfAward = null;
            }
        }

        return rangeOfAwards;
    }

    public Optional<RangeOfAward> getMinRangeOfAwards() {
        return this.getRangeOfAwards().stream().min(Comparator.comparingInt(RangeOfAward::getInterval));
    }

    public Optional<RangeOfAward> getMaxRangeOfAwards() {
        return this.getRangeOfAwards().stream().max(Comparator.comparingInt(RangeOfAward::getInterval));
    }

    public int getMinIntervalBetweenAwards() {
        Optional<RangeOfAward> rangeOfAward = this.getRangeOfAwards().stream().min(Comparator.comparingInt(RangeOfAward::getInterval));
        return rangeOfAward.map(RangeOfAward::getInterval).orElse(Integer.MAX_VALUE);
    }

    public int getMaxIntervalBetweenAwards() {
        Optional<RangeOfAward> rangeOfAward = this.getRangeOfAwards().stream().max(Comparator.comparingInt(RangeOfAward::getInterval));
        return rangeOfAward.map(RangeOfAward::getInterval).orElse(Integer.MIN_VALUE);
    }

}
