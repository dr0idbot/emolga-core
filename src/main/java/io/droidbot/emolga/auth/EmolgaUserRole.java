package io.droidbot.emolga.auth;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = AuthConstants.USER_ROLE_TABLE, schema = AuthConstants.AUTH_SCHEMA,
       uniqueConstraints = @UniqueConstraint(columnNames = {AuthConstants.COLUMN_USER_ID, AuthConstants.COLUMN_ROLE_ID}))
public class EmolgaUserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = AuthConstants.COLUMN_ID)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = AuthConstants.COLUMN_USER_ID, nullable = false)
    private EmolgaUser user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = AuthConstants.COLUMN_ROLE_ID, nullable = false)
    private EmolgaRole role;

    @Column(name = AuthConstants.COLUMN_CREATED_AT, nullable = false)
    private Instant createdAt;

    @Column(name = AuthConstants.COLUMN_UPDATED_AT, nullable = false)
    private Instant updatedAt;

    protected EmolgaUserRole() {
    }

    public EmolgaUserRole(EmolgaUser user, EmolgaRole role) {
        this.user = user;
        this.role = role;
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
    }

    public Long getId() {
        return id;
    }

    public EmolgaUser getUser() {
        return user;
    }

    public EmolgaRole getRole() {
        return role;
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
