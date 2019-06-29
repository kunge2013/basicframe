package com.basic.core.birt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
@Configuration
public class ApplicationConfig extends WebMvcConfigurerAdapter {
	@Value("${birt.filepath}")
    private String filepath;
	@Value("${birt.suffix}")
	private String suffix;
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler(suffix+"/**")
			.addResourceLocations(filepath);
		super.addResourceHandlers(registry);
	}
}
