package de.workshops.bookshelf;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.net.URL;

@ConfigurationProperties(prefix = "bshelf")
record SwaggerRecordProperties(String title,
                               Integer size,
                               String version,
                               License license) {
}
record License(String name, URL url) {
}
