package com.pillbox.repository;

import com.pillbox.entity.PillBox;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PillBoxRepository extends JpaRepository<PillBox, Long> {

    PillBox findById(Long id);

}
