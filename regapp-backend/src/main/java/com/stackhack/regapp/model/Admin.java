package com.stackhack.regapp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(
    name = "admin"
)
public class Admin {
    @Id
    @GeneratedValue(
        strategy = GenerationType.IDENTITY
    )
    @Column(
        name = "admin_id"
    )
    private long adminId;
    @Column(
        name = "admin_username",
        unique = true
    )
    private String email;
    @Column(
        name = "admin_password"
    )
    private String password;

    public Admin() {
    }

    public Admin(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public long getAdminId() {
        return this.adminId;
    }

    public void setAdminId(long adminId) {
        this.adminId = adminId;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String toString() {
        return "Admin{adminId=" + this.adminId + ", email='" + this.email + '\'' + ", password='" + this.password + '\'' + '}';
    }
}
