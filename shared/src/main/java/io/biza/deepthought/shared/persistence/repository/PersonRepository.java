package io.biza.deepthought.shared.persistence.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import io.biza.deepthought.shared.persistence.model.person.PersonData;

@Repository
public interface PersonRepository extends JpaRepository<PersonData, UUID> {
  
}