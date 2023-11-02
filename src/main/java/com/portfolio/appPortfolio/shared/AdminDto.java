package com.portfolio.appPortfolio.shared;

public class AdminDto {
    private long id;
    private String adminId;
    private String adminName;
    private String adminEmail;
    private String adminPassword;

    private String encryptedAdminPassword;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getAdminPassword() {
        return adminPassword;
    }

    public void setAdminPassword(String adminPassword) {
        this.adminPassword = adminPassword;
    }

    public String getEncryptedAdminPassword() {
        return encryptedAdminPassword;
    }

    public void setEncryptedAdminPassword(String encryptedAdminPassword) {
        this.encryptedAdminPassword = encryptedAdminPassword;
    }
}
