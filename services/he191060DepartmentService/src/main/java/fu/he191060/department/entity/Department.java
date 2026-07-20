package fu.he191060.department.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

import java.util.Date;

@Entity
@Table(name = "departments")
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "department_id")
    private Long departmentId;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "code", nullable = false, unique = true, length = 10)
    private String code;

    @Temporal(TemporalType.DATE)
    @Column(name = "effective_date")
    private Date effectiveDate;

    @Column(name = "status", length = 10)
    private String status;

    @Column(name = "location", length = 100)
    private String location;

    @Column(name = "parent_id")
    private Long parentId;

    public Department() {
    }

    public Long getDepartmentId() { return departmentId; }
    public void setDepartmentId(Long departmentId) { this.departmentId = departmentId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public Date getEffectiveDate() { return effectiveDate; }
    public void setEffectiveDate(Date effectiveDate) { this.effectiveDate = effectiveDate; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public Long getParentId() { return parentId; }
    public void setParentId(Long parentId) { this.parentId = parentId; }
}
