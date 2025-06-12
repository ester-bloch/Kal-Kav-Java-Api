package com.example.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Models.Bus;

import io.micrometer.common.lang.NonNull;

@Repository
public interface BusRepository extends JpaRepository<Bus,Long>{
    Optional<Bus> findById(@NonNull Long id);    
}
