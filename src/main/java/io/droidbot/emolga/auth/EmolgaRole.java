package io.droidbot.emolga.auth;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = AuthConstants.ROLE_TABLE, schema = AuthConstants.AUTH_SCHEMA)
public class EmolgaRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = AuthConstants.COLUMN_ID)
    private Long id;

    @Column(name = AuthConstants.COLUMN_ROLE_NAME, nullable = false, unique = true, length = AuthConstants.ROLE_NAME_MAX_LENGTH)
    private String roleName;

    @Column(name = AuthConstants.COLUMN_DESCRIPTION, length = AuthConstants.DESCRIPTION_MAX_LENGTH)
    private String description;

    @Column(name = AuthConstants.COLUMN_CREATED_AT, nullable = false)
    private Instant createdAt;

    @Column(name = AuthConstants.COLUMN_UPDATED_AT, nullable = false)
    private Instant updatedAt;

    protected EmolgaRole() {
    }

    public EmolgaRole(String roleName) {
        this.roleName = roleName;
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
    }

    public Long getId() {
        return id;
    }

    public String getRoleName() {
        return roleName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }
}
