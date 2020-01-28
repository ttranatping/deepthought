package io.biza.deepthought.data;

import java.lang.reflect.InvocationTargetException;
import java.util.Set;
import org.reflections.Reflections;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.converter.BidirectionalConverter;
import ma.glasnost.orika.converter.ConverterFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;

@Slf4j
public class OrikaFactoryConfigurer {

  protected static MapperFactory localMapper;

  /**
   * Produce a local mapper factory
   * @return MapperFactory with configuration
   */
  public MapperFactory getFactory() {
    if (localMapper == null) {
      buildFactory();
    }

    return localMapper;
  }
  
  /**
   * Initialise the mapper, configure it and setup localMapper
   */
  public void buildFactory() {
    /**
     * Initialise the orika mapper
     */
    DefaultMapperFactory.Builder builder = new DefaultMapperFactory.Builder();
    configureFactoryBuilder(builder);
    localMapper = builder.build();
    configureMapperFactory(localMapper);
    
  }
  
  /**
   * Setup the lombok resolver
   * @param builder with lombok compatible resolver
   */
  public void configureFactoryBuilder(DefaultMapperFactory.Builder builder) {
    builder.propertyResolverStrategy(new OrikaFluentLombokResolver());
  }
  
  /**
   * Reflect the converts and mappers and set them up
   * @param mapper with deepthought mapper/converters setup
   */
  public void configureMapperFactory(MapperFactory mapper) {

    /**
     * Configure the factory with converters
     */
    Reflections converterReflections =
        new Reflections("io.biza.deepthought.data.translation.converter");
    ConverterFactory converterFactory = mapper.getConverterFactory();

    @SuppressWarnings("rawtypes")
    Set<Class<? extends BidirectionalConverter>> converterClasses =
        converterReflections.getSubTypesOf(BidirectionalConverter.class);
    for (@SuppressWarnings("rawtypes")
    Class<? extends BidirectionalConverter> clazz : converterClasses) {
      try {
        BidirectionalConverter<?, ?> converter = clazz.getConstructor().newInstance();
        converterFactory.registerConverter(converter);
      } catch (InstantiationException | IllegalAccessException | InvocationTargetException
          | NoSuchMethodException | SecurityException e) {
        LOG.error("Unable to find a declared constructor for class name of {} with exception of {}",
            clazz.getName(), e.toString());
      } catch (IllegalArgumentException e) {
        LOG.error(
            "Encountered issues when configuring downstream mapper for class name of {} with exception of {}",
            clazz.getName(), e.toString());
      }
    }

    /**
     * Configure the factory using configurers
     */
    Reflections mapperReflections = new Reflections("io.biza.deepthought.data.translation.mapper");

    Set<Class<? extends OrikaFactoryConfigurerInterface>> mapperClasses =
        mapperReflections.getSubTypesOf(OrikaFactoryConfigurerInterface.class);
    for (Class<? extends OrikaFactoryConfigurerInterface> clazz : mapperClasses) {
      try {
        OrikaFactoryConfigurerInterface configurer = clazz.getConstructor().newInstance();
        configurer.configure(mapper);
      } catch (InstantiationException | IllegalAccessException | InvocationTargetException
          | NoSuchMethodException | SecurityException e) {
        LOG.error("Unable to find a declared constructor for class name of {} with exception of {}",
            clazz.getName(), e.toString());
      } catch (IllegalArgumentException e) {
        LOG.error(
            "Encountered issues when configuring downstream mapper for class name of {} with exception of {}",
            clazz.getName(), e.toString());
      }
    }
  }

}
