package com.evolution.config.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("evolution")
public class EvolutionApiProperty {

	private String[] origens;
	private boolean enableHttps;
	private boolean enableCors;

	public String[] getOrigens() {
		return origens;
	}

	public void setOrigens(String[] origens) {
		this.origens = origens;
	}

	public boolean isEnableHttps() {
		return enableHttps;
	}

	public void setEnableHttps(boolean enableHttps) {
		this.enableHttps = enableHttps;
	}

	public boolean isEnableCors() {
		return enableCors;
	}

	public void setEnableCors(boolean enableCors) {
		this.enableCors = enableCors;
	}

}
