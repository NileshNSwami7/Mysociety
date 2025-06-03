package com.mysociety.security;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix="security")
public class PathSecuritProperties {

	private  List<String> publicpaths;

	public PathSecuritProperties(List<String> publicpaths) {
		super();
		this.publicpaths = publicpaths;
	}

	public List<String> getPublicpaths() {
		return publicpaths;
	}

	public void setPublicpaths(List<String> publicpaths) {
		this.publicpaths = publicpaths;
	}
	
	
	
}
