package com.example.Repositories;

import java.security.cert.PKIXRevocationChecker.Option;
import java.sql.Time;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Models.Travel;

@Repository
public interface TravelRepository extends JpaRepository<Travel,Long>{
    Optional<List<Travel>> findByDepartureTime(Time hour);
}
