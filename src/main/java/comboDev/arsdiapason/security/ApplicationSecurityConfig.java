package comboDev.arsdiapason.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfig {

    private final CustomAuthenticationProvider customAuthenticationProvider;

    public ApplicationSecurityConfig(CustomAuthenticationProvider customAuthenticationProvider) {
        this.customAuthenticationProvider = customAuthenticationProvider;
    }

    // 1. L'equivalente esatto del tuo vecchio web.ignoring()
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers(
                "/",
                "/*.js",
                "/favicon.ico",
                "/api/getRegex",
                "/api/createAccount",
                "/api/confirmAccount",
                "/api/sendResetPasswordEmail",
                "/api/resetPassword"
        );
    }

    @Autowired
    private Environment environment;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        boolean isLocal = environment.acceptsProfiles(Profiles.of("local"));

        http
                // 1. Disabilita il CSRF (e ignora H2 solo se siamo in local)
                .csrf(csrf -> {
                    if (isLocal) {
                        csrf.ignoringRequestMatchers("/h2-console/**");
                    }
                    csrf.disable();
                })
                // 2. Permetti a H2 di caricare i suoi frame (attivo solo in local)
                .headers(headers -> {
                    if (isLocal) {
                        headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin);
                    }
                })
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))

                // 3. Gestione sessioni (IF_REQUIRED per supportare sia la console H2 che l'autenticazione Basic)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                )

                // 4. Regole di autorizzazione
                .authorizeHttpRequests(auth -> {
                    // Permette sempre i preflight CORS
                    auth.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll();

                    // Rotta di login
                    auth.requestMatchers("/api/login").authenticated();

                    // H2 Console accessibile senza filtri SOLO in ambiente local
                    if (isLocal) {
                        auth.requestMatchers("/h2-console/**").permitAll();
                    }

                    // Tutto il resto richiede l'autorità PSICOLOGO
                    auth.anyRequest().hasAuthority("PSICOLOGO");
                })
                .httpBasic(basic -> basic
                        .authenticationEntryPoint(new NoPopupBasicAuthenticationEntryPoint())
                )
                .authenticationProvider(customAuthenticationProvider);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:4200"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type", "X-Requested-With"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}