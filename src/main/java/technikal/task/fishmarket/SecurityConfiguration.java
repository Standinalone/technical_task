package technikal.task.fishmarket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
	@Autowired
	DataSource dataSource;
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
				.csrf(csrf -> csrf.ignoringRequestMatchers("*/create", "*/delete"))
				.authorizeHttpRequests((requests) -> requests
								.requestMatchers("/fish/create", "/fish/delete").hasRole("ADMIN")
								.requestMatchers("/", "/fish", "/images/*").permitAll()
                                .anyRequest().authenticated()
				)
				.formLogin((form) -> form
								.loginProcessingUrl("/login")
								.defaultSuccessUrl("/fish")
								.permitAll()
				)
				.logout((logout) -> logout
						.permitAll()
						.logoutSuccessUrl("/fish")
				);
		return http.build();
	}
	
	@Bean
	public UserDetailsService userDetailsService() {
		return new JdbcUserDetailsManager(dataSource);
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}
}
