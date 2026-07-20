package fu.he191060.employee.service;

import fu.he191060.employee.dto.EmployeeDTO;
import fu.he191060.employee.dto.PageDTO;

public interface EmployeeService {
    EmployeeDTO create(EmployeeDTO employeeDTO);
    EmployeeDTO update(Long employeeId, EmployeeDTO employeeDTO);
    EmployeeDTO getById(Long employeeId);
    void deactivate(Long employeeId);
    PageDTO getList(int page, int size, String name, String status);
}
