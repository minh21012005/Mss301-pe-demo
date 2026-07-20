package fu.he191060.department.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class DepartmentDTO {
    private Long departmentId;
    private String code;
    private String name;
    private String location;
    private String status;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date effectiveDate;

    private Long parentId;

    public DepartmentDTO() {
    }

    public DepartmentDTO(Long departmentId, String code, String name, String location,
                         String status, Date effectiveDate, Long parentId) {
        this.departmentId = departmentId;
        this.code = code;
        this.name = name;
        this.location = location;
        this.status = status;
        this.effectiveDate = effectiveDate;
        this.parentId = parentId;
    }

    public Long getDepartmentId() { return departmentId; }
    public void setDepartmentId(Long departmentId) { this.departmentId = departmentId; }
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Date getEffectiveDate() { return effectiveDate; }
    public void setEffectiveDate(Date effectiveDate) { this.effectiveDate = effectiveDate; }
    public Long getParentId() { return parentId; }
    public void setParentId(Long parentId) { this.parentId = parentId; }
}
