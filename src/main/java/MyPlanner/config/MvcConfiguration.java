package MyPlanner.config;

import MyPlanner.dao.*;
import MyPlanner.oauth.*;
import MyPlanner.service.CanvasApi;
import MyPlanner.service.CanvasApiImpl;
import MyPlanner.service.LoginInfoRepo;
import MyPlanner.service.LoginInfoRepoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.util.Properties;

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

    @Bean(name="simpleMappingExceptionResolver")
    public SimpleMappingExceptionResolver createSimpleMappingExceptionResolver(){
        SimpleMappingExceptionResolver r = new SimpleMappingExceptionResolver();

        Properties mappings = new Properties();
        mappings.setProperty("NotAuthorizedException", "errors/not-authorized");

        r.setExceptionMappings(mappings);
        //r.setDefaultErrorView("error");
        r.setExceptionAttribute("ex");
        return r;
    }

    @Bean(name="oAuth")
    public OAuth2 oAuth(){
        // TODO: mock oauth
        OAuth2 oAuth = new OAuth2Impl(props());
        //OAuth2 oAuth = new OAuthMock();
        return oAuth;
    }

    @Bean
    public LoginInfoRepo loginInfoRepo(){
        LoginInfoRepo loginInfoRepo = new LoginInfoRepoImpl();
        return loginInfoRepo;
    }

    @Bean(name = "props")
    public CanvasProperties props(){
        CanvasProperties props = new CanvasProperties(env);
        return props;
    }

    @Bean
    public CanvasApi canvasApi(){
        CanvasApi canvasApi = new CanvasApiImpl();
        return canvasApi;
    }

    @Bean
    public UserDao userDao(){
        UserDao userDao = new UserDaoImpl();
        return userDao;
    }

    @Bean
    public CourseDao courseDao(){
        CourseDao courseDao = new CourseDaoImpl();
        return courseDao;
    }

    @Bean
    public ModuleDao moduleDao(){
        ModuleDao moduleDao = new ModuleDaoImpl();
        return moduleDao;
    }

    @Bean
    public UserHasModuleDao userHasModuleDao(){
        UserHasModuleDao userHasModuleDao = new UserHasModuleDaoImpl();
        return userHasModuleDao;
    }

}
