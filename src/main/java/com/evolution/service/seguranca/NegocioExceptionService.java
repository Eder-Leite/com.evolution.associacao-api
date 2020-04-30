package com.evolution.service.seguranca;

public class NegocioExceptionService extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public NegocioExceptionService(String msg) {
		super(msg);
	}
	
}
