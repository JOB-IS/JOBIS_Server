package com.catchup.config.security;

import com.catchup.domain.code.AuthorityType;
import com.catchup.web.dto.response.TokenResponseDTO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

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
      @Value("${jwt.refresh-token-expire-time}") long refreshTime
  ) {
    this.ACCESS_TOKEN_EXPIRE_TIME = accessTime;
    this.REFRESH_TOKEN_EXPIRE_TIME = refreshTime;
    byte[] keyBytes = Decoders.BASE64.decode(secretKey);
    this.key = Keys.hmacShaKeyFor(keyBytes);
  }

  protected String createToken(String nickName, AuthorityType authorityType, long tokenValid) {
    Claims claims = Jwts.claims().setSubject(nickName);
    claims.put(AUTHORITIES_KEY, authorityType.getValue());

    Date now = new Date();
    return Jwts.builder()
        .setClaims(claims) // 토큰 발행 유저 정보
        .setIssuedAt(now) // 토큰 발행 시간
        .setExpiration(new Date(now.getTime() + tokenValid)) // 토큰 만료시간
        .signWith(key, SignatureAlgorithm.HS512) // 키와 알고리즘 설정
        .compact();
  }

  public String createAccessToken(String nickName, AuthorityType authorityType) {
    return this.createToken(nickName, authorityType, ACCESS_TOKEN_EXPIRE_TIME);
  }

  public String createRefreshToken(String nickName, AuthorityType authorityType) {
    return this.createToken(nickName, authorityType, REFRESH_TOKEN_EXPIRE_TIME);
  }

//  public String getNickNameByToken(String token) {
//    return this.parseClaims(token).getSubject();
//  }

  public TokenResponseDTO createTokenDTO(String accessToken, String refreshToken) {
    return TokenResponseDTO.builder()
        .accessToken(accessToken)
        .refreshToken(refreshToken)
        .grantType(BEARER_TYPE)
        .build();
  }

  public Authentication getAuthentication(String accessToken) {
    // 토큰 복호화
    Claims claims = parseClaims(accessToken);

    if (claims.get(AUTHORITIES_KEY) == null || !StringUtils.hasText(claims.get(AUTHORITIES_KEY).toString())) {
      throw new SecurityException("NOT FOUND AUTHORITIES"); // 유저에게 아무런 권한이 없습니다.
    }

    Collection<? extends GrantedAuthority> authorities =
        Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toList());

    UserDetails principal = new User(claims.getSubject(), "", authorities);
    return new CustomAbstractAuthenticationToken(principal, "", authorities);
  }

  public int validateToken(String token) {
    try {
      Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
      return 1;
    } catch (ExpiredJwtException e) {   // 만료된 토큰
      return 2;
    } catch (Exception e) {             // 잘못된 토큰
      return -1;
    }
  }

  private Claims parseClaims(String accessToken) {
    try {
      return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
    } catch (ExpiredJwtException e) { // 만료된 토큰이 더라도 일단 파싱을 함
      return e.getClaims();
    }
  }

}
