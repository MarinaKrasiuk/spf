# Spring Framework L01: Spring Fundamentals - Assignment

## Assignment Overview

**Course**: Spring Framework Training Course  
**Lecture**: 1 of 10  
**Assignment**: Spring Fundamentals Implementation  
**Total Points**: 100 points  
**Due Date**: Before next lecture  
**Estimated Time**: 4-6 hours

### Learning Objectives

By completing this assignment, you will demonstrate:
- Understanding of Inversion of Control (IoC) and Dependency Injection (DI) principles
- Ability to set up and configure Spring Boot applications from scratch
- Proficiency in implementing constructor-based dependency injection
- Knowledge of Spring component scanning and bean lifecycle management
- Skills in creating layered architecture with proper separation of concerns
- Understanding of Spring Boot application properties and configuration

## Task Description

Create the foundation of a **Task Management System** using Spring Boot and fundamental Spring Framework concepts. This system will serve as the base for all subsequent lectures, growing in complexity and features as we progress through the course.

### Core Requirements

#### 1. Project Setup and Configuration
- Create a Spring Boot 3.5.x application with Maven
- Configure proper project structure following Spring Boot conventions
- Set up application properties for different environments (dev, test, prod)
- Include necessary dependencies: Spring Boot Web, Validation, Lombok, Testing

#### 2. Domain Model Implementation
- Create a `Task` entity with the following properties:
    - `id` (Long) - unique identifier
    - `title` (String) - required, 3-100 characters
    - `description` (String) - optional, max 1000 characters
    - `status` (Enum) - TODO, IN_PROGRESS, DONE
    - `priority` (Enum) - LOW, MEDIUM, HIGH, CRITICAL
    - `assigneeEmail` (String) - required, valid email format
    - `createdAt` (LocalDateTime) - auto-generated
    - `updatedAt` (LocalDateTime) - auto-updated

#### 3. Service Layer Implementation
- **TaskService**: Core business logic for CRUD operations
- **NotificationService**: Handle task assignment and status change notifications
- **AuditService**: Log all task operations for tracking purposes
- All services must use **constructor injection only** (no @Autowired fields)

#### 4. Repository Layer Implementation
- **TaskRepository**: In-memory storage using thread-safe collections
- Implement all CRUD operations with proper error handling
- Support for sorting and filtering operations
- Initialize with sample data for demonstration purposes

#### 5. Application Configuration
- Multi-profile support (dev, test, prod) with different configurations
- Externalized configuration via application.properties
- Custom application properties binding using @ConfigurationProperties
- Proper bean configuration and lifecycle management

### Functional Requirements

#### Task Operations
- Create new tasks with validation
- Retrieve tasks by ID and list all tasks
- Update task details and status with business rule validation
- Delete tasks with proper cleanup
- Search tasks by assignee email and status

#### Business Rules
- Task titles must be unique within the system
- Status transitions must follow business logic (TODO → IN_PROGRESS → DONE)
- High priority tasks must have valid assignee emails
- System must maintain audit trail of all operations

#### Configuration Requirements
- Support for different logging levels per environment
- Database connection properties (for future integration)
- Application-specific properties (notification settings, audit configuration)
- Profile-specific bean creation and configuration

## Acceptance Criteria

### Functional Criteria
✅ **Task Creation**: Application can create tasks with all required fields and validation  
✅ **Task Retrieval**: Application can find tasks by ID and list all tasks with proper sorting  
✅ **Task Updates**: Application can update task details and status with business rule enforcement  
✅ **Task Deletion**: Application can remove tasks with proper cleanup and notifications  
✅ **Notifications**: NotificationService triggers appropriately on task operations  
✅ **Auditing**: AuditService logs all operations with timestamps and user tracking  
✅ **Validation**: Comprehensive input validation with meaningful error messages  
✅ **Profiles**: Application runs correctly with different configuration profiles

### Technical Criteria
✅ **Spring Boot Setup**: Uses Spring Boot 3.5.x with proper starter dependencies  
✅ **Java 21**: Utilizes modern Java features and syntax appropriately  
✅ **Constructor DI**: All dependencies injected via constructors, no field injection  
✅ **Lombok Integration**: Proper usage of @Data, @RequiredArgsConstructor, @Slf4j  
✅ **Layered Architecture**: Clear separation between service and repository layers  
✅ **Testing Coverage**: Comprehensive unit tests with 80%+ coverage  
✅ **Configuration Management**: Externalized and environment-specific configuration  
✅ **Thread Safety**: Repository implementation handles concurrent access properly

## Implementation Guidelines

### Phase 1: Project Foundation (1 hour)
1. Create Spring Boot Maven project with required dependencies
2. Set up proper package structure
3. Configure application.properties for multiple profiles
4. Verify project compiles and runs successfully

### Phase 2: Domain Model (1 hour)
1. Implement Status and Priority enums with validation
2. Create Task entity with all required fields and annotations
3. Add business logic methods (status transitions, validation)
4. Write unit tests for domain model

### Phase 3: Repository Layer (1.5 hours)
1. Implement TaskRepository with thread-safe in-memory storage
2. Add all CRUD operations with proper error handling
3. Implement search and filtering capabilities
4. Add sample data initialization
5. Write comprehensive repository tests

### Phase 4: Service Layer (1.5 hours)
1. Implement all three service classes with constructor injection
2. Add business logic and validation in TaskService
3. Implement notification and audit functionality
4. Wire all dependencies properly
5. Write service layer unit tests

### Phase 5: Integration and Testing (1 hour)
1. Create Spring Boot main application class
2. Write integration tests to verify all components work together
3. Test different configuration profiles
4. Verify application starts and functions correctly
5. Document any setup or running instructions

### Best Practices to Follow

#### Constructor Injection Pattern
```java
@Service
@RequiredArgsConstructor  // Lombok generates constructor
@Slf4j
public class TaskService {
    private final TaskRepository taskRepository;
    private final NotificationService notificationService;
    private final AuditService auditService;
    
    // All dependencies injected via constructor
}
```

#### Proper Configuration
```java
@ConfigurationProperties(prefix = "app.task")
@Data
public class TaskProperties {
    private String defaultPriority = "MEDIUM";
    private boolean notificationsEnabled = true;
    private int maxTitleLength = 100;
}
```

#### Thread-Safe Repository
```java
@Repository
public class TaskRepository {
    private final Map<Long, Task> tasks = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);
    
    // Thread-safe implementation
}
```

## Evaluation Focus Areas

### Code Quality (30 points)
- Clean, readable code with meaningful names
- Proper use of Lombok annotations and Java features
- Comprehensive documentation and comments
- Adherence to Spring and Java best practices
- No code duplication or anti-patterns

### Functionality (25 points)
- All required features implemented correctly
- Business rules properly enforced
- Error handling covers edge cases
- System maintains data consistency

### Architecture (25 points)
- Proper layered architecture implementation
- Clean separation of concerns
- Correct dependency injection usage
- Good configuration management practices

### Testing (20 points)
- Comprehensive unit tests for all components
- Integration tests verify end-to-end functionality
- Good test organization and naming
- Edge cases and error scenarios covered

## Common Pitfalls to Avoid

❌ **Field Injection**: Never use @Autowired on fields - always use constructor injection  
❌ **Mutable Domain Objects**: Don't expose public fields or setters without validation  
❌ **Missing Validation**: Always validate input data at service layer boundaries  
❌ **Thread Safety Issues**: Ensure repository implementation is thread-safe  
❌ **Poor Configuration**: Avoid hardcoded values - use externalized configuration  
❌ **Insufficient Testing**: Don't skip edge cases and error scenarios in tests

## Submission Checklist

### Required Deliverables
- [ ] Complete Spring Boot project with all source code
- [ ] Maven POM file with correct dependencies and configuration
- [ ] Application properties files for all profiles (dev, test, prod)
- [ ] Comprehensive test suite with good coverage
- [ ] README file with setup and running instructions
- [ ] All functionality working as specified

### Code Quality Checks
- [ ] No @Autowired field injection used anywhere
- [ ] All services use constructor injection with @RequiredArgsConstructor
- [ ] Proper Lombok annotations used throughout
- [ ] Input validation implemented at service boundaries
- [ ] Thread-safe repository implementation verified
- [ ] All tests pass and provide good coverage

### Functionality Verification
- [ ] Application starts successfully with all profiles
- [ ] All CRUD operations work correctly
- [ ] Business rules properly enforced
- [ ] Notifications and audit logging functional
- [ ] Configuration properties properly externalized
- [ ] Sample data loads correctly on startup

This assignment establishes the foundation for our progressive Task Management System. Focus on understanding Spring's core concepts of IoC and DI, as these principles will be essential throughout the entire course.
