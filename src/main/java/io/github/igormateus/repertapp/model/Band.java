package io.github.igormateus.repertapp.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data // Create getters and setters
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Band {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "created_at")
    @CreationTimestamp
    private Date createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private Date updatedAt;

    @Column(nullable = false, unique = true)
    @NotBlank
    @Size(min = 3, max = 255)
    private String name;

    @Size(max = 255)
    private String description;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "band_app_user",
        joinColumns = @JoinColumn(name = "band_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "app_user_id", referencedColumnName = "id"),
        uniqueConstraints = @UniqueConstraint(name = "band_app_user_unique", columnNames = {"band_id", "app_user_id"}))
    private List<AppUser> members = new ArrayList<>();
}