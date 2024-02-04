package com.publisher.publisherasap.testController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.publisher.publisherasap.QueueSender;


@RestController
@RequestMapping("/teste")
public class TesteController {
	
	@Autowired
	private QueueSender queueSender;
	
	@GetMapping
	public String send() {
		queueSender.sendToCreate("Testando!!!");
		return "Testando...";
	}
	
}
