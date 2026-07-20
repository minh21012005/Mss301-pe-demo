package fu.he191060.employee.repository;

import fu.he191060.employee.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Page<Employee> findByFullNameContainingIgnoreCase(String fullName, Pageable pageable);
    Page<Employee> findByStatus(String status, Pageable pageable);
    Page<Employee> findByFullNameContainingIgnoreCaseAndStatus(String fullName, String status,
                                                                Pageable pageable);
}
