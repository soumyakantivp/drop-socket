package com.connection.dropsocket.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

@Controller
public class InterceptController {
	@GetMapping("/webhook")
	@ResponseBody
	public ResponseEntity<String> verify(@RequestParam String challenge) {
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.clear();
		responseHeaders.set("Content-Type", "text/plain");
		responseHeaders.set("X-Content-Type-Options", "nosniff");

		return ResponseEntity.ok().headers(responseHeaders).body(challenge);
	}

	@PostMapping(value = "/webhook", consumes = "application/json")
	public String updatePerson(@RequestBody String payload) {
		
		System.out.println("pay: "+payload);
		String url = "https://sokt.io/c/app/qy41HjusBSyHrbyLJ3xe/ep";
		RestTemplate restTemplate = new RestTemplate();
		String result = restTemplate.getForObject(url, String.class);
		System.out.println(result);
		return "redirect:/test";
	}
}
