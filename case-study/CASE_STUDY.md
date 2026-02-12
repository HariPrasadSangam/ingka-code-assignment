# Case Study Scenarios to discuss

## Scenario 1: Cost Allocation and Tracking
**Situation**: The company needs to track and allocate costs accurately across different Warehouses and Stores. The costs include labor, inventory, transportation, and overhead expenses.

**Task**: Discuss the challenges in accurately tracking and allocating costs in a fulfillment environment. Think about what are important considerations for this, what are previous experiences that you have you could related to this problem and elaborate some questions and considerations

**Questions you may have and considerations:**

**Key Challenges** :
**Complex Cost Drivers**: Multiple cost categories (labor, inventory, transportation, overhead) with different allocation bases
**Variable vs Fixed Costs**: Distinguishing between costs that scale with volume vs. static operational costs
**Cross-functional Dependencies**: Costs shared between warehouses and stores requiring fair allocation methodologies

**Important Considerations**:

**Activity-Based Costing (ABC)**: Allocate costs based on actual activities consumed rather than simple averages
**Cost Pools**: Group similar costs together for more accurate allocation (e.g., all receiving costs, all picking costs)

**Questions to Clarify**:

What is the required frequency for cost allocation reporting (daily, weekly, monthly)?
How should shared services costs (IT, HR, finance) be allocated across locations?


## Scenario 2: Cost Optimization Strategies
**Situation**: The company wants to identify and implement cost optimization strategies for its fulfillment operations. The goal is to reduce overall costs without compromising service quality.

**Task**: Discuss potential cost optimization strategies for fulfillment operations and expected outcomes from that. How would you identify, prioritize and implement these strategies?

**Questions you may have and considerations:**

**Potential Cost Optimization Strategies:**

1. **Transportation Optimization**: Route optimization and load consolidation

2. **Inventory Management**: Safety stock optimization based on demand variability

3. **Labor Productivity**: Workforce management, scheduling optimization and Automation opportunities for repetitive tasks

4. **Warehouse Operations**: Slotting optimization for reduced travel time, Energy efficiency and facility utilization and Identification and Prioritization Process:

**Expected Outcomes**:

Short-term: 5-15% cost reduction through operational efficiencies
Medium-term: 15-25% reduction through process redesign and technology
Long-term: 25%+ reduction through network optimization and automation

**Implementation Considerations**:

Pilot programs to validate assumptions before full rollout
Change management and employee training
Continuous monitoring and adjustment of strategies
Integration with existing systems and processes

**Questions to Clarify**:

What are the current cost benchmarks and industry standards?
What is the company's risk tolerance for service level impacts?
What budget is available for optimization initiatives?


## Scenario 3: Integration with Financial Systems
**Situation**: The Cost Control Tool needs to integrate with existing financial systems to ensure accurate and timely cost data. The integration should support real-time data synchronization and reporting.

**Task**: Discuss the importance of integrating the Cost Control Tool with financial systems. What benefits the company would have from that and how would you ensure seamless integration and data synchronization?

**Questions you may have and considerations:**
 
**Importance of Financial System Integration**:

Benefits:

Real-time Visibility: Immediate access to cost data for decision-making
Data Consistency: Single source of truth eliminates reconciliation issues
Automated Reporting: Streamlined financial reporting and compliance
Improved Accuracy: Reduced manual data entry errors and delays
Better Forecasting: Historical cost data enables more accurate predictions

Ensuring Seamless Integration:

**Technical Considerations**:

API Architecture: RESTful APIs for secure, scalable data exchange
Data Validation: Business rules to ensure data quality and consistency
Error Handling: Robust exception management and retry mechanisms

**Data Governance**: Clear data ownership, Data quality standards and validation rules 

**Questions to Clarify**:

What financial systems are currently in use (ERP, GL, reporting tools)?
What is the required frequency for data synchronization?
What are the data format and protocol requirements?

## Scenario 4: Budgeting and Forecasting
**Situation**: The company needs to develop budgeting and forecasting capabilities for its fulfillment operations. The goal is to predict future costs and allocate resources effectively.

**Task**: Discuss the importance of budgeting and forecasting in fulfillment operations and what would you take into account designing a system to support accurate budgeting and forecasting?

**Questions you may have and considerations:**
**Importance of Budgeting and Forecasting**:

**Strategic Benefits**:

Resource Allocation: Optimize distribution of capital and operational resources
Performance Management: Establish benchmarks and variance analysis

**System Design Considerations**:

1. Data Foundation: Historical cost data with appropriate seasonality adjustments
2. Forecasting Methodology:
   Time Series Analysis: Trend, seasonal, and cyclical components
   Scenario Planning: Best case, worst case, and most likely scenarios
3. Budget Management:
   Bottom-up Budgeting: Department-level input with top-down validation
   Flexible Budgeting: Adjust for volume and activity variations

**Key Design Elements**:

Scalability: Handle multiple locations, products, and time periods
Flexibility: Accommodate different business models and cost structures
User Experience: Intuitive interfaces for non-finance users

## Scenario 5: Cost Control in Warehouse Replacement
**Situation**: The company is planning to replace an existing Warehouse with a new one. The new Warehouse will reuse the Business Unit Code of the old Warehouse. The old Warehouse will be archived, but its cost history must be preserved.

**Task**: Discuss the cost control aspects of replacing a Warehouse. Why is it important to preserve cost history and how this relates to keeping the new Warehouse operation within budget?

**Questions you may have and considerations:**

**Cost Control Importance in Warehouse Replacement**:

**Why Preserve Cost History**:

Performance Benchmarking: Historical data provides baseline for new warehouse performance
Trend Analysis: Identify seasonal patterns and cost trends for the business unit
Budget Validation: Compare new warehouse costs against historical averages

**Budget Control Considerations**:

1. **Cost Baseline Establishment**:

Extract historical cost patterns from the archived warehouse
Adjust for inflation, market changes, and operational differences

2. **Transition Cost Management**:

Dual Operations: Costs during overlap period when both warehouses operate
Migration Expenses: System integration, equipment transfer, and relocation costs

3. **Performance Monitoring**:

Early Warning Systems: Identify cost overruns quickly in the new facility
Variance Analysis: Compare actual costs against historical benchmarks

4. **Business Unit Code Continuity**:

Cost Attribution: Maintain clear linkage between historical and current costs
Reporting Consistency: Ensure financial reports show continuous cost tracking

**Implementation Challenges:**

Data Migration: Ensuring accurate transfer of cost data and allocation rules
System Integration: Updating all systems to recognize the new warehouse

**Key Success Factors**:

Comprehensive Planning: Detailed cost transition plan with clear timelines
Stakeholder Communication: Regular updates on cost performance during transition

**Questions to Clarify**:

What is the expected timeline for the warehouse replacement process?
How will dual operation costs be handled during the transition period?
What cost variances are acceptable between the old and new warehouses?



## Instructions for Candidates
Before starting the case study, read the BRIEFING.md(BRIEFING.md) to quickly understand the domain, entities, business rules, and other relevant details.

**Analyze the Scenarios**: Carefully analyze each scenario and consider the tasks provided. To make informed decisions about the project's scope and ensure valuable outcomes, what key information would you seek to gather before defining the boundaries of the work? Your goal is to bridge technical aspects with business value, bringing a high level discussion; no need to deep dive.
