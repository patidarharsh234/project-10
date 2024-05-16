package jwtblacklistprogress;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//@Configuration
public class Web implements WebMvcConfigurer{
	
	//Me-->implements WebMvcConfigurer //jis class me impliment ho.
	
	@Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new TokenBlacklistInterceptorMe()).addPathPatterns("/**");
    }

}
