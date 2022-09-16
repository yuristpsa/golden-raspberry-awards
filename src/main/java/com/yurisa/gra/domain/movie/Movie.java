package com.yurisa.gra.domain.movie;

import com.yurisa.gra.base.BaseEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "movie")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Movie implements BaseEntity {

    @Id
    @GeneratedValue(generator = "movie", strategy = GenerationType.TABLE)
    @TableGenerator(name = "movie", table = "sequence")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "release_year")
    private int releaseYear;

    @Column(name = "winner")
    private boolean winner;

}
