# Case Study Scenarios to discuss

## Scenario 1: Cost Allocation and Tracking
**Situation**: The company needs to track and allocate costs accurately across different Warehouses and Stores. The costs include labor, inventory, transportation, and overhead expenses.

**Task**: Discuss the challenges in accurately tracking and allocating costs in a fulfillment environment. Think about what are important considerations for this, what are previous experiences that you have you could related to this problem and elaborate some questions and considerations

**Questions you may have and considerations:**

Accurately tracking costs across warehouses and stores is challenging due to shared resources (labor, space) that need fair apportionment, real-time data from multiple systems (WMS, TMS, payroll) with different SLAs, and retroactive adjustments from returns/chargebacks. 
**Key considerations** Choosing an allocation model (activity-based vs. volume-based), ensuring idempotent event-driven cost accrual, and using BigDecimal for precision. From experience, applying event sourcing and strategy patterns for pluggable allocation rules works well. 

**Critical questions**: 
Who consumes this data? 
What's the reconciliation frequency? 
How do we handle split shipments across warehouses?


## Scenario 2: Cost Optimization Strategies
**Situation**: The company wants to identify and implement cost optimization strategies for its fulfillment operations. The goal is to reduce overall costs without compromising service quality.

**Task**: Discuss potential cost optimization strategies for fulfillment operations and expected outcomes from that. How would you identify, prioritize and implement these strategies?

**Questions you may have and considerations:**

To identify optimization opportunities, I'd start by analyzing cost drivers (labor, transport, inventory holding) through data profiling and pinpointing the highest-spend areas. 
**Key strategies** Inventory placement optimization (stocking closer to demand to reduce shipping costs), labor scheduling based on demand forecasting, carrier rate negotiation 
with multi-carrier routing, and batch processing for picks/packs to improve throughput. Prioritization should follow effort vs. impact matrix — quick wins like route optimization first, 
then structural changes like warehouse automation. Implementation should be incremental with A/B testing to measure impact without risking service quality. 

**Expected outcomes**: 15-25% reduction in transport costs, improved labor utilization, and faster fulfillment cycles while maintaining SLAs

**Critical questions**:
What are the current biggest cost drivers — is it labor, transportation, or inventory holding costs?
Do we have baseline metrics/KPIs (cost-per-order, cost-per-unit) to measure optimization impact against?
What are the non-negotiable SLAs — same-day delivery, accuracy rates — that we cannot compromise?

## Scenario 3: Integration with Financial Systems
**Situation**: The Cost Control Tool needs to integrate with existing financial systems to ensure accurate and timely cost data. The integration should support real-time data synchronization and reporting.

**Task**: Discuss the importance of integrating the Cost Control Tool with financial systems. What benefits the company would have from that and how would you ensure seamless integration and data synchronization?

**Questions you may have and considerations:**

Integrating the Cost Control Tool with financial systems ensures single source of truth for cost data, eliminates manual reconciliation errors, and enables real-time financial visibility for decision-making. 
Benefits include automated cost posting to GL, faster month-end closing, and accurate P&L per warehouse/store. For seamless integration, I'd use event-driven architecture with APIs (REST/Kafka) for real-time sync, 
implement idempotent transactions to handle failures, and establish data validation/reconciliation jobs to catch discrepancies. 

**Key questions**: 
What financial systems exist (SAP, Oracle)? 
What's the data format and frequency — real-time or batch? 
Who owns the master data — Cost Tool or financial system? Are there compliance requirements (SOX) affecting how data flows

## Scenario 4: Budgeting and Forecasting
**Situation**: The company needs to develop budgeting and forecasting capabilities for its fulfillment operations. The goal is to predict future costs and allocate resources effectively.

**Task**: Discuss the importance of budgeting and forecasting in fulfillment operations and what would you take into account designing a system to support accurate budgeting and forecasting?

**Questions you may have and considerations:**

Budgeting and forecasting are critical for proactive cost management — predicting labor needs, inventory investments, and transport spend before they become problems. When designing this system, 
I'd account for seasonality patterns (peak seasons, promotions), historical cost trends, and external factors (fuel prices, carrier rate changes) as key inputs. The system should support rolling 
forecasts over fixed annual budgets for agility, with variance analysis comparing actual vs. predicted to continuously improve accuracy. Technically, I'd leverage time-series data models, 
integrate with existing cost tracking and financial systems, and provide scenario modeling (best/worst/expected case). 

**Key questions**: 
What's the forecasting horizon — monthly, quarterly? 
Do we have enough historical data to build reliable models? 
Who are the consumers — finance, operations, or both? 
How do we handle unexpected events like supply chain disruptions?

## Scenario 5: Cost Control in Warehouse Replacement
**Situation**: The company is planning to replace an existing Warehouse with a new one. The new Warehouse will reuse the Business Unit Code of the old Warehouse. The old Warehouse will be archived, but its cost history must be preserved.

**Task**: Discuss the cost control aspects of replacing a Warehouse. Why is it important to preserve cost history and how this relates to keeping the new Warehouse operation within budget?

**Questions you may have and considerations:**

Preserving cost history is critical for benchmarking the new warehouse's performance against the old one, ensuring budget targets are realistic and grounded in actual data. Since the Business Unit Code is reused, the system must logically 
separate old vs. new warehouse cost records (e.g., versioning or effective dating) to avoid data contamination while maintaining reporting continuity. Cost control during replacement involves tracking transition costs (migration, parallel 
operations, ramp-up inefficiencies) as a separate category to avoid skewing ongoing operational budgets. The archived warehouse data serves compliance, audit trails, and trend analysis for future planning. 

**Key questions**: 
What's the transition period where both warehouses operate simultaneously? 
How do we handle in-flight orders and their cost attribution during cutover? 
Should historical reports reflect the old warehouse separately or merged under the same Business Unit Code? 
What retention policies apply to archived cost data?


## Instructions for Candidates
Before starting the case study, read the BRIEFING.md(BRIEFING.md) to quickly understand the domain, entities, business rules, and other relevant details.

**Analyze the Scenarios**: Carefully analyze each scenario and consider the tasks provided. To make informed decisions about the project's scope and ensure valuable outcomes, what key information would you seek to gather before defining the boundaries of the work? Your goal is to bridge technical aspects with business value, bringing a high level discussion; no need to deep dive.
