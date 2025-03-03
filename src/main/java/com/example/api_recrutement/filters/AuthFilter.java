package com.example.api_recrutement.filters;

import com.example.api_recrutement.utils.JwtUtil;
//import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
//import jakarta.servlet.ServletRequest;
//import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
public class AuthFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(AuthFilter.class);

    private final UserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;

    // Liste des routes publiques
    private static final List<String> PUBLIC_ROUTES = Arrays.asList("/api/auth/register", "/api/auth/login");

    public AuthFilter(UserDetailsService userDetailsService, JwtUtil jwtUtil) {
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        final String authorizationHeader = request.getHeader("Authorization");

        // Vérifie si la requête est sur une route publique
        if (isPublicRoute(request.getRequestURI())) {
            logger.info("Bypass JWT filter for public route: " + request.getRequestURI());
            chain.doFilter(request, response);  // Ignore ce filtre pour ces routes
            return;
        }

        String username = null;
        String jwt = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
            username = jwtUtil.extractUsername(jwt);
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            if (jwtUtil.validateToken(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        chain.doFilter(request, response);
    }

    // Vérifier si la route est publique
    private boolean isPublicRoute(String uri) {
        return PUBLIC_ROUTES.stream().anyMatch(uri::startsWith);
    }

    // Vérifier si la route nécessite un rôle spécifique (ex. ADMIN)
    private boolean isAdminRoute(String uri) {
        // Implémentez ici la logique pour valider les routes réservées aux admins
        return uri.startsWith("/api/admin");
    }
}



//        String username = null;
//        String jwt = null;
//
//        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
//            jwt = authorizationHeader.substring(7);
//            username = jwtUtil.extractUsername(jwt);
//        }
//
//        // Si le token est présent et qu'aucune authentification n'est en place
//        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
//
//            if (jwtUtil.validateToken(jwt, String.valueOf(userDetails))) {
//                // Extraire le rôle du token pour validation
//                String role = jwtUtil.extractRole(jwt);
//
//                // Si vous souhaitez restreindre certains accès selon les rôles, vous pouvez valider ici
//                if ("ADMIN".equals(role) && isAdminRoute(request.getRequestURI())) {
//                    logger.info("Admin access granted to: " + username);
//                }
//
//                // Authentifier l'utilisateur
//                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
//                        userDetails, null, userDetails.getAuthorities());
//                usernamePasswordAuthenticationToken
//                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
//            } else {
//                logger.warn("Token invalide pour l'utilisateur: " + username);
//            }
//        } else {
//            logger.warn("Aucun token ou nom d'utilisateur dans l'en-tête Authorization");
//        }
//        chain.doFilter(request, response);
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
//            throws IOException, ServletException {
//        HttpServletRequest httpRequest = (HttpServletRequest) request;
//        HttpServletResponse httpResponse = (HttpServletResponse) response;
//
//        String path = httpRequest.getRequestURI();
//
//        // Exclure les endpoints publics de la vérification JWT
//        if (path.startsWith("/swagger-ui") || path.startsWith("/v3/api-docs") || path.equals("/")) {
//            filterChain.doFilter(request, response);
//            return;
//        }
//
//        // Vérification du token JWT pour les autres endpoints
//        String token = httpRequest.getHeader("Authorization");
//
//        if (token != null && token.startsWith("Bearer ")) {
//            token = token.substring(7);  // Supprimer "Bearer " du token
//            // Valider le token JWT ici
//            filterChain.doFilter(request, response);
//        } else {
//            // Si aucun token n'est présent, renvoyer 401 Unauthorized
//            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//            httpResponse.getWriter().write("Unauthorized");
//        }
//    }
