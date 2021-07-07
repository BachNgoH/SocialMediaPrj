package com.bachngo.socialmediaprj.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import com.sun.istack.NotNull;

@Component
@EnableConfigurationProperties
@ConfigurationProperties(prefix="app")
public class AppConfig {
	
	@NotNull
	private String url;
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
}
