package david.augusto.luan.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import david.augusto.luan.domain.PerfilTipo;
import david.augusto.luan.service.UsuarioService;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private static final String ADMIN = PerfilTipo.ADMIN.getDesc();
	private static final String MEDICO = PerfilTipo.MEDICO.getDesc();
	private static final String PACIENTE = PerfilTipo.PACIENTE.getDesc();
	
	@Autowired
	public UsuarioService service;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(service).passwordEncoder(new BCryptPasswordEncoder()); // a criptografia
																						// BCryptPasswordEncoder e a
																						// mais segura para senha
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// atraves desse metodo a gnt inicia as configurações referente
		// ao que vai ter acesso ou nao na nossa aplicacao
		http.authorizeRequests()
				// acessos publicos liberados
				.antMatchers("/webjars/**", "/css/**", "/image/**", "/js/**").permitAll()
				.antMatchers("/", "/home").permitAll() // este metodo vai tornar public essas uri q eu colocar seguidas
													   // por ele
				
				// acessos privados admin
				.antMatchers("/u/editar/senha", "/u/confirmar/senha").hasAuthority(ADMIN)
				.antMatchers("/u/**").hasAuthority(ADMIN)
				
				// acessos privados medicos
				.antMatchers("/medicos/dados", "/medicos/salvar","/medicos/editar").hasAnyAuthority(MEDICO, ADMIN)
				.antMatchers("/medicos/**").hasAuthority(MEDICO)
				
				// acessos privados pacientes
				.antMatchers("/pacientes/**").hasAuthority(PACIENTE)
				
				// acessos privados especialidades
				.antMatchers("/especialidades/datatables/server/medico/*").hasAnyAuthority(MEDICO,ADMIN)
				.antMatchers("/especialidades/titulo").hasAnyAuthority(MEDICO,ADMIN)
				.antMatchers("/especialidades/**").hasAuthority(ADMIN)
				
				.anyRequest().authenticated()
			.and()
				.formLogin()
				.loginPage("/login")
				.defaultSuccessUrl("/", true)
				.failureUrl("/login-error") // indica qual a URI em caso de falha
				.permitAll() // todo usuario mesmo o que n esta logado ele tem q ter permissao para acessar a
																						// pag d login e de erro
			.and() // parte referente ao logout
				.logout() // vai dizer para onde este metodo tem que me direcionar apos o logout
				.logoutSuccessUrl("/")
			.and()
				.exceptionHandling()
				.accessDeniedPage("/acesso-negado");

	}

}
