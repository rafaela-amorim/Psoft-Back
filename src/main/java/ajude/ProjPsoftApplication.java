package ajude;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import ajude.filter.Filter;

@SpringBootApplication
public class ProjPsoftApplication {
	
	
	/**
	 * método para habilitar cross origin 
	 * @return
	 */
	@Bean
	public FilterRegistrationBean corsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration().applyPermitDefaultValues();
		config.addAllowedMethod("*");
		source.registerCorsConfiguration("/**", config);
		FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
		bean.setOrder(0);
		return bean;
	}
	
	@Bean
	public FilterRegistrationBean<Filter> filter() {
		FilterRegistrationBean<Filter> filter = new FilterRegistrationBean<Filter>();
		filter.setFilter(new Filter());
		filter.addUrlPatterns("/auth/*"); //rotas que precisam ser autenticadas
		return filter;
	}

	public static void main(String[] args) {
		SpringApplication.run(ProjPsoftApplication.class, args);
	}
}