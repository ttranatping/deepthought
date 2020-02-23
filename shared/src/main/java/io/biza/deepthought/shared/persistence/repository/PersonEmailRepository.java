package io.biza.deepthought.shared.persistence.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import io.biza.deepthought.shared.persistence.model.person.PersonEmailData;

@Repository
public interface PersonEmailRepository extends JpaRepository<PersonEmailData, UUID> {
  
}
