package com.evolution.resource.seguranca;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/servidores")
public class ServidorResource {

	@GetMapping
	public Object lista(HttpServletRequest request) {
		return "Servidor Operando Normalmente.";
	}

}
