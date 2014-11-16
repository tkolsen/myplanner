package MyPlanner.config;

import MyPlanner.oauth.OAuth;
import MyPlanner.oauth.OAuthImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@ComponentScan(basePackages="MyPlanner")
//@PropertySource("/WEB-INF/mp.properties")
@EnableWebMvc
public class MvcConfiguration extends WebMvcConfigurerAdapter{
    @Autowired
    Environment env;

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer(){
        return new PropertySourcesPlaceholderConfigurer();
    }

	@Bean
	public InternalResourceViewResolver internalResourceViewResolver(){
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/WEB-INF/views/");
		resolver.setSuffix(".jsp");
		return resolver;
	}
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
	}

    @Bean
    public OAuth oAuth(){
        return new OAuthImpl();
    }

    @Bean
    @Scope(value = "session", proxyMode = ScopedProxyMode.NO)
    public AccessGrant accessGrant(){
        AccessGrant accessGrant = null;
        return accessGrant;
    }
	
}
