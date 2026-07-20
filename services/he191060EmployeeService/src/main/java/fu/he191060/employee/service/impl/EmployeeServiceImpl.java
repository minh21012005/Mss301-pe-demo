package fu.he191060.employee.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import fu.he191060.employee.common.BusinessException;
import fu.he191060.employee.dto.ApiResponseDTO;
import fu.he191060.employee.dto.DepartmentDTO;
import fu.he191060.employee.dto.EmployeeDTO;
import fu.he191060.employee.dto.PageDTO;
import fu.he191060.employee.entity.Employee;
import fu.he191060.employee.repository.EmployeeRepository;
import fu.he191060.employee.service.EmployeeService;
import fu.he191060.employee.service.client.DepartmentClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private static final Set<String> POSITIONS = Set.of("Manager", "Developer", "Staff");
    private static final Set<String> STATUSES = Set.of("ACTIVE", "LEFT", "RETIRED", "INACTIVE");

    private final EmployeeRepository employeeRepository;
    private final DepartmentClient departmentClient;
    private final ObjectMapper objectMapper;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository,
                               DepartmentClient departmentClient,
                               ObjectMapper objectMapper) {
        this.employeeRepository = employeeRepository;
        this.departmentClient = departmentClient;
        this.objectMapper = objectMapper;
    }

    @Override
    public EmployeeDTO create(EmployeeDTO dto) {
        validate(dto, true);
        DepartmentDTO department = getDepartment(dto.getDepartment().getDepartmentId());
        Employee employee = new Employee();
        applyAll(employee, dto);
        return toDTO(employeeRepository.save(employee), department);
    }

    @Override
    public EmployeeDTO update(Long employeeId, EmployeeDTO dto) {
        validateId(employeeId);
        validate(dto, false);
        Employee employee = find(employeeId);
        DepartmentDTO department = null;
        if (dto.getDepartment() != null) {
            department = getDepartment(dto.getDepartment().getDepartmentId());
        }
        applyProvided(employee, dto);
        employee = employeeRepository.save(employee);
        if (department == null) {
            department = getDepartment(employee.getDepartmentId());
        }
        return toDTO(employee, department);
    }

    @Override
    public EmployeeDTO getById(Long employeeId) {
        validateId(employeeId);
        Employee employee = find(employeeId);
        return toDTO(employee, getDepartment(employee.getDepartmentId()));
    }

    @Override
    public void deactivate(Long employeeId) {
        validateId(employeeId);
        Employee employee = find(employeeId);
        employee.setStatus("INACTIVE");
        employeeRepository.save(employee);
    }

    @Override
    public PageDTO getList(int page, int size, String name, String status) {
        if (page < 0 || size < 1 || size > 100) {
            throw new BusinessException(2, 400);
        }
        String normalizedName = blank(name) ? null : name.trim();
        String normalizedStatus = blank(status) ? null : status.trim();
        if (normalizedStatus != null && !STATUSES.contains(normalizedStatus)) {
            throw new BusinessException(2, 400);
        }
        PageRequest pageable = PageRequest.of(page, size, Sort.by("employeeId").ascending());
        Page<Employee> result;
        if (normalizedName != null && normalizedStatus != null) {
            result = employeeRepository.findByFullNameContainingIgnoreCaseAndStatus(
                    normalizedName, normalizedStatus, pageable);
        } else if (normalizedName != null) {
            result = employeeRepository.findByFullNameContainingIgnoreCase(normalizedName, pageable);
        } else if (normalizedStatus != null) {
            result = employeeRepository.findByStatus(normalizedStatus, pageable);
        } else {
            result = employeeRepository.findAll(pageable);
        }
        List<EmployeeDTO> content = result.getContent().stream()
                .map(employee -> toDTO(employee, getDepartment(employee.getDepartmentId())))
                .toList();
        return new PageDTO(result.getSize(), result.getNumber(), result.getTotalPages(),
                result.getTotalElements(), result.isFirst(), result.isLast(), content);
    }

    private void validate(EmployeeDTO dto, boolean creating) {
        if (dto == null) {
            throw new BusinessException(2, 400);
        }
        if (creating && (blank(dto.getFullName()) || blank(dto.getEmail())
                || blank(dto.getPosition()) || blank(dto.getStatus())
                || dto.getStartDate() == null || dto.getDepartment() == null)) {
            throw new BusinessException(2, 400);
        }
        if (dto.getFullName() != null
                && (dto.getFullName().isBlank() || dto.getFullName().length() > 100)) {
            throw new BusinessException(2, 400);
        }
        if (dto.getEmail() != null && (dto.getEmail().isBlank() || dto.getEmail().length() > 100
                || !dto.getEmail().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"))) {
            throw new BusinessException(2, 400);
        }
        if (dto.getPosition() != null && !POSITIONS.contains(dto.getPosition())) {
            throw new BusinessException(2, 400);
        }
        if (dto.getStatus() != null && !STATUSES.contains(dto.getStatus())) {
            throw new BusinessException(2, 400);
        }
        if (dto.getDepartment() != null
                && (dto.getDepartment().getDepartmentId() == null
                || dto.getDepartment().getDepartmentId() <= 0)) {
            throw new BusinessException(2, 400);
        }
    }

    private void validateId(Long employeeId) {
        if (employeeId == null || employeeId <= 0) {
            throw new BusinessException(2, 400);
        }
    }

    private Employee find(Long employeeId) {
        return employeeRepository.findById(employeeId)
                .orElseThrow(() -> new BusinessException(4, 400));
    }

    private boolean blank(String value) {
        return value == null || value.isBlank();
    }

    private DepartmentDTO getDepartment(Long departmentId) {
        try {
            ApiResponseDTO response = departmentClient.getDepartment(departmentId);
            if (response == null || response.getStatus() != 1 || response.getData() == null) {
                throw new BusinessException(4, 400);
            }
            return objectMapper.convertValue(response.getData(), DepartmentDTO.class);
        } catch (FeignException exception) {
            if (exception.status() == 400 || exception.status() == 404) {
                throw new BusinessException(4, 400);
            }
            throw exception;
        }
    }

    private void applyAll(Employee employee, EmployeeDTO dto) {
        employee.setFullName(dto.getFullName().trim());
        employee.setEmail(dto.getEmail().trim());
        employee.setPosition(dto.getPosition());
        employee.setStatus(dto.getStatus());
        employee.setStartDate(dto.getStartDate());
        employee.setEndDate(dto.getEndDate());
        employee.setDepartmentId(dto.getDepartment().getDepartmentId());
    }

    private void applyProvided(Employee employee, EmployeeDTO dto) {
        if (dto.getFullName() != null) employee.setFullName(dto.getFullName().trim());
        if (dto.getEmail() != null) employee.setEmail(dto.getEmail().trim());
        if (dto.getPosition() != null) employee.setPosition(dto.getPosition());
        if (dto.getStatus() != null) employee.setStatus(dto.getStatus());
        if (dto.getStartDate() != null) employee.setStartDate(dto.getStartDate());
        if (dto.getEndDate() != null) employee.setEndDate(dto.getEndDate());
        if (dto.getDepartment() != null) {
            employee.setDepartmentId(dto.getDepartment().getDepartmentId());
        }
    }

    private EmployeeDTO toDTO(Employee employee, DepartmentDTO department) {
        return new EmployeeDTO(
                employee.getEmployeeId(),
                employee.getFullName(),
                employee.getPosition(),
                employee.getStatus(),
                employee.getEmail(),
                employee.getStartDate(),
                employee.getEndDate(),
                department);
    }
}
