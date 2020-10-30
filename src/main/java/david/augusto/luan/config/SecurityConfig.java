package david.augusto.luan.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		// atraves desse metodo a gnt inicia as configurações referente 
		// ao que vai ter acesso ou nao na nossa aplicacao
		http.authorizeRequests()
		.antMatchers("/")
		.permitAll() // este metodo vai tornar public essas uri q eu colocar seguidas por ele
		.anyRequest()
		.authenticated();
	}

}
