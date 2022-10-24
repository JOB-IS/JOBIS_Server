package com.catchup.domain.entity;

import com.catchup.domain.code.AuthorityType;
import com.catchup.domain.code.OauthProviderType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class User extends BaseTime{

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "user_id")
  private Long id;

  private String nickName;

  @Column(name = "oauth_id", nullable = false, unique = true)
  private String oauthId;

  @Column(name = "oauth_provider_type", nullable = false)
  @Enumerated(EnumType.STRING)
  private OauthProviderType oauthProviderType;

  @Column(name = "authority_type", nullable = false)
  @Enumerated(EnumType.STRING)
  private AuthorityType authorityType;

}
