package fu.he191060.he191060departmentservice.controller;

import fu.he191060.he191060departmentservice.common.BusinessException;
import fu.he191060.he191060departmentservice.dto.ApiResponseDTO;
import fu.he191060.he191060departmentservice.dto.DepartmentDTO;
import fu.he191060.he191060departmentservice.service.DepartmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class he191060DepartmentController {

    private final DepartmentService departmentService;

    public he191060DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @PostMapping("/departments")
    public ApiResponseDTO createDepartment(@RequestBody DepartmentDTO departmentDTO) {
        return new ApiResponseDTO("Department created successfully", true);
    };

    @PutMapping("/departments/{id}")
    public ApiResponseDTO updateDepartment(@PathVariable Long id, @RequestBody DepartmentDTO departmentDTO) {
        return new ApiResponseDTO("Department updated successfully", true);
    };

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiResponseDTO> handleBusinessException(BusinessException e) {
        ApiResponseDTO response = new ApiResponseDTO()
        return ResponseEntity.badRequest().body(new ApiResponseDTO(e.getMessage(), false));
    }

}
