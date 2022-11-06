package com.jobis.domain.entity;

import com.jobis.domain.code.OauthProviderType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class User extends BaseTime {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "user_id")
  private Long id;

  @Column(name = "nick_name", unique = true)
  private String nickName;

  @Column(name = "oauth_id", nullable = false, unique = true)
  private String oauthId;

  @Column(name = "oauth_provider_type", nullable = false)
  @Enumerated(EnumType.STRING)
  private OauthProviderType oauthProviderType;

  @Column(name = "activated", nullable = false)
  private boolean activated;

}
