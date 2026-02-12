# Questions

Here we have 3 questions related to the code base for you to answer. It is not about right or wrong, but more about what's the reasoning behind your decisions.

1. In this code base, we have some different implementation strategies when it comes to database access layer and manipulation. If you would maintain this code base, would you refactor any of those? Why?

**Answer:**
```txt
Yes, I would refactor the database access layer for consistency and maintainability. The codebase currently uses three different approaches:

1. **Products**: Simple PanacheRepository pattern with direct entity access
2. **Stores**: Direct PanacheEntity usage with static methods (Store.listAll(), Store.findById())
3. **Warehouses**: Clean architecture with domain ports/adapters pattern and separate DbWarehouse entity

**Refactoring approach:**
- Standardize on the clean architecture pattern used in warehouses for all modules
- Create consistent domain models separate from database entities
- Implement repository interfaces with clear domain operations

```
----
2. When it comes to API spec and endpoints handlers, we have an Open API yaml file for the `Warehouse` API from which we generate code, but for the other endpoints - `Product` and `Store` - we just coded directly everything. What would be your thoughts about what are the pros and cons of each approach and what would be your choice?

**Answer:**
```txt
**OpenAPI Code Generation (Warehouse approach):**

*Pros:*
- Contract-first development ensures API consistency
- Automatic documentation generation
- Type safety and reduced boilerplate
- Standardized error handling and validation
- Better team collaboration with clear API contracts

*Cons:*
- Learning curve for OpenAPI specification
- Generated code can be verbose
- Additional build complexity

**Direct Coding (Product/Store approach):**

*Pros:*
- Faster development for simple endpoints
- Full control over implementation
- No additional tooling dependencies
- Easier debugging and customization
- Simpler build process

*Cons:*
- Inconsistent API patterns across modules
- Manual documentation maintenance
- Higher risk of API contract violations
- No automatic client generation
- Potential for inconsistent error handling

**My choice:** I would standardize on OpenAPI code generation for all APIs. The benefits of consistency, automatic documentation, and contract-first development outweigh the initial setup complexity, especially in a team environment where API contracts are crucial for frontend integration and long-term maintainability.
```
----
3. Given the need to balance thorough testing with time and resource constraints, how would you prioritize and implement tests for this project? Which types of tests would you focus on, and how would you ensure test coverage remains effective over time?

**Answer:**
```txt
**Testing Priority Pyramid:**

1. **Unit Tests (Highest Priority):**
   - Domain logic and business rules (warehouse operations, validation)
   - Repository patterns and data mappers
   - Use case implementations
   - Fast feedback loop, high ROI

2. **Integration Tests (Medium Priority):**
   - API endpoints with real database (@QuarkusTest)
   - Repository-database interactions
   - External gateway integrations
   - Current REST-assured tests are good foundation

**Implementation Strategy:**
- Start with 80% unit test coverage for domain logic
- Add integration tests for all API endpoints
- Set up CI/CD with coverage thresholds (minimum 70% unit, 60% integration)

**Maintaining Effectiveness:**
- Automate coverage reporting in CI/CD
- Require tests for all new features
- Regular test maintenance sprints

Focus on the warehouse module's testing approach as the template - it has good separation of concerns making it highly testable.
```