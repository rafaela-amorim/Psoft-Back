package ajude;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

import ajude.filter.Filter;

@SpringBootApplication
public class ProjPsoftApplication {
	
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