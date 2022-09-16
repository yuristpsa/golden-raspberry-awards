CREATE TABLE public.sequence (
    sequence_name varchar(100) NOT NULL PRIMARY KEY,
    next_val bigint
);

INSERT INTO sequence(sequence_name, next_val) VALUES ('movie', 0);