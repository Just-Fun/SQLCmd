package ua.com.juja.serzh.sqlcmd;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ua.com.juja.serzh.sqlcmd.service.Service;

public class App {
    public static void main(String[] args) {
//        ConfigurableApplicationContext context = new ClassPathXmlApplicationContext("/WEB-INF/Spring-Module.xml");
        ApplicationContext context = new ClassPathXmlApplicationContext("/application-context.xml");

        Service service = (Service) context.getBean("service");
        System.out.println(service);
    }
}