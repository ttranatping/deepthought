package io.biza.deepthought.product;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EntityScan(basePackages = "io.biza.deepthought.data.persistence.model")
@EnableJpaRepositories(basePackages = "io.biza.deepthought.data.repository")
@EnableTransactionManagement
public class RepositoryConfiguration {


}
