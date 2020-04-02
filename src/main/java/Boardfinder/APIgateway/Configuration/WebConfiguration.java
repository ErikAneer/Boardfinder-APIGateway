package Boardfinder.APIgateway.Configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/** 
 * Configuration class for web configuration.
 */

@Configuration
@EnableWebMvc
public class WebConfiguration implements WebMvcConfigurer {


    /** 
     * Method that adds localhost:4200 as only allowed origin for accessing the API Gateway.
     * @param registry 
     */
    @Override
    public void addCorsMappings(final CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins("http://localhost:4200");
    }
}
