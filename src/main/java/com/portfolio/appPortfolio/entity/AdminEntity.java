package com.portfolio.appPortfolio.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name = "admin")
public class AdminEntity {

    @Id
    @GeneratedValue
    private long id;
    @Column(nullable = false)
    private String adminId;
    @Column(nullable = false)
    private String adminName;
    @Column(nullable = false)
    private String adminEmail;
    @Column(nullable = false)
    private String encryptedAdminPassword;

    public AdminEntity() {
    }

    public AdminEntity(String adminId, String adminName, String adminEmail, String encryptedAdminPassword) {
        this.adminId = adminId;
        this.adminName = adminName;
        this.adminEmail = adminEmail;
        this.encryptedAdminPassword = encryptedAdminPassword;
    }

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getAdminEmail() {
        return adminEmail;
    }

    public void setAdminEmail(String adminEmail) {
        this.adminEmail = adminEmail;
    }

    public String getEncryptedAdminPassword() {
        return encryptedAdminPassword;
    }

    public void setEncryptedAdminPassword(String encryptedAdminPassword) {
        this.encryptedAdminPassword = encryptedAdminPassword;
    }
}
