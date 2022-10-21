package com.catchup.domain.entity;

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
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "user_id")
  private Long id;

  @Column(name = "oauth_provider_type", nullable = false)
  @Enumerated(EnumType.STRING)
  private OauthProviderType oauthProviderType;

  @Column(name = "uid", nullable = false)
  private String uid;

}
