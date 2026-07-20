package fu.he191060.department.service;

import fu.he191060.department.dto.DepartmentDTO;
import fu.he191060.department.dto.PageDTO;

public interface DepartmentService {
    DepartmentDTO create(DepartmentDTO departmentDTO);
    DepartmentDTO update(Long departmentId, DepartmentDTO departmentDTO);
    DepartmentDTO getById(Long departmentId);
    void deactivate(Long departmentId);
    PageDTO getList(int page, int size, String name, String status);
}
