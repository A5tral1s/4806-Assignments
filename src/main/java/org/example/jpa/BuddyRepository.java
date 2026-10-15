package org.example.jpa;

import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;

@Repository
public interface BuddyRepository extends CrudRepository<BuddyInfo, Long> {
}