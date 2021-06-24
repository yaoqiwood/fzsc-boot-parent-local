package org.gwb;

import java.net.UnknownHostException;

import org.apache.catalina.Context;
import org.apache.tomcat.util.scan.StandardJarScanner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
@EnableScheduling
public class GwbLocalApplication {

    // public JeecgApplication() {
    // LoginFrame.getInstance().startLoginFrame();
    // }

    public static void main(String[] args) throws UnknownHostException {
        ConfigurableApplicationContext application = SpringApplication.run(GwbLocalApplication.class, args);
        // Environment env = application.getEnvironment();
        // String ip = InetAddress.getLocalHost().getHostAddress();
        // String port = env.getProperty("server.port");
        // String path = env.getProperty("server.servlet.context-path");
        // log.info("\n----------------------------------------------------------\n\t" +
        // "Application Jeecg-Boot is running! Access URLs:\n\t" +
        // "Local: \t\thttp://localhost:" + port + path + "/\n\t" +
        // "External: \thttp://" + ip + ":" + port + path + "/\n\t" +
        // "Swagger-UI: \t\thttp://" + ip + ":" + port + path + "/doc.html\n" +
        // "----------------------------------------------------------");

    }

    /**
    * tomcat-embed-jasper引用后提示jar找不到的问题
    */
    @Bean
    public TomcatServletWebServerFactory tomcatFactory() {
        return new TomcatServletWebServerFactory() {
            @Override
            protected void postProcessContext(Context context) {
                ((StandardJarScanner) context.getJarScanner()).setScanManifest(false);
            }
        };
    }

}
