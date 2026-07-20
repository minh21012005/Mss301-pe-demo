package fu.he191060.department.service.impl;

import fu.he191060.department.common.BusinessException;
import fu.he191060.department.dto.DepartmentDTO;
import fu.he191060.department.dto.PageDTO;
import fu.he191060.department.entity.Department;
import fu.he191060.department.repository.DepartmentRepository;
import fu.he191060.department.service.DepartmentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Set;

@Service
public class DepartmentServiceImpl implements DepartmentService {
    private static final Set<String> STATUSES = Set.of("ACTIVE", "INACTIVE", "CLOSED");
    private final DepartmentRepository departmentRepository;

    public DepartmentServiceImpl(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @Override
    public DepartmentDTO create(DepartmentDTO dto) {
        validate(dto, true);
        String code = dto.getCode().trim();
        if (departmentRepository.existsByCodeIgnoreCase(code)) {
            throw new BusinessException(3, 400);
        }
        Department department = new Department();
        applyAll(department, dto);
        return toDTO(departmentRepository.save(department));
    }

    @Override
    public DepartmentDTO update(Long departmentId, DepartmentDTO dto) {
        validateId(departmentId);
        validate(dto, false);
        Department department = find(departmentId);
        if (dto.getCode() != null) {
            String code = dto.getCode().trim();
            if (departmentRepository.existsByCodeIgnoreCaseAndDepartmentIdNot(code, departmentId)) {
                throw new BusinessException(3, 400);
            }
            department.setCode(code);
        }
        applyProvided(department, dto);
        return toDTO(departmentRepository.save(department));
    }

    @Override
    public DepartmentDTO getById(Long departmentId) {
        validateId(departmentId);
        return toDTO(find(departmentId));
    }

    @Override
    public void deactivate(Long departmentId) {
        validateId(departmentId);
        Department department = find(departmentId);
        department.setStatus("INACTIVE");
        departmentRepository.save(department);
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
        PageRequest pageable = PageRequest.of(page, size, Sort.by("departmentId").ascending());
        Page<Department> result;
        if (normalizedName != null && normalizedStatus != null) {
            result = departmentRepository.findByNameContainingIgnoreCaseAndStatus(
                    normalizedName, normalizedStatus, pageable);
        } else if (normalizedName != null) {
            result = departmentRepository.findByNameContainingIgnoreCase(normalizedName, pageable);
        } else if (normalizedStatus != null) {
            result = departmentRepository.findByStatus(normalizedStatus, pageable);
        } else {
            result = departmentRepository.findAll(pageable);
        }
        List<DepartmentDTO> content = result.getContent().stream().map(this::toDTO).toList();
        return new PageDTO(result.getSize(), result.getNumber(), result.getTotalPages(),
                result.getTotalElements(), result.isFirst(), result.isLast(), content);
    }

    private void validate(DepartmentDTO dto, boolean creating) {
        if (dto == null) {
            throw new BusinessException(2, 400);
        }
        if (creating && blank(dto.getName())) {
            throw new BusinessException(2, 400);
        }
        if (creating && blank(dto.getCode())) {
            throw new BusinessException(2, 400);
        }
        if (dto.getName() != null && (dto.getName().isBlank() || dto.getName().length() > 50)) {
            throw new BusinessException(2, 400);
        }
        if (dto.getCode() != null && (dto.getCode().isBlank() || dto.getCode().length() > 10
                || !dto.getCode().matches("[A-Za-z0-9]+"))) {
            throw new BusinessException(2, 400);
        }
        if (dto.getLocation() != null && dto.getLocation().length() > 100) {
            throw new BusinessException(2, 400);
        }
        if (dto.getStatus() != null && !STATUSES.contains(dto.getStatus())) {
            throw new BusinessException(2, 400);
        }
        if (dto.getParentId() != null && dto.getParentId() <= 0) {
            throw new BusinessException(2, 400);
        }
        if (dto.getEffectiveDate() != null) {
            LocalDate date = dto.getEffectiveDate().toInstant()
                    .atZone(ZoneId.systemDefault()).toLocalDate();
            if (!date.isAfter(LocalDate.of(2000, 1, 1))
                    || !date.isBefore(LocalDate.now().plusDays(360))) {
                throw new BusinessException(2, 400);
            }
        }
    }

    private void validateId(Long departmentId) {
        if (departmentId == null || departmentId <= 0) {
            throw new BusinessException(2, 400);
        }
    }

    private Department find(Long departmentId) {
        return departmentRepository.findById(departmentId)
                .orElseThrow(() -> new BusinessException(4, 400));
    }

    private boolean blank(String value) {
        return value == null || value.isBlank();
    }

    private void applyAll(Department department, DepartmentDTO dto) {
        department.setCode(dto.getCode().trim());
        department.setName(dto.getName().trim());
        department.setLocation(dto.getLocation() == null ? null : dto.getLocation().trim());
        department.setStatus(dto.getStatus());
        department.setEffectiveDate(dto.getEffectiveDate());
        department.setParentId(dto.getParentId() == null ? 0L : dto.getParentId());
    }

    private void applyProvided(Department department, DepartmentDTO dto) {
        if (dto.getName() != null) {
            department.setName(dto.getName().trim());
        }
        if (dto.getLocation() != null) {
            department.setLocation(dto.getLocation().trim());
        }
        if (dto.getStatus() != null) {
            department.setStatus(dto.getStatus());
        }
        if (dto.getEffectiveDate() != null) {
            department.setEffectiveDate(dto.getEffectiveDate());
        }
        if (dto.getParentId() != null) {
            department.setParentId(dto.getParentId());
        }
    }

    private DepartmentDTO toDTO(Department department) {
        return new DepartmentDTO(
                department.getDepartmentId(),
                department.getCode(),
                department.getName(),
                department.getLocation(),
                department.getStatus(),
                department.getEffectiveDate(),
                Long.valueOf(0L).equals(department.getParentId())
                        ? null : department.getParentId());
    }
}
