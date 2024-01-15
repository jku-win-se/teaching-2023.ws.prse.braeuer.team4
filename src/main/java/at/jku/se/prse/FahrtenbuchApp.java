package at.jku.se.prse;

import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class FahrtenbuchApp {

    @Value("${dropbox.oauth.token}")
    private String token;
    public static void main(String[] args) {
        SpringApplication.run(FahrtenbuchApp.class);
    }

    @Bean("dropboxClient")
    public DbxClientV2 dropboxClient() {
        String ACCESS_TOKEN = token;
        DbxRequestConfig config = new DbxRequestConfig("dropbox/java-tutorial");
        DbxClientV2 client = new DbxClientV2(config, ACCESS_TOKEN);
        return client;
    }
}