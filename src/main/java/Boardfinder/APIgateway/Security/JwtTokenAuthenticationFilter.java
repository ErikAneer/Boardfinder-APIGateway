package Boardfinder.APIgateway.Security;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import Boardfinder.APIgateway.Service.ActiveTokenService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import java.nio.file.AccessDeniedException;

/**
 * JWT Filter class that filters incoming http requests. Modified code from Omar
 * El Gabry
 */
public class JwtTokenAuthenticationFilter extends OncePerRequestFilter {

    private final JwtConfig jwtConfig;

    private ActiveTokenService tokenService;

    public JwtTokenAuthenticationFilter(JwtConfig jwtConfig, ActiveTokenService tokenService) {
        this.jwtConfig = jwtConfig;
        this.tokenService = tokenService;
    }

    /**
     * Filters the incoming http request to see if it contains a valid token.
     * Filter method is called if a token is in the request header or if a path
     * that needs to be checked is tried to be accessed that should have a
     * token. Is designed to throw a AccessDeniedException in case no valid
     * token is identified.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        String header = request.getHeader(jwtConfig.getHeader());

        if (header == null || !header.startsWith(jwtConfig.getPrefix())) {
            chain.doFilter(request, response);
            return;
        }

        String token = header.replace(jwtConfig.getPrefix(), "");

        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(jwtConfig.getSecret().getBytes())
                    .parseClaimsJws(token)
                    .getBody();

            String username = claims.getSubject();
            if (username != null) {
                @SuppressWarnings("unchecked")
                List<String> authorities = (List<String>) claims.get("authorities");

                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                        username, null, authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));

                if (!tokenService.checkIfActiveToken(token)) {
                    SecurityContextHolder.clearContext();
                    response.sendError(401, "Valid token does not exist");
                    return;
                } else {
                    SecurityContextHolder.getContext().setAuthentication(auth);
                    chain.doFilter(request, response);
                }
            }
            ;
        } catch (AccessDeniedException e) {
            SecurityContextHolder.clearContext();
            throw new AccessDeniedException("Access denied");
        }
    }
}
