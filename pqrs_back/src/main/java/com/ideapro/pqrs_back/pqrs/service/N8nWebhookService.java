/* 
package com.ideapro.pqrs_back.pqrs.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Service
public class N8nWebhookService {

    private static final Logger logger = LoggerFactory.getLogger(N8nWebhookService.class);

    private final RestTemplate restTemplate;

    @Value("${n8n.webhook.url}") // URL configurada en application.properties
    private String n8nWebhookUrl;

    @SuppressWarnings("removal")
    public N8nWebhookService(RestTemplateBuilder builder) {
        this.restTemplate = builder
                .setConnectTimeout(Duration.ofSeconds(5))
                .setReadTimeout(Duration.ofSeconds(5))
                .build();
    }

    public void notificarEstadoPQRS(String id, String nombreUsuario, String telefono, String estado) {
        try {
            Map<String, Object> payload = new HashMap<>();
            payload.put("id", id);
            payload.put("nombreUsuario", nombreUsuario);
            payload.put("telefono", telefono);
            payload.put("estado", estado);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Map<String, Object>> request = new HttpEntity<>(payload, headers);

            ResponseEntity<String> response = restTemplate.exchange(
                    n8nWebhookUrl,
                    HttpMethod.POST,
                    request,
                    String.class
            );

            logger.info("✅ Notificación enviada a n8n correctamente: {}", payload);
            logger.info("📩 Respuesta de n8n: {}", response.getBody());

        } catch (Exception e) {
            logger.error("❌ Error al notificar a n8n", e);
        }
    }
}
*/