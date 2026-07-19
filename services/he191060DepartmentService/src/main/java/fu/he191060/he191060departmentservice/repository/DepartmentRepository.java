package fu.he191060.he191060departmentservice.repository;

import fu.he191060.he191060departmentservice.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
}
