package io.biza.deepthought.shared.mapper;

import ma.glasnost.orika.MapperFactory;

/**
 * An interface for Orika Mapper Factory setup
 */
public interface OrikaFactoryConfigurerInterface {
  void configure(MapperFactory orikaMapperFactory);
}
