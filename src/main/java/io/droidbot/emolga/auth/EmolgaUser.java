package io.droidbot.emolga.auth;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = AuthConstants.USER_TABLE, schema = AuthConstants.AUTH_SCHEMA)
public class EmolgaUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = AuthConstants.COLUMN_ID)
    private Long id;

    @Column(name = AuthConstants.COLUMN_USERNAME, nullable = false, unique = true, length = AuthConstants.USERNAME_MAX_LENGTH)
    private String username;

    @Column(name = AuthConstants.COLUMN_EMAIL, unique = true, length = AuthConstants.EMAIL_MAX_LENGTH)
    private String email;

    @Column(name = AuthConstants.COLUMN_PASSWORD_HASH, nullable = false, length = AuthConstants.PASSWORD_HASH_MAX_LENGTH)
    private String passwordHash;

    @Column(name = AuthConstants.COLUMN_DISPLAY_NAME, length = AuthConstants.DISPLAY_NAME_MAX_LENGTH)
    private String displayName;

    @Column(name = AuthConstants.COLUMN_FIRST_NAME, length = AuthConstants.FIRST_NAME_MAX_LENGTH)
    private String firstName;

    @Column(name = AuthConstants.COLUMN_LAST_NAME, length = AuthConstants.LAST_NAME_MAX_LENGTH)
    private String lastName;

    @Column(name = AuthConstants.COLUMN_ENABLED, nullable = false)
    private boolean enabled = true;

    @Column(name = AuthConstants.COLUMN_ACCOUNT_LOCKED, nullable = false)
    private boolean accountLocked = false;

    @Column(name = AuthConstants.COLUMN_EMAIL_VERIFIED, nullable = false)
    private boolean emailVerified = false;

    @Column(name = AuthConstants.COLUMN_LAST_LOGIN_AT)
    private Instant lastLoginAt;

    @Column(name = AuthConstants.COLUMN_CREATED_AT, nullable = false)
    private Instant createdAt;

    @Column(name = AuthConstants.COLUMN_UPDATED_AT, nullable = false)
    private Instant updatedAt;

    protected EmolgaUser() {
    }

    public EmolgaUser(String username, String passwordHash) {
        this.username = username;
        this.passwordHash = passwordHash;
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isAccountLocked() {
        return accountLocked;
    }

    public void setAccountLocked(boolean accountLocked) {
        this.accountLocked = accountLocked;
    }

    public boolean isEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(boolean emailVerified) {
        this.emailVerified = emailVerified;
    }

    public Instant getLastLoginAt() {
        return lastLoginAt;
    }

    public void setLastLoginAt(Instant lastLoginAt) {
        this.lastLoginAt = lastLoginAt;
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
