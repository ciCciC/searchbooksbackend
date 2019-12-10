package com.koray.searchbackend;

import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.Settings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;

import java.util.Map;

@SpringBootApplication
public class SearchbackendApplication implements CommandLineRunner {

    @Qualifier("elasticsearchTemplate")
    @Autowired
    private ElasticsearchTemplate es;

    public static void main(String[] args) {
        SpringApplication.run(SearchbackendApplication.class, args);
    }

    @Override
    public void run(String... args) {
        printElasticSearchInfo();
    }

    //print elastic search details
    private void printElasticSearchInfo() {
        System.out.println("--ElasticSearch--");
        Client client = es.getClient();
        Map<String, Settings> asMap = client.settings().getAsGroups();

        asMap.forEach((k, v) -> {
            System.out.println(k + " = " + v);
        });
        System.out.println("--ElasticSearch--");
    }
}
