
package ci.esatic.security;

import ci.esatic.services.UserDetailServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.sql.DataSource;

import java.io.IOException;
import java.util.Set;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private UserDetailServiceImpl userDetailServiceImpl;

    public SecurityConfig(UserDetailServiceImpl userDetailServiceImpl) {
        this.userDetailServiceImpl = userDetailServiceImpl;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .formLogin(login->login.loginPage("/login").defaultSuccessUrl("/", true)
                        .successHandler(new AuthenticationSuccessHandler() {
                            @Override
                            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                                                Authentication authentication) throws IOException, ServletException {

                                Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
                                System.out.println("Roles: " + roles);

                                if (roles.contains("ROLE_ADMIN")) {
                                    System.out.println("Redirecting to /admin");
                                    response.sendRedirect("/admin");
                                } else if (roles.contains("ROLE_ENSEIGNANT")) {
                                    System.out.println("Redirecting to /enseignant");
                                    response.sendRedirect("/enseignant");
                                } else if (roles.contains("ROLE_ETUDIANT")) {
                                    System.out.println("Redirecting to /etudiant");
                                    response.sendRedirect("/etudiant");
                                } else {
                                    System.out.println("Redirecting to /");
                                    response.sendRedirect("/");
                                }
                            }
                        })
                        .failureUrl("/login?error=true")
                        .permitAll())
                .logout(LogoutConfigurer::permitAll)
                .authorizeHttpRequests(autorize -> autorize.requestMatchers("/admin/**").hasRole("ADMIN"))
                .authorizeHttpRequests(autorize -> autorize.requestMatchers("/enseignant/**").hasRole("ENSEIGNANT"))
                .authorizeHttpRequests(autorize -> autorize.requestMatchers("/etudiant/**").hasRole("ETUDIANT"))
                .authorizeHttpRequests(autorize ->autorize.requestMatchers("/webjars/**", "/img/**", "/css/**", "/js/**", "/fonts/**", "/plugins/**", "/uploads/**").permitAll())
                .authorizeHttpRequests(autorize -> autorize.anyRequest().authenticated())
                .exceptionHandling(ex -> ex.accessDeniedPage("/notAuthorize")
                ).userDetailsService(userDetailServiceImpl)
                .build();

        // .authorizeHttpRequests(autorize -> autorize.requestMatchers("/").permitAll()
    }
}
