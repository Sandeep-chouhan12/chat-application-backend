package com.dollop.app.request;

import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotNull;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {

  
  @NotBlank(message = "email not be blank") 
  @NotNull(message = "email not be null")
  @Pattern(regexp ="^[a-z][a-z0-9._-]*@[a-z0-9.-]+\\.com$",message="The string has to be a well-formed email address")
  private String email;
  
  
  @NotBlank(message = "password not be blank")  
  @NotNull(message = "password not be null")
//  @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%?&])[A-Za-z\\d@$!%?&]{8,}$",message="the password is not in current formate")
  private String password;
  
}

//Lowercase letters, numbers, @, valid domain, no spaces  for email
//At least 8 chars, 1 uppercase, 1 lowercase, 1 digit, 1 special character, no
// spaces  for password
