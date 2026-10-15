package org.example.jpa;

import org.springframework.data.repository.CrudRepository;

public interface BuddyRepository extends CrudRepository<BuddyInfo, Long> {
}