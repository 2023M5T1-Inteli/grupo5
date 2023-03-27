/**
* Configuration class for Spring Web MVC.
*/
package br.edu.inteli.cc.m5.maverick.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
* Configuration class that implements {@link WebMvcConfigurer} to customize
* the behavior of Spring Web MVC.
*/
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    /**
    * Adds Cross-Origin Resource Sharing (CORS) mappings to the Spring Web MVC
    * configuration.
    *
    * @param registry the CORS registry to which mappings can be added
    */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedMethods("*")
                .allowedHeaders("*")
                .allowedOrigins("*");
    }
}