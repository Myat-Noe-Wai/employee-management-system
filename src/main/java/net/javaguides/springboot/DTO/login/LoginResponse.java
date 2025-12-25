package net.javaguides.springboot.DTO.login;

public class LoginResponse {
	String message;
    Boolean status;
    int id;
    String empName;
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public Boolean getStatus() {
        return status;
    }
    public void setStatus(Boolean status) {
        this.status = status;
    }
    
    public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	public LoginResponse(String message, Boolean status) {
        this.message = message;
        this.status = status;
    }
	public LoginResponse(String message, Boolean status, int id, String empName) {
		super();
		this.message = message;
		this.status = status;
		this.id = id;
		this.empName = empName;
	}
}
