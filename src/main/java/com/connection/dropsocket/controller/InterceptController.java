package com.connection.dropsocket.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InterceptController {
	@GetMapping("/webhook")
	public ResponseEntity<String> usingResponseEntityBuilderAndHttpHeaders(@RequestParam String challenge) {
	    HttpHeaders responseHeaders = new HttpHeaders();
	    responseHeaders.clear();
	    responseHeaders.set("Content-Type", 
	      "text/plain");
	    responseHeaders.set("X-Content-Type-Options", 
	  	      "nosniff");

	    return ResponseEntity.ok()
	      .headers(responseHeaders)
	      .body(challenge);
	}
}
