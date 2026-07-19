package fu.he191060.he191060departmentservice.dto;

import java.util.Date;

public class DepartmentDTO {
    private long departmentId;
    private String code;
    private String name;
    private String location;
    private long parentId;
    private String status;
    private Date effectiveDate;

    public DepartmentDTO(long departmentId, String code, String location, String name, long parentId, String status, Date effectiveDate) {
        this.departmentId = departmentId;
        this.code = code;
        this.location = location;
        this.name = name;
        this.parentId = parentId;
        this.status = status;
        this.effectiveDate = effectiveDate;
    }

    public long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(long departmentId) {
        this.departmentId = departmentId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public long getParentId() {
        return parentId;
    }

    public void setParentId(long parentId) {
        this.parentId = parentId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }
}
