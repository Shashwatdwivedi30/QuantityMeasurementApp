# QuantityMeasurementApp
---

This log documents the daily progress of tasks completed during the Quantity Measurement App development, identifying work done on each date with thematic headings and detailed summaries.

---

Folder Structure The repository is organized in a modular way following the standard Maven directory structure.

Currently, the application code resides in the src/main/java directory, and the test code resides in the src/test/java directory.

```
QuantityMeasurementApp/
|
+-- src/main/java (Application Code)
|
+-- src/test/java (Test Cases)
|
+-- pom.xml (Maven Configuration)
|
+-- README.md
```

### Week 1: Quantity Measurement System TDD Approach & Unit Conversion
---

#### 18-Feb-2026 (Wednesday): 
-Created the repository and set up the project structure. Started implementing the Quantity Measurement System using Test Driven Development (TDD). Addressed UC1 (Feet Equality) to handle feet measurement comparisons. Extended functionality to support Inches Equality (UC2), allowing comparison of inch values with 100% test coverage.<br>

[Browse UC1 Implementation](https://github.com/Shashwatdwivedi30/QuantityMeasurementApp/tree/feature/UC1-FeetEquality)<br>
[Browse UC2 Implementation](https://github.com/Shashwatdwivedi30/QuantityMeasurementApp/tree/feature/UC2-FeetAndInchesMeasurementEquality)

---

#### 19-Feb-2026 (Thursday): 
-Refactored the code to use a generic Quantity class and LengthUnit enum (UC3), implementing the DRY principle. Extended the system to support Yard and Centimeter units (UC4) with comprehensive test coverage for cross-unit comparisons.<br>

[Browse UC3 Implementation](https://github.com/Shashwatdwivedi30/QuantityMeasurementApp/tree/feature/UC3-GenericQuantity)<br>
[Browse UC4 Implementation](https://github.com/Shashwatdwivedi30/QuantityMeasurementApp/tree/feature/UC4-ExtendedUnitSupport)


