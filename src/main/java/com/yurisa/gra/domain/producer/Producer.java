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

    public List<RangeOfAward> getRangeOfAwards() {
        List<Integer> winnerYears = getWinnerYears();

        RangeOfAward rangeOfAward = null;
        List<RangeOfAward> rangeOfAwards = new ArrayList<>();

        for (int year : winnerYears) {

            if (Objects.isNull(rangeOfAward)) {
                rangeOfAward = RangeOfAward.builder().previousWin(year).build();
            } else {
                rangeOfAward.setFollowingWin(year);
                rangeOfAwards.add(rangeOfAward);

                rangeOfAward = RangeOfAward
                        .builder()
                        .previousWin(year)
                        .build();
            }
        }

        return rangeOfAwards;
    }

    private List<Integer> getWinnerYears() {
        return this.movies.stream()
                .filter(Movie::isWinner)
                .map(Movie::getReleaseYear)
                .sorted()
                .toList();
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
