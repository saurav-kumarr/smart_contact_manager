package com.scm.forms;




import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class UserForm {
	
	@NotBlank(message = "Username is required")
	@Size(min = 3,message = "Min 3 Characters is required")
	private String name;
	
	@Email(message = "Invalid email Address")
	@NotBlank(message = "Email is required")
	//@Pattern(regexp = "^[a-z0-9]+([._-][a-z0-9]+)*@[a-z0-9-]+(\\.[a-z]{2,})+$", message = "Invalid email address!")
	private String email;
	
	@NotBlank(message = "Password is required")
	@Size(min = 6,message = "Min 6 Character is required")
	private String password;
	
	@NotBlank(message = "About is required")
	private String about;
	
	@Size(min = 10,max = 10, message = "Invalid Phone Number")
	private String phoneNumber;
	
}
