package com.jobis.config.security;

import com.jobis.domain.code.AuthType;
import com.jobis.exception.AuthenticationException;
import com.jobis.exception.AuthenticationException.AuthenticationExceptionCode;
import com.jobis.web.dto.response.TokenResponseDTO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

@Getter
@Component
public class TokenProvider {

  private static final String AUTHORITIES_KEY = "auth";
  private static final String BEARER_TYPE = "Bearer";

  private final long ACCESS_TOKEN_EXPIRE_TIME;
  private final long REFRESH_TOKEN_EXPIRE_TIME;

  private final Key key;

  public TokenProvider(@Value("${jwt.secret}") String secretKey ,
      @Value("${jwt.access-token-expire-time}") long accessTime,
      @Value("${jwt.refresh-token-expire-time}") long refreshTime) {
    this.ACCESS_TOKEN_EXPIRE_TIME = accessTime;
    this.REFRESH_TOKEN_EXPIRE_TIME = refreshTime;
    byte[] keyBytes = Decoders.BASE64.decode(secretKey);
    this.key = Keys.hmacShaKeyFor(keyBytes);
  }

  public TokenResponseDTO createToken(LoginUser loginUser) {
    String authorities = loginUser.getAuthorities().stream()
        .map(GrantedAuthority::getAuthority)
        .collect(Collectors.joining(","));

    Date now = new Date();
    String accessToken = Jwts.builder()
        .claim("email", loginUser.getEmail())  // ?????? ?????? ?????? ??????
        .claim("oauthId", loginUser.getOauthId())
        .claim("userId", loginUser.getUserId())
        .claim("nickName", loginUser.getNickName())
        .claim(AUTHORITIES_KEY, authorities)
        .setIssuedAt(now) // ?????? ?????? ??????
        .setExpiration(new Date(now.getTime() + ACCESS_TOKEN_EXPIRE_TIME)) // ?????? ????????????
        .signWith(key, SignatureAlgorithm.HS512) // ?????? ???????????? ??????
        .compact();

    String refreshToken = Jwts.builder()
        .setExpiration(new Date(now.getTime() + REFRESH_TOKEN_EXPIRE_TIME)) // ?????? ????????????
        .signWith(key, SignatureAlgorithm.HS512) // ?????? ???????????? ??????
        .compact();

    return new TokenResponseDTO(accessToken, refreshToken, loginUser.getAuthType());
  }

  public Authentication getAuthentication(String accessToken) {
    Claims claims = parseClaims(accessToken);
    if (claims.get(AUTHORITIES_KEY) == null) {
      throw new RuntimeException("?????? ????????? ?????? ???????????????.");
    }
    return new UsernamePasswordAuthenticationToken(
        new LoginUser(getUser(claims)),
        "",
        getAuthorities(claims)
    );
  }

  private com.jobis.domain.entity.User getUser(Claims claims) {
    return com.jobis.domain.entity.User.builder()
        .email(claims.get("email", String.class))
        .oauthId(claims.get("oauthId", String.class))
        .id(claims.get("userId", Long.class))
        .nickName(claims.get("nickName", String.class))
        .authType(AuthType.valueOf(claims.get("auth", String.class)))
        .oauthProviderType(null)
        .build();
  }

  private Collection<? extends GrantedAuthority> getAuthorities(Claims claims) {
    return Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
        .map(SimpleGrantedAuthority::new)
        .collect(Collectors.toSet());
  }

  public boolean validateToken(String token) {
    try {
      Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
      return true;
    } catch (Exception e) {
      throw new AuthenticationException(AuthenticationExceptionCode.INVALID_JWT_TOKEN);
    }
//    catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
////      log.info("wrong jwt signature");
//    } catch (ExpiredJwtException e) {
////      log.info("expired jwt token");
//    } catch (UnsupportedJwtException e) {
////      log.info("unsupported jwt token");
//    } catch (IllegalArgumentException e) {
////      log.info("wrong jwt token");
//    }
//    return false;
  }

  private Claims parseClaims(String accessToken) {
    try {
      return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
    } catch (ExpiredJwtException e) { // ????????? ????????? ????????? ?????? ????????? ???
      return e.getClaims();
    }
  }

}
