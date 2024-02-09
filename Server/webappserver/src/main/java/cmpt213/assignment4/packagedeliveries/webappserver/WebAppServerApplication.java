package cmpt213.assignment4.packagedeliveries.webappserver;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;


@SpringBootApplication
public class WebAppServerApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(WebAppServerApplication.class).headless(false).run(args);
    }


}
