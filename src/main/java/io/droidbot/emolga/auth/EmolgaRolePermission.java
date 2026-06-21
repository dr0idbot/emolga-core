package io.droidbot.emolga.auth;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = AuthConstants.ROLE_PERMISSION_TABLE, schema = AuthConstants.AUTH_SCHEMA,
       uniqueConstraints = @UniqueConstraint(columnNames = {AuthConstants.COLUMN_ROLE_ID, AuthConstants.COLUMN_PERMISSION_KEY}))
public class EmolgaRolePermission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = AuthConstants.COLUMN_ID)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = AuthConstants.COLUMN_ROLE_ID, nullable = false)
    private EmolgaRole role;

    @Column(name = AuthConstants.COLUMN_PERMISSION_KEY, nullable = false, length = AuthConstants.PERMISSION_KEY_MAX_LENGTH)
    private String permissionKey;

    @Column(name = AuthConstants.COLUMN_CREATED_AT, nullable = false)
    private Instant createdAt;

    @Column(name = AuthConstants.COLUMN_UPDATED_AT, nullable = false)
    private Instant updatedAt;

    protected EmolgaRolePermission() {
    }

    public EmolgaRolePermission(EmolgaRole role, String permissionKey) {
        this.role = role;
        this.permissionKey = permissionKey;
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
    }

    public Long getId() {
        return id;
    }

    public EmolgaRole getRole() {
        return role;
    }

    public String getPermissionKey() {
        return permissionKey;
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
