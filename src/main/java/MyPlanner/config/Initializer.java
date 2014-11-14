package MyPlanner.config;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

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
            }
        });

        ServletRegistration.Dynamic dispatcher = servletContext.addServlet("dispatcher", new DispatcherServlet(context));

        dispatcher.addMapping("/");
        dispatcher.setLoadOnStartup(1);
    }
}
