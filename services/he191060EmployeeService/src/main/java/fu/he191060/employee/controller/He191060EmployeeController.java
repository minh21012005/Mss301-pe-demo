package fu.he191060.employee.controller;

import fu.he191060.employee.dto.ApiResponseDTO;
import fu.he191060.employee.dto.EmployeeDTO;
import fu.he191060.employee.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/employees")
public class He191060EmployeeController {
    private final EmployeeService employeeService;

    public He191060EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping
    public ResponseEntity<ApiResponseDTO> create(@RequestBody EmployeeDTO employeeDTO) {
        EmployeeDTO created = employeeService.create(employeeDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponseDTO.success(created));
    }

    @PutMapping("/{employeeId}")
    public ResponseEntity<ApiResponseDTO> update(@PathVariable("employeeId") Long employeeId,
                                                  @RequestBody EmployeeDTO employeeDTO) {
        EmployeeDTO updated = employeeService.update(employeeId, employeeDTO);
        return ResponseEntity.ok(ApiResponseDTO.success(updated));
    }

    @GetMapping("/{employeeId}")
    public ResponseEntity<ApiResponseDTO> getDetail(
            @PathVariable("employeeId") Long employeeId) {
        return ResponseEntity.ok(ApiResponseDTO.success(employeeService.getById(employeeId)));
    }

    @DeleteMapping("/{employeeId}")
    public ResponseEntity<ApiResponseDTO> deactivate(
            @PathVariable("employeeId") Long employeeId) {
        employeeService.deactivate(employeeId);
        return ResponseEntity.ok(ApiResponseDTO.success(null));
    }

    @GetMapping
    public ResponseEntity<ApiResponseDTO> getList(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "status", required = false) String status) {
        return ResponseEntity.ok(ApiResponseDTO.success(
                employeeService.getList(page, size, name, status)));
    }
}
