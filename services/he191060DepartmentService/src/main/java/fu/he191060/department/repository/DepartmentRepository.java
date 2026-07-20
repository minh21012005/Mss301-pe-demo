package fu.he191060.department.repository;

import fu.he191060.department.entity.Department;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
    boolean existsByCodeIgnoreCase(String code);
    boolean existsByCodeIgnoreCaseAndDepartmentIdNot(String code, Long departmentId);
    Page<Department> findByNameContainingIgnoreCase(String name, Pageable pageable);
    Page<Department> findByStatus(String status, Pageable pageable);
    Page<Department> findByNameContainingIgnoreCaseAndStatus(String name, String status,
                                                              Pageable pageable);
}
