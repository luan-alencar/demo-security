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
				.antMatchers("/webjars/**", "/css/**", "/image/**","/js/**").permitAll()
				.antMatchers("/", "/home")
				.permitAll() // este metodo vai tornar public essas uri q eu colocar seguidas por ele
				.anyRequest().authenticated()
				.and()
					.formLogin()
					.loginPage("/login")
					.defaultSuccessUrl("/", true)
					.failureUrl("/login-error") // indica qual a URI em caso de falha
					.permitAll() // todo usuario mesmo o que n esta logado ele tem q ter permissao para acessar a pag d login e de erro
				.and() // parte referente ao logout
					.logout() // vai dizer para onde este metodo tem que me direcionar apos o logout
					.logoutSuccessUrl("/");
		
	}

}
