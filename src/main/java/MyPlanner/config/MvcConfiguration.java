package MyPlanner.config;

import MyPlanner.api.CanvasApiHIST;
import MyPlanner.interfaces.CanvasApiInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.social.oauth2.OAuth2Template;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@ComponentScan(basePackages="MyPlanner")
@PropertySource("/WEB-INF/mp.properties")
@EnableWebMvc
public class MvcConfiguration extends WebMvcConfigurerAdapter{
    @Autowired
    Environment env;

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer(){
        return new PropertySourcesPlaceholderConfigurer();
    }

	@Bean
	public ViewResolver getViewResolver(){
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/WEB-INF/views/");
		resolver.setSuffix(".jsp");
		return resolver;
	}

    @Bean
    public RestTemplate restTemplate(){
        RestTemplate restTemplate = new RestTemplate();

        return restTemplate;
    }

    @Bean
    public CanvasApiInterface canvasApi(){
        // TODO: Denne går kunn mot HIST.
        CanvasApiInterface canvasApi = new CanvasApiHIST();

        return canvasApi;
    }

    @Bean
    public OAuth2Template oAuth2Template(){
        OAuth2Template oAuth2Template = new OAuth2Template(env.getProperty("client.id"), env.getProperty("client.secret"),
                env.getProperty("provider.authorizeUrl"), env.getProperty("provider.accessTokenUrl"));

        return  oAuth2Template;
    }
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
	}

	
}
