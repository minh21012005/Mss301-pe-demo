package fu.he191060.department.controller;

import fu.he191060.department.dto.ApiResponseDTO;
import fu.he191060.department.dto.DepartmentDTO;
import fu.he191060.department.service.DepartmentService;
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
@RequestMapping("/api/departments")
public class He191060DepartmentController {
    private final DepartmentService departmentService;

    public He191060DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @PostMapping
    public ResponseEntity<ApiResponseDTO> create(@RequestBody DepartmentDTO departmentDTO) {
        DepartmentDTO created = departmentService.create(departmentDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponseDTO.success(created));
    }

    @PutMapping("/{departmentId}")
    public ResponseEntity<ApiResponseDTO> update(@PathVariable("departmentId") Long departmentId,
                                                  @RequestBody DepartmentDTO departmentDTO) {
        DepartmentDTO updated = departmentService.update(departmentId, departmentDTO);
        return ResponseEntity.ok(ApiResponseDTO.success(updated));
    }

    @GetMapping("/{departmentId}")
    public ResponseEntity<ApiResponseDTO> getDetail(
            @PathVariable("departmentId") Long departmentId) {
        return ResponseEntity.ok(ApiResponseDTO.success(departmentService.getById(departmentId)));
    }

    @DeleteMapping("/{departmentId}")
    public ResponseEntity<ApiResponseDTO> deactivate(
            @PathVariable("departmentId") Long departmentId) {
        departmentService.deactivate(departmentId);
        return ResponseEntity.ok(ApiResponseDTO.success(null));
    }

    @GetMapping
    public ResponseEntity<ApiResponseDTO> getList(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "status", required = false) String status) {
        return ResponseEntity.ok(ApiResponseDTO.success(
                departmentService.getList(page, size, name, status)));
    }
}
