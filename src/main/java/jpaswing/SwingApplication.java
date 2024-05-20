package jpaswing;

import jpaswing.ui.MainUI;
import jpaswing.ui.PilotoUI;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import java.awt.*;

@SpringBootApplication
public class SwingApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext context  = new SpringApplicationBuilder(SwingApplication.class)
                .headless(false)
                .web(WebApplicationType.NONE)
                .run(args);

        EventQueue.invokeLater(()  -> {
                MainUI mainUI = context.getBean(MainUI.class);
                mainUI.setVisible(true);
        });
    }
}