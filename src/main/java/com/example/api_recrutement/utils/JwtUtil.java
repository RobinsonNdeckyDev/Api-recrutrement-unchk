package com.example.api_recrutement.utils;

import io.jsonwebtoken.*;
// import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

//import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {

    private String SECRET_KEY = "ae5ba1871bc1a99f10aa1d03f9c0d0ad6764e0b02189ad5086a86ba41d05825394c32abd96c874464f9181a91aaffbc279c1a5fb7f52f30ec235408e0d8d776da60af53ce3499c229fe5634e992c0a1e90d1f642e7a28fd97473903aa0ee228c6a3efc0626f827cc80a91d950416e6f5cf6cfacfc1e3d027f554fbfc4e4acf4baf7574872a3dce2a1fed606772f04d500b92ac318e6e7185f1b8510ed09cd00e545ae7e7920c244a8ce4cde76fd48b5e25163de527e2003afc363b3ba3dd12b096a159104871ace155bd87610518f7e3ea514efd84c1b2417aa2843f62fe72ead2d35de29b5a9643a3dc478df671059e826d1156bec73155f6db2d5a14e98c53";

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userDetails.getUsername());
    }

    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

}
