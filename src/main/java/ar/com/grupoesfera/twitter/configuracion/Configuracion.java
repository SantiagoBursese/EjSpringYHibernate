package ar.com.grupoesfera.twitter.configuracion;

import ar.com.grupoesfera.twitter.recursos.TwitterApi;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.rs.security.cors.CrossOriginResourceSharingFilter;
import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.List;

@Configuration
public class Configuracion {

    @Inject
    private TwitterApi twitterApi;
    
    @Bean(destroyMethod = "shutdown")
    public SpringBus cxf() {
        return new SpringBus();
    }

    @Bean(destroyMethod = "destroy")
    @DependsOn("cxf")
    public Server jaxRsServer() {
        final JAXRSServerFactoryBean factory = new JAXRSServerFactoryBean();

        List<Object> providers = Arrays.asList(new JacksonJsonProvider(), new CrossOriginResourceSharingFilter());

        factory.setServiceBean(this.twitterApi);
        factory.setBus(cxf());
        factory.setProviders(providers);
        factory.setAddress("/");

        return factory.create();
    }
    
}
