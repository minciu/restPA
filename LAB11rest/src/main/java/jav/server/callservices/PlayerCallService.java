package jav.server.callservices;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jav.server.Gomoku.Player;
import jav.rest.trans.PlayerTrans;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
public class PlayerCallService {
    private final String URI = "http://localhost:8080/player";

    @GetMapping("/call_players")
    public List<PlayerTrans> getPlayers() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<List<PlayerTrans>> response = restTemplate.exchange(
                URI, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<PlayerTrans>>() {
                });
        return response.getBody();
    }

    @PostMapping("/call_players")
    public ResponseEntity<PlayerTrans> createPlayer(Player player) {
        ResponseEntity<PlayerTrans> playerTrans = null;
        try {
            RestTemplate restTemplate = new RestTemplate();

            HttpHeaders header = new HttpHeaders();
            header.setContentType(MediaType.APPLICATION_JSON);

            Map<String, String> bodyParams = new HashMap<>();
            bodyParams.put("name", player.getName());

            String bodyData = new ObjectMapper().writeValueAsString(bodyParams);

            HttpEntity<String> request = new HttpEntity<>(bodyData, header);

            playerTrans = restTemplate.postForEntity(URI, request, PlayerTrans.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return playerTrans;
    }
}
