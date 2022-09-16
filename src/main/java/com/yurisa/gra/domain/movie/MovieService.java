package com.yurisa.gra.domain.movie;

import com.yurisa.gra.base.AbstractService;
import org.springframework.stereotype.Service;

@Service
public class MovieService extends AbstractService<Movie, MovieRepository> {

    public MovieService(MovieRepository repo) {
        super(repo);
    }
}
