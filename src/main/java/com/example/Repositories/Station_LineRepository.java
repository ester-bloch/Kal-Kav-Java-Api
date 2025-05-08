package com.example.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Models.Station_Line;

@Repository
public interface Station_LineRepository extends JpaRepository<Station_Line,Long>{
    Optional<Station_Line> findById(Long id);
}
