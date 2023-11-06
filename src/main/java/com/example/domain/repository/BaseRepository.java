package com.example.domain.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.io.Serializable;

@NoRepositoryBean
@Transactional
public interface BaseRepository<T,ID extends Serializable> extends CrudRepository<T,ID>, JpaRepository<T,ID>, PagingAndSortingRepository<T,ID> {
}
