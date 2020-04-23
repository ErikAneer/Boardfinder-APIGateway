package Boardfinder.APIgateway.Security;

import Boardfinder.APIgateway.Configuration.CustomCorsFilter;
import Boardfinder.APIgateway.Service.ActiveTokenService;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;

@EnableWebSecurity
public class SecurityTokenConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtConfig jwtConfig;

    @Autowired
    private ActiveTokenService tokenService;

    @Autowired
    private CustomCorsFilter myCorsFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.addFilterBefore(myCorsFilter, ChannelProcessingFilter.class);
        http
                // check if possible to remove.        
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/boardfinder/auth/**").permitAll()
                .antMatchers(
                        "/boardfinder/snowboards/**",
                        "/boardfinder/techdetails/**",
                        "/boardfinder/shoesizes/**",
                        "/boardfinder/ridingterrain/**",
                        "/boardfinder/riderlevel/**",
                        "/boardfinder/promotion/**",
                        "/boardfinder/users/**"
                ).permitAll()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling().authenticationEntryPoint((req, rsp, e) -> rsp.sendError(HttpServletResponse.SC_UNAUTHORIZED))
                .and()
                .addFilterAfter(new JwtTokenAuthenticationFilter(jwtConfig, tokenService), UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers("/boardfinder/displayedboards/**", "/boardfinder/boardsearches/**").hasRole("ADMIN")
                .anyRequest().authenticated();
    }

    @Bean
    public JwtConfig jwtConfig() {
        return new JwtConfig();
    }
}
