package fu.he191060.employee.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class EmployeeDTO {
    private Long employeeId;
    private String fullName;
    private String position;
    private String status;
    private String email;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date startDate;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date endDate;

    private DepartmentDTO department;

    public EmployeeDTO() {
    }

    public EmployeeDTO(Long employeeId, String fullName, String position, String status,
                       String email, Date startDate, Date endDate, DepartmentDTO department) {
        this.employeeId = employeeId;
        this.fullName = fullName;
        this.position = position;
        this.status = status;
        this.email = email;
        this.startDate = startDate;
        this.endDate = endDate;
        this.department = department;
    }

    public Long getEmployeeId() { return employeeId; }
    public void setEmployeeId(Long employeeId) { this.employeeId = employeeId; }
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public String getPosition() { return position; }
    public void setPosition(String position) { this.position = position; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public Date getStartDate() { return startDate; }
    public void setStartDate(Date startDate) { this.startDate = startDate; }
    public Date getEndDate() { return endDate; }
    public void setEndDate(Date endDate) { this.endDate = endDate; }
    public DepartmentDTO getDepartment() { return department; }
    public void setDepartment(DepartmentDTO department) { this.department = department; }
}
