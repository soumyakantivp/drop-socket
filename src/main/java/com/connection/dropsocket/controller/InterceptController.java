package com.connection.dropsocket.controller;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

@Controller
public class InterceptController {
	private String challenge;
	
	@Autowired
	private  RestTemplate restTemplate;
	
	@GetMapping("/webhook")
	public ResponseEntity<String> verify(@RequestParam String challenge) {
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.clear();
		responseHeaders.set("Content-Type", "text/plain");
		responseHeaders.set("X-Content-Type-Options", "nosniff");
		this.challenge = challenge;
		return ResponseEntity.ok().headers(responseHeaders).body(challenge);
	}

	@PostMapping(value = "/webhook", consumes = "application/json")
	public ResponseEntity<String> updatePerson(@RequestBody String payload) {
		//ResponseEntity r = new ResponseEntity(HttpStatus.OK);
		try {
			String url = "https://sokt.io/c/app/qy41HjusBSyHrbyLJ3xe/ep";
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            headers.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
            HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);

            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET,entity,String.class);

            System.out.println(payload);
            return response;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
		return null;
		
	}
}
