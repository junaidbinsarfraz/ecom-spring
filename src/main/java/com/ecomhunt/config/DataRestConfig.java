package com.ecomhunt.config;

import com.ecomhunt.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.core.mapping.ExposureConfigurer;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import javax.persistence.EntityManager;
import javax.persistence.metamodel.EntityType;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Use only for Spring Data Rest not for others like Spring Controllers etc
 */
@Configuration
public class DataRestConfig implements RepositoryRestConfigurer {

    @Autowired
    private EntityManager entityManager;

    @Value("${allowed.origins}")
    private String[] allowedOrigins;

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {

        HttpMethod[] unsupportedActions = {HttpMethod.PUT, HttpMethod.POST, HttpMethod.DELETE, HttpMethod.PATCH};

        disableHttpMethods(config.getExposureConfiguration()
                .forDomainType(Product.class), unsupportedActions);

        disableHttpMethods(config.getExposureConfiguration()
                .forDomainType(ProductCategory.class), unsupportedActions);

        disableHttpMethods(config.getExposureConfiguration()
                .forDomainType(Country.class), unsupportedActions);

        disableHttpMethods(config.getExposureConfiguration()
                .forDomainType(State.class), unsupportedActions);

        disableHttpMethods(config.getExposureConfiguration()
                .forDomainType(Order.class), unsupportedActions);

        exposePrimaryIds(config);

        // config.getBasePath() read value from @Value(${spring.data.rest.base-path})
        cors.addMapping(config.getBasePath() + "/**").allowedOrigins(this.allowedOrigins);
    }

    private void disableHttpMethods(ExposureConfigurer config, HttpMethod[] unsupportedActions) {
        config
                .withItemExposure(((metdata, httpMethods) -> httpMethods.disable(unsupportedActions)))
                .withCollectionExposure(((metdata, httpMethods) -> httpMethods.disable(unsupportedActions)));
    }

    private void exposePrimaryIds(RepositoryRestConfiguration config) {
        Set<EntityType<?>> entities = this.entityManager.getMetamodel().getEntities();

        List<Class> entityClasses = new ArrayList<>();

        for(EntityType entity : entities) {
            entityClasses.add(entity.getJavaType());
        }

        Class[] domainTypes = entityClasses.toArray(new Class[0]);
        config.exposeIdsFor(domainTypes);
    }

}
