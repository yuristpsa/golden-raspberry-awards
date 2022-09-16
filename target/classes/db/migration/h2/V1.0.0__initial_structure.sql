CREATE TABLE producer (
	id bigint NOT NULL PRIMARY KEY,
	name VARCHAR (200) NOT NULL
);

CREATE TABLE movie (
	id bigint NOT NULL PRIMARY KEY,
	title VARCHAR (100) NOT NULL,
	release_year number(4) NOT NULL,
	winner boolean
);

CREATE TABLE producer_has_movie (
	producer_id bigint NOT NULL,
	movie_id bigint NOT NULL,
	CONSTRAINT fk_movie_has_producer_studio FOREIGN KEY (producer_id) REFERENCES producer(id),
	CONSTRAINT fk_movie_has_producer_movie FOREIGN KEY (movie_id) REFERENCES movie(id)
);

ALTER TABLE producer_has_movie ADD CONSTRAINT pk_producer_has_movie PRIMARY KEY (producer_id, movie_id);

CREATE TABLE studio (
	id bigint NOT NULL PRIMARY KEY,
	name VARCHAR (100) NOT NULL
);
--
--CREATE TABLE movie_has_studio (
--	movie_id bigint NOT NULL,
--	studio_id bigint NOT NULL,
--	CONSTRAINT fk_movie_has_studio_movie FOREIGN KEY (movie_id) REFERENCES movie(id),
--	CONSTRAINT fk_movie_has_studio_studio FOREIGN KEY (studio_id) REFERENCES studio(id)
--);
--
--ALTER TABLE movie_has_studio ADD CONSTRAINT pk_movie_has_studio PRIMARY KEY (movie_id, studio_id);

--CREATE TABLE movie_has_producer (
--	movie_id bigint NOT NULL,
--	producer_id bigint NOT NULL,
--	CONSTRAINT fk_movie_has_producer_movie FOREIGN KEY (movie_id) REFERENCES movie(id),
--	CONSTRAINT fk_movie_has_producer_studio FOREIGN KEY (producer_id) REFERENCES producer(id)
--);
--
--ALTER TABLE movie_has_producer ADD CONSTRAINT pk_movie_has_producer PRIMARY KEY (movie_id, producer_id);
