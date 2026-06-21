package io.droidbot.emolga.auth;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = AuthConstants.USER_TABLE)
public class EmolgaUser {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = AuthConstants.COLUMN_ID)
    private Long id;

    @Column(name = AuthConstants.COLUMN_USERNAME, nullable = false, unique = true, length = AuthConstants.USERNAME_MAX_LENGTH)
    private String username;

    @Column(name = AuthConstants.COLUMN_PASSWORD, nullable = false, length = AuthConstants.PASSWORD_MAX_LENGTH)
    private String password;

    @Column(name = AuthConstants.COLUMN_CREATED_AT, nullable = false)
    private Instant createdAt;

    @Column(name = AuthConstants.COLUMN_UPDATED_AT, nullable = false)
    private Instant updatedAt;

    protected EmolgaUser() {
    }

    public EmolgaUser(String username, String password) {
        this.username = username;
        this.password = password;
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }
}
