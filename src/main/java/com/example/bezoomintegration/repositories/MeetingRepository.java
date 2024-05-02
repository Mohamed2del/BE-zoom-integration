package com.example.bezoomintegration.repositories;

import com.example.bezoomintegration.entites.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MeetingRepository extends JpaRepository<Meeting, Long> {

    List<Meeting> findByHostEmailLike(String host_email);
}