package com.yazidsistems.app.cruddemo.repository;

import com.yazidsistems.app.cruddemo.entity.Model;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Repository extends JpaRepository <Model, Integer> {
}
