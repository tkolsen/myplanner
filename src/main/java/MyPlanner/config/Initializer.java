package MyPlanner.config;

import MyPlanner.model.LoginInfo;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.HashMap;

public class Initializer implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.register(MvcConfiguration.class);

        servletContext.addListener(new ContextLoaderListener(context));
        servletContext.addListener(new HttpSessionListener() {
            @Override
            public void sessionCreated(HttpSessionEvent httpSessionEvent) {

            }

            @Override
            public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
                System.out.println("Session destroyed.");
                // TODO: Add this back in before deployment
                /*LoginInfo loginInfo = (LoginInfo)httpSessionEvent.getSession().getAttribute("loginInfo");
                if(loginInfo != null) {
                    RestTemplate restTemplate = new RestTemplate();
                    String url = "https://canvas.instructure.com/login/oauth2/token";
                    HttpHeaders headers = new HttpHeaders();
                    if(loginInfo.getAccessToken() != null) {
                        headers.set("Authorization", "Bearer " + loginInfo.getAccessToken());
                        HttpEntity requestEntity = new HttpEntity(headers);
                        restTemplate.exchange(url, HttpMethod.DELETE, requestEntity, Object.class, new HashMap<String, String>());
                    }
                }*/
            }
        });

        ServletRegistration.Dynamic dispatcher = servletContext.addServlet("dispatcher", new DispatcherServlet(context));

        dispatcher.addMapping("/");
        dispatcher.setLoadOnStartup(1);
    }
}
