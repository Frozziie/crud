package com.cv.crud.configuration;

import javax.sql.DataSource;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity /* Indicamos a Spring que vamos a configurar la seguridad web de nuestra aplicación. Permite las configuraciones personalizadas. */
public class SecurityConfig {

	private final RsaKeyProperties rsaKeys;

	public SecurityConfig(RsaKeyProperties rsaKeys) {
		this.rsaKeys = rsaKeys;
	}

	@Bean
	JwtEncoder jwtEncoder() { /* Bean que se encarga de firmar tokens JWT mediante una clave publica y privada RSA */
		JWK jwk = new RSAKey.Builder(rsaKeys.getPublicKey()).privateKey(rsaKeys.getPrivateKey()).build();
		JWKSource<SecurityContext> jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
		return new NimbusJwtEncoder(jwks);
	}

	@Bean
	JwtDecoder jwtDecoder() { /* Bean que se encarga de validar tokens JWT mediante una clave publica RSA */
		return NimbusJwtDecoder.withPublicKey(rsaKeys.getPublicKey()).build();
	}

	@Bean
	DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("org.postgresql.Driver");
		dataSource.setUrl("jdbc:postgresql://localhost:5432/postgres");
		dataSource.setUsername("postgres");
		dataSource.setPassword("casona");
		return dataSource;
	};
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl();
	}
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http
				.csrf(AbstractHttpConfigurer::disable)
				.authorizeRequests(request -> request.anyRequest().authenticated())
				.httpBasic(withDefaults())
				.oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt) /* Wires a BearerTokenAuthenticationFilter */
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.userDetailsService(userDetailsService())
				.build();
	}
	
}
