package com.github.fromi.openidconnect.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UserInfo implements UserDetails {
    private final String id;
    private final String name;
    private final String givenName;
    private final String familyName;
    private final String gender;
    private final String picture;
    private final String link;
    private final String email;
    private final String verifiedEmail;
    @JsonCreator
    public UserInfo(@JsonProperty("id") String id,
                    @JsonProperty("name") String name,
                    @JsonProperty("given_name") String givenName,
                    @JsonProperty("family_name") String familyName,
                    @JsonProperty("gender") String gender,
                    @JsonProperty("picture") String picture,
                    @JsonProperty("link") String link,
                    @JsonProperty("email") String email,
                    @JsonProperty("verified_email") String verifiedEmail) {
        this.id = id;
        this.name = name;
        this.givenName = givenName;
        this.familyName = familyName;
        this.gender = gender;
        this.picture = picture;
        this.link = link;
        this.email = email;
        this.verifiedEmail = verifiedEmail;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getGivenName() {
        return givenName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public String getGender() {
        return gender;
    }

    public String getPicture() {
        return picture;
    }

    public String getLink() {
        return link;
    }

    public String getEmail() {
        return email;
    }

    public String getVerifiedEmail() {
        return verifiedEmail;
    }

	@Override
	public String toString() {
		return "UserInfo [id=" + id + ", name=" + name + ", givenName="
				+ givenName + ", familyName=" + familyName + ", gender="
				+ gender + ", picture=" + picture + ", link=" + link + ", email=" + email + ", verifiedEmail=" + verifiedEmail + "]";
	}

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return null;
  }

  @Override
  public String getPassword() {
    return null;
  }

  @Override
  public String getUsername() {
    return givenName;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
