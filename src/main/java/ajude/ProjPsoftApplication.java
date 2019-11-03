package ajude;

import java.util.Date;
import java.util.GregorianCalendar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

import ajude.filter.Filter;

@SpringBootApplication
public class ProjPsoftApplication {
	
	@Bean
	public FilterRegistrationBean<Filter> filter(){
		FilterRegistrationBean<Filter> fil = new FilterRegistrationBean<Filter>();
		fil.setFilter(new Filter());
		fil.addUrlPatterns(""); //rotas que precisam ser autenticadas
		return fil;
	}

	public static void main(String[] args) {
		SpringApplication.run(ProjPsoftApplication.class, args);
	}

}
