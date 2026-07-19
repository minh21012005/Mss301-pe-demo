package fu.he191060.he191060departmentservice.service.impl;

import fu.he191060.he191060departmentservice.repository.DepartmentRepository;
import fu.he191060.he191060departmentservice.service.DepartmentService;
import org.springframework.stereotype.Service;

@Service
public class DepartmentServiceImpl implements DepartmentService {
    private final DepartmentRepository departmentRepository;

    public DepartmentServiceImpl(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }
}
