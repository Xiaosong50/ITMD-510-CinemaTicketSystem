package model;

public class Admin {

    private int adminId;
    private String adminName;
    private String adminPassword;
    private String adminPhone;
    private String adminPosition;

    public Admin(int adminId, String adminName, String adminPassword, String adminPhone, String adminPosition) {
        this.adminId = adminId;
        this.adminName = adminName;
        this.adminPassword = adminPassword;
        this.adminPhone = adminPhone;
        this.adminPosition = adminPosition;
    }

    // Getters and Setters
    public int getAdminId() {
        return adminId;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminId(int adminId) {
		this.adminId = adminId;
	}

	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}

	public void setAdminPassword(String adminPassword) {
		this.adminPassword = adminPassword;
	}

	public void setAdminPhone(String adminPhone) {
		this.adminPhone = adminPhone;
	}

	public void setAdminPosition(String adminPosition) {
		this.adminPosition = adminPosition;
	}

	public String getAdminPassword() {
        return adminPassword;
    }

    public String getAdminPhone() {
        return adminPhone;
    }

    public String getAdminPosition() {
        return adminPosition;
    }
}