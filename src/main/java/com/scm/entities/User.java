package com.scm.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
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
public class User {
	
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
	private boolean enabled = false;
	private boolean emailVerified = false;
	private boolean phoneVerified = false;
	
	// SELF, GOOGLE, FACEBOOK, TWITTER,LINKEDIN,GITHUB
	@Enumerated(value = EnumType.STRING)
    private Providers provider = Providers.SELF;
    private String providerUserId;
    
    //add more fields needed
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Contact> contacts = new ArrayList<>();

	public User(String userId, String name, String email, String password, String about, String profilePic,
			String phoneNumber, boolean enabled, boolean emailVerified, boolean phoneVerified, Providers provider,
			String providerUserId, List<Contact> contacts) {
		super();
		this.userId = userId;
		this.name = name;
		this.email = email;
		this.password = password;
		this.about = about;
		this.profilePic = profilePic;
		this.phoneNumber = phoneNumber;
		this.enabled = enabled;
		this.emailVerified = emailVerified;
		this.phoneVerified = phoneVerified;
		this.provider = provider;
		this.providerUserId = providerUserId;
		this.contacts = contacts;
	}

	public User() {
		super();
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAbout() {
		return about;
	}

	public void setAbout(String about) {
		this.about = about;
	}

	public String getProfilePic() {
		return profilePic;
	}

	public void setProfilePic(String profilePic) {
		this.profilePic = profilePic;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public boolean isEmailVerified() {
		return emailVerified;
	}

	public void setEmailVerified(boolean emailVerified) {
		this.emailVerified = emailVerified;
	}

	public boolean isPhoneVerified() {
		return phoneVerified;
	}

	public void setPhoneVerified(boolean phoneVerified) {
		this.phoneVerified = phoneVerified;
	}

	public Providers getProvider() {
		return provider;
	}

	public void setProvider(Providers provider) {
		this.provider = provider;
	}

	public String getProviderUserId() {
		return providerUserId;
	}

	public void setProviderUserId(String providerUserId) {
		this.providerUserId = providerUserId;
	}

	public List<Contact> getContacts() {
		return contacts;
	}

	public void setContacts(List<Contact> contacts) {
		this.contacts = contacts;
	}
	
	//Builder Method
	public static UserBuilder builder() {
	    return new UserBuilder();
	}

	//Inner Builder Class
	public static class UserBuilder {
	    private String userId;
	    private String name;
	    private String email;
	    private String password;
	    private String about;
	    private String profilePic;
	    private String phoneNumber;
	    private boolean enabled;
	    private boolean emailVerified;
	    private boolean phoneVerified;
	    private Providers provider;
	    private String providerUserId;
	    private List<Contact> contacts = new ArrayList<>();

	    public UserBuilder userId(String userId) {
	        this.userId = userId;
	        return this;
	    }

	    public UserBuilder name(String name) {
	        this.name = name;
	        return this;
	    }

	    public UserBuilder email(String email) {
	        this.email = email;
	        return this;
	    }

	    public UserBuilder password(String password) {
	        this.password = password;
	        return this;
	    }

	    public UserBuilder about(String about) {
	        this.about = about;
	        return this;
	    }

	    public UserBuilder profilePic(String profilePic) {
	        this.profilePic = profilePic;
	        return this;
	    }

	    public UserBuilder phoneNumber(String phoneNumber) {
	        this.phoneNumber = phoneNumber;
	        return this;
	    }

	    public UserBuilder enabled(boolean enabled) {
	        this.enabled = enabled;
	        return this;
	    }

	    public UserBuilder emailVerified(boolean emailVerified) {
	        this.emailVerified = emailVerified;
	        return this;
	    }

	    public UserBuilder phoneVerified(boolean phoneVerified) {
	        this.phoneVerified = phoneVerified;
	        return this;
	    }

	    public UserBuilder provider(Providers provider) {
	        this.provider = provider;
	        return this;
	    }

	    public UserBuilder providerUserId(String providerUserId) {
	        this.providerUserId = providerUserId;
	        return this;
	    }

	    public UserBuilder contacts(List<Contact> contacts) {
	        this.contacts = contacts;
	        return this;
	    }

	    public User build() {
	        return new User(userId, name, email, password, about, profilePic, phoneNumber, enabled, emailVerified, phoneVerified, provider, providerUserId, contacts);
	    }
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", name=" + name + ", email=" + email + ", password=" + password + ", about="
				+ about + ", profilePic=" + profilePic + ", phoneNumber=" + phoneNumber + ", enabled=" + enabled
				+ ", emailVerified=" + emailVerified + ", phoneVerified=" + phoneVerified + ", provider=" + provider
				+ ", providerUserId=" + providerUserId + ", contacts=" + contacts + "]";
	}
    
    
    
    
    
	

}
