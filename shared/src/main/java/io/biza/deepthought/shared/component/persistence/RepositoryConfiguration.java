package io.biza.deepthought.shared.component.persistence;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EntityScan(basePackages = "io.biza.deepthought.shared.persistence.model")
@EnableJpaRepositories(basePackages = "io.biza.deepthought.shared.persistence.repository")
@EnableTransactionManagement
public class RepositoryConfiguration {


}


