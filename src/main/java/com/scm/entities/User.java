package com.scm.entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User implements UserDetails {
	
	@Id
	private String userId;
	@Column(name="user_name",nullable = false)
	private String name;
	@Column(unique = true,nullable = false)
	private String email;
	private String password;
	@Column(length = 1000)
	private String about;
	@Column(length = 1000)
	private String profilePic;
	private String phoneNumber;
	//information
	private boolean enabled = true;
	private boolean emailVerified = false;
	private boolean phoneVerified = false;
	
	// SELF, GOOGLE, FACEBOOK, TWITTER,LINKEDIN,GITHUB
	@Enumerated(value = EnumType.STRING)
    private Providers provider = Providers.SELF;
    private String providerUserId;
    
    //add more fields needed
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Contact> contacts = new ArrayList<>();

	

	

	@Override
	public String toString() {
		return "User [userId=" + userId + ", name=" + name + ", email=" + email + ", password=" + password + ", about="
				+ about + ", profilePic=" + profilePic + ", phoneNumber=" + phoneNumber + ", enabled=" + enabled
				+ ", emailVerified=" + emailVerified + ", phoneVerified=" + phoneVerified + ", provider=" + provider
				+ ", providerUserId=" + providerUserId + ", contacts=" + contacts + "]";
	}


    @ElementCollection(fetch = FetchType.EAGER)
	private List<String> roleList = new ArrayList<>();


	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		//list of roles[USER,ADMIN]
		//collection of SimpGrantedAuthority[roles{ADMIN,USER}]
		Collection<SimpleGrantedAuthority> roles =  roleList.stream().map(role -> new SimpleGrantedAuthority(role)).collect(Collectors.toList());
		return roles;
	}

 // for this project:
	//email id hai wahi hamare username



	@Override
	public String getUsername() {
		
		return this.email;
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
    	return this.enabled;
    }
	
	public String getPassword() {
		return this.password;
	}
	

}
