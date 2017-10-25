package com.aribanilia.vaadin;

        import com.aribanilia.vaadin.loader.Loader;
        import org.springframework.boot.SpringApplication;
        import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class VaadinApplication {

    public static void main(String[] args) {
        Loader.load();
        SpringApplication.run(VaadinApplication.class, args);
    }
}
