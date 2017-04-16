package com.pillbox.repository;

import com.pillbox.entity.PillRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PillRecordRepository extends JpaRepository<PillRecord, Long> {

    PillRecord findByPillRecordId(Long pillRecordId);

}
