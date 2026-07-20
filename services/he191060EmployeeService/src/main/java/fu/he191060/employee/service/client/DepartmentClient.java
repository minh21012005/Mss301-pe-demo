package fu.he191060.employee.service.client;

import fu.he191060.employee.dto.ApiResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "department-service", url = "http://localhost:8081")
public interface DepartmentClient {
    @GetMapping("/api/departments/{departmentId}")
    ApiResponseDTO getDepartment(@PathVariable("departmentId") Long departmentId);
}
