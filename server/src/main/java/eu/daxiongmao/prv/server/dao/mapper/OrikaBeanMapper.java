package eu.daxiongmao.prv.server.dao.mapper;

import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.convert.converter.ConverterFactory;
import org.springframework.stereotype.Component;

import ma.glasnost.orika.Converter;
import ma.glasnost.orika.Mapper;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import ma.glasnost.orika.metadata.ClassMapBuilder;

/**
 * A bean mapper designed for Spring suitable for dependency injection.
 *
 * Provides an implementation of {@link MapperFacade} which can be injected. In
 * addition it is "Spring aware" in that it can autodiscover any implementations
 * of {@link Mapper} or {@link Converter} that are managed beans within it's
 * parent {@link ApplicationContext}.
 *
 * @author Ken Blair
 */
@Component
public class OrikaBeanMapper extends ConfigurableMapper implements ApplicationContextAware {

    private final static Logger LOGGER = LoggerFactory.getLogger(OrikaBeanMapper.class);

    private MapperFactory factory;
    private ApplicationContext applicationContext;

    public OrikaBeanMapper() {
        super(false);
    }

    @PostConstruct
    public void init() {
        LOGGER.info(this.getClass().getName() + " Started!");
        super.init();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void configure(final MapperFactory factory) {
        this.factory = factory;
        addAllSpringBeans(applicationContext);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void configureFactoryBuilder(final DefaultMapperFactory.Builder factoryBuilder) {
        // Nothing to configure for now
    }

    /**
     * Constructs and registers a {@link ClassMapBuilder} into the
     * {@link MapperFactory} using a {@link Mapper}.
     *
     * @param mapper
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void addMapper(final GenericMapper<?, ?> mapper) {
        ClassMapBuilder<?, ?> cmb = factory.classMap(mapper.getAType(), mapper.getBType()).byDefault();
        for (Map.Entry<String, String> m : mapper.getFields().entrySet()) {
            cmb = cmb.field(m.getKey(), m.getValue());
        }
        cmb.customize((Mapper) mapper).register();
    }

    /**
     * Registers a {@link Converter} into the {@link ConverterFactory}.
     *
     * @param converter
     */
    public void addConverter(final Converter<?, ?> converter) {
        factory.getConverterFactory().registerConverter(converter);
    }

    /**
     * Scans the appliaction context and registers all Mappers and Converters
     * found in it.
     *
     * @param applicationContext
     */
    @SuppressWarnings("rawtypes")
    private void addAllSpringBeans(final ApplicationContext applicationContext) {
        Map<String, GenericMapper> mappers = applicationContext.getBeansOfType(GenericMapper.class);
        for (GenericMapper mapper : mappers.values()) {
            addMapper(mapper);
        }
        Map<String, Converter> converters = applicationContext.getBeansOfType(Converter.class);
        for (Converter converter : converters.values()) {
            addConverter(converter);
        }
    }

    public void setApplicationContext(final ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

}
