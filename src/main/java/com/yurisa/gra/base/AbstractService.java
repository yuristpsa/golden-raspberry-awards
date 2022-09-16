package com.yurisa.gra.base;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public class AbstractService<E extends BaseEntity, R extends JpaRepository<E, Long>> {

    protected R repo;

    public AbstractService(R repo) {
        this.repo = repo;
    }

    public E save(E entity) {
        return this.repo.save(entity);
    }

    public void delete(Long id) {
        this.repo.deleteById(id);
    }

    public boolean exists(Long id) {
        return this.repo.existsById(id);
    }

    public Optional<E> findById(Long id) {
        return this.repo.findById(id);
    }

    public List<E> listAll() {
        return this.repo.findAll();
    }
}
