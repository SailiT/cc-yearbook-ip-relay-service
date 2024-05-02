package com.octanner.yearbook.ccyearbookiprelayservice;

import com.octanner.yearbook.ccyearbookiprelayservice.config.RelayProperties;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@AllArgsConstructor
@RestController
@Slf4j
public class RelayController {

    private final RestTemplate restTemplate;
    private final RelayProperties relayProperties;

    @PostMapping("/relay")
    public ResponseEntity<String> relayPrecisionProcoRequest(@RequestBody String body) {
        String url = relayProperties.getUrl();
        try  {
            HttpEntity<String> requestEntity = new HttpEntity<>(body, createHeaders(relayProperties.getBearerToken()));
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
            log.info("Received response with status code {}", response.getStatusCode());
            return response;
        }
      catch(RestClientException e){
          log.error("Error occurred while sending request to {}", url, e);
          return new ResponseEntity<>("Failed to relay request", HttpStatus.INTERNAL_SERVER_ERROR);
      }
    }

    private HttpHeaders createHeaders(String bearerToken) {
        return new HttpHeaders() {{
            String authHeader = "Bearer " + bearerToken;
            set("Authorization", authHeader);
        }};
    }
}
