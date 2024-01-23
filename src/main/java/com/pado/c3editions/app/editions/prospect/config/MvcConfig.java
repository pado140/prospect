package com.pado.c3editions.app.editions.prospect.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
//@EnableWebMvc
public class MvcConfig implements WebMvcConfigurer {
	final Environment env;

	public MvcConfig(Environment env) {
		this.env = env;
	}

	@Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
//        super.addResourceHandlers(registry);
		String location=env.getProperty("files.uploads");
		System.out.println(location);

        registry.addResourceHandler("/uploads/**").addResourceLocations("file:/"+location+"/");
//        registry.addResourceHandler("/css/**").addResourceLocations("/css/");
//        registry.addResourceHandler("/js/**").addResourceLocations("/js/");
    }


}
