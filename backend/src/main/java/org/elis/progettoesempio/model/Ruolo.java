package org.elis.progettoesempio.model;

import lombok.Getter;

@Getter
public enum Ruolo {
	RUOLO_USER("User"),
    RUOLO_ADMIN("Admin");
    
    private String nome;

	Ruolo(String nome) {
		this.nome = nome;
	}
}
