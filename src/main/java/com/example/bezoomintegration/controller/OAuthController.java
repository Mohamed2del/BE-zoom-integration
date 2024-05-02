package com.example.bezoomintegration.controller;

import com.example.bezoomintegration.entites.Meeting;
import com.example.bezoomintegration.repositories.MeetingRepository;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;


@Controller
@CrossOrigin("http://localhost:4200/")
public class OAuthController {

    private final MeetingRepository meetingRepository;

    public OAuthController(MeetingRepository meetingRepository, MeetingRepository meetingRepository1) {
        this.meetingRepository = meetingRepository1;
    }

    @GetMapping("/oauth2/authorization/zoom")
    public ResponseEntity<String> getAuthorizationUrl() {
        return ResponseEntity.ok("https://zoom.us/oauth/authorize?client_id=3d3uuHV2QwaLtG9ll8VpNQ&response_type=code&redirect_uri=http%3A%2F%2Flocalhost%3A4200");
    }

    @PostMapping("/api/zoom/oauth")
    public ResponseEntity<?> handleZoomAuth(@RequestBody Map<String, String> payload) {
        String code = payload.get("code");
        RestTemplate restTemplate = new RestTemplate();
        String tokenUrl = "https://zoom.us/oauth/token";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setBasicAuth("3d3uuHV2QwaLtG9ll8VpNQ", "94xMSS5D4qMjxlrxd35aezStR1OkQMkI"); // Ensure these are correct and consider moving them to environment variables for security

        MultiValueMap<String, String> map= new LinkedMultiValueMap<>();
        map.add("grant_type", "authorization_code");
        map.add("code", code);
        map.add("redirect_uri", "http://localhost:4200/zoom-auth");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(tokenUrl, request, String.class);
            return ResponseEntity.ok(response.getBody());
        } catch (HttpClientErrorException ex) {
            ex.printStackTrace();
            // Logging the status code and response body can help in further diagnosing the issue
            return ResponseEntity.status(ex.getStatusCode()).body(ex.getResponseBodyAsString());
        }
    }

    @PostMapping("/api/zoom/meetings")
    public ResponseEntity<?> createMeeting(@RequestBody Map<String, Object> meetingDetails, @RequestHeader("Authorization") String authToken) {
        String baseUrl = "https://api.zoom.us/v2/users/me/meetings";
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(authToken.replace("Bearer ", ""));

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(meetingDetails, headers);
        try {
            ResponseEntity<Meeting> response = restTemplate.postForEntity(baseUrl, entity, Meeting.class);
            meetingRepository.save(response.getBody());
                return ResponseEntity.ok(response.getBody());
        } catch (HttpClientErrorException ex) {
            ex.printStackTrace();
            return ResponseEntity.status(ex.getStatusCode()).body(ex.getResponseBodyAsString());
        }
    }

    @GetMapping("/get-meetings")
    public ResponseEntity<?> getMeetings(@RequestParam String email) {
        return ResponseEntity.ok(meetingRepository.findByHostEmailLike(email));
    }

}