package org.example.jpa.repository;

import org.example.jpa.model.BuddyInfo;
import org.springframework.data.repository.CrudRepository;

public interface BuddyRepository extends CrudRepository<BuddyInfo, Long> {
}