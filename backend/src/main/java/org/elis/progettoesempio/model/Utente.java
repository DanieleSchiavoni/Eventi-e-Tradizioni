package org.elis.progettoesempio.model;

import java.io.Serial;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
public class Utente implements UserDetails {

	@Serial
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	@EqualsAndHashCode.Exclude
	@NotNull
	private Ruolo ruolo;

	@Column(nullable = false)
	@NotBlank
	private String nome;

	@Column(nullable = false)
	@NotBlank
	private String cognome;

	@Column(nullable = false, unique = true)
	@Email
	@NotNull
	private String email;

	@Column(nullable = false)
	@NotBlank
	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	private String password;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
	    SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + ruolo.getNome().toUpperCase());
	    return List.of(authority);
	}
	
	@Override
	public String getUsername() {
		// Utente non ha un campo "username": il login avviene per email
		return email;
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