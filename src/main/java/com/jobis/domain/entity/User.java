package com.jobis.domain.entity;

import com.jobis.domain.code.AuthType;
import com.jobis.domain.code.OauthProviderType;
import java.util.Objects;
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
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "user_id")
  private Long id;

  @Column(name = "email", nullable = false)
  private String email;

  @Column(name = "oauth_id", nullable = false, unique = true)
  private String oauthId;

  @Column(name = "nick_name", unique = true)
  private String nickName;    // nickname == null : 회원가입 완료 이전

  @Column(name = "oauth_provider_type", nullable = false)
  @Enumerated(EnumType.STRING)
  private OauthProviderType oauthProviderType;

  @Column(name = "auth_type", nullable = false)
  @Enumerated(EnumType.STRING)
  private AuthType authType;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof User)) {
      return false;
    }
    User user = (User) o;
    return Objects.equals(getId(), user.getId());
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

}
