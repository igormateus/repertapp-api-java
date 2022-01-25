package io.github.igormateus.repertapp.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data // Create getters and setters
@NoArgsConstructor
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "created_at")
    @CreationTimestamp
    private Date createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private Date updatedAt;

    @Column(unique = true, nullable = false)
    @Size(min = 5, max = 255)
    @NotBlank
    private String username;

    @Column(nullable = false)
    @Size(min = 8)
    @NotBlank
    private String password;

    @Column(unique = true)
    @Email
    private String email;

    @Column(unique = true)
    @Size(min = 3, max = 255)
    private String name;

    @Column
    @Size(max = 255)
    private String bio;

    @ElementCollection(fetch = FetchType.EAGER)
    List<AppUserRole> appUserRoles;

}