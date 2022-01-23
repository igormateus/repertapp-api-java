package io.github.igormateus.repertapp.common;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

@ConfigurationProperties("app.properties")
@Getter
@Setter
public class AppProperties {

    public static class JWT {

        public static class Token {

            public static int expiration = 60;

            public static String secret = "secret"; 
        }
    }
}
