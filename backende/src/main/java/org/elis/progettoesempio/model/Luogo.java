package org.elis.progettoesempio.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "luogo")
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Luogo {

	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
 
    @NotBlank
    @Column(nullable = false, length = 150)
    private String name;
 
    @Column(length = 2000)
    private String description;
 
    @Column(length = 255)
    private String address;
 
    @Column(nullable = false)
    private Double latitude;
 
    @Column(nullable = false)
    private Double longitude;
 
    @Column(name = "image_url", length = 500)
    private String imageUrl;
 
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;
}
