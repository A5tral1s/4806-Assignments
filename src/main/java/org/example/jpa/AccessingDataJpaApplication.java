package org.example.jpa;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URI;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tools.jackson.databind.ObjectMapper;

@SpringBootApplication
public class AccessingDataJpaApplication {

    public static void main(String[] args) throws IOException, InterruptedException {
        String filePath = "src/main/java/org/example/jpa/setup2.txt";
        SpringApplication.run(AccessingDataJpaApplication.class, args);

        List<String> lines = Files.readAllLines(Path.of(filePath));

        try (HttpClient client = HttpClient.newHttpClient()) {

            for (String line : lines) {
                if (line.isBlank() || line.trim().startsWith("#")) continue;
                System.out.println("Running: " + line);
                executeCurl(client, line.trim());
            }
        }
    }

    private static void executeCurl(HttpClient client, String curl) throws IOException, InterruptedException {
        String method = "GET";
        String url = "";
        String body = null;
        java.net.http.HttpRequest.Builder requestBuilder = HttpRequest.newBuilder();

        String[] parts = curl.split("\\s+");
        for (int i = 0; i < parts.length; i++) {
            switch (parts[i]) {
                case "-X", "--request" -> method = parts[++i];
                case "-H", "--header" -> {
                    String[] header = parts[++i].replace("\"", "").split(":", 2);
                    requestBuilder.header(header[0].trim(), header[1].trim());
                }
                case "-d", "--data", "--data-raw" -> body = parts[++i].replaceAll("^['\"]|['\"]$", "");
                default -> {
                    if (parts[i].startsWith("http://") || parts[i].startsWith("https://")) {
                        url = parts[i];
                    }
                }
            }
        }

        requestBuilder.uri(URI.create(url));
        if (body != null) {
            requestBuilder.method(method, HttpRequest.BodyPublishers.ofString(body));
        } else {
            requestBuilder.method(method, HttpRequest.BodyPublishers.noBody());
        }

        HttpResponse<String> response = client.send(requestBuilder.build(), HttpResponse.BodyHandlers.ofString());
        System.out.println("Status: " + response.statusCode());
        System.out.println("Response: " + response.body() + "\n-----------------------------------");
    }
}