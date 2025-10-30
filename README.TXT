# 📊 COVID-19 Statistics Viewer

> Interactive JavaFX application for visualising and analysing COVID-19 data across London boroughs

![Java](https://img.shields.io/badge/Java-ED8B00?logo=openjdk&logoColor=white)
![JavaFX](https://img.shields.io/badge/JavaFX-17+-blue)
![License](https://img.shields.io/badge/License-MIT-green)

## Overview

Desktop application that visualises COVID-19 statistics for London boroughs using interactive maps, dynamic charts, and comparative analytics. The tool processes historical pandemic data to reveal patterns in cases, deaths, and mobility changes across different geographical regions.

## Features

### 📍 Interactive Map Visualisation
- **Colour-coded hexagonal map** of London boroughs based on death rates
- **Click-through functionality** to view detailed borough statistics
- **Dynamic colour gradients** showing severity levels (green to red scale)
- **Real-time data filtering** by custom date ranges

### 📈 Statistical Analysis Dashboard
- **Total borough deaths** aggregated across selected time period
- **Average daily cases** with trend analysis
- **Peak infection dates** highlighting critical periods
- **Google Mobility Reports (GMR)** tracking:
  - Workplace mobility changes
  - Residential activity patterns
  - Retail and recreation trends
  - Transit usage fluctuations

### 🗂️ Detailed Data Tables
- **Sortable data views** for each borough
- **Multi-column sorting** by any metric
- **Complete historical records** including:
  - Daily new cases and deaths
  - Cumulative totals
  - Mobility metrics across 6 categories
  - Temporal analysis capabilities

### 🔄 Multi-Panel Navigation
- **Four distinct views**: Welcome, Map, Statistics, and Custom panels
- **Seamless transitions** between analysis modes
- **Date range validation** with user-friendly error handling
- **Responsive UI** adapting to window resizing

## Technical Stack

- **Language:** Java
- **UI Framework:** JavaFX
- **Architecture:** Model-View-Controller (MVC)
- **Data Processing:** OpenCSV for CSV parsing
- **Design Patterns:** Abstract Factory, Observer

## Architecture

```
src/
├── ApplicationWindow.java      # Main application entry point
├── MainController.java          # Central navigation and date management
├── AbstractController.java      # Base controller with shared functionality
├── CovidData.java              # Data model for COVID records
├── CovidDataLoader.java        # CSV parsing and data aggregation
├── WelcomeController.java      # Landing screen controller
├── MapController.java          # Interactive map visualisation
├── StatisticsController.java   # Statistical dashboard controller
├── mapTableController.java     # Detailed borough data table
├── FourthController.java       # Additional panel controller
└── windowGUI.fxml              # Main UI layout definition
```

## Data Model

Each COVID record contains:
- **Temporal:** Date of recording
- **Geographical:** London borough name
- **Health Metrics:** 
  - New cases and deaths (daily)
  - Total cases and deaths (cumulative)
- **Mobility Data:**
  - Retail & Recreation GMR
  - Grocery & Pharmacy GMR
  - Parks GMR
  - Transit GMR
  - Workplaces GMR
  - Residential GMR

## Key Functionality

### Date Range Selection
```java
// Validates user-selected date ranges
// Ensures dates fall within available data (Feb 2020 - Feb 2023)
// Prevents invalid selections (e.g., end date before start date)
```

### Dynamic Map Colouring
```java
// Calculates min/max death rates across boroughs
// Applies colour gradient based on relative severity
// Updates instantly when date range changes
```

### Statistical Calculations
- **Aggregation algorithms** for temporal data
- **Average computations** across multiple days
- **Peak detection** for identifying critical dates
- **Trend analysis** using mobility reports

## Installation & Setup

### Prerequisites
```bash
Java JDK 11+
JavaFX SDK 17+
OpenCSV library
```

### Running the Application

```bash
# Clone the repository
git clone https://github.com/FardeenIdrus/CovidSimulator.git

# Navigate to project directory
cd CovidSimulator

# Compile with JavaFX
javac --module-path /path/to/javafx-sdk/lib --add-modules javafx.controls,javafx.fxml *.java

# Run the application
java --module-path /path/to/javafx-sdk/lib --add-modules javafx.controls,javafx.fxml ApplicationWindow
```

### Dependencies

Add to classpath:
- `javafx.controls`
- `javafx.fxml`
- `opencsv-5.x.jar`

## Technical Highlights

### MVC Architecture
- **Models:** Encapsulate COVID data and business logic
- **Views:** FXML-based declarative UI with custom styling
- **Controllers:** Handle user interactions and data updates

### Design Patterns

**Abstract Factory Pattern**
```java
AbstractController base class provides:
- Shared data loader instance
- Common date handling logic
- Standard view management
```

**Observer Pattern**
```java
Controllers observe date picker changes
Update views dynamically when date range modified
```

### Data Processing
- **Efficient CSV parsing** with OpenCSV
- **HashMap-based storage** for O(1) date lookups
- **Lazy loading** of borough-specific data
- **In-memory caching** for performance

## Code Quality Features

✅ **Input validation** for all user-provided dates  
✅ **Error handling** with user-friendly alert dialogues  
✅ **Null safety** checks throughout data processing  
✅ **Modular design** enabling easy feature additions  
✅ **Consistent styling** via CSS integration

## Future Enhancements

- [ ] Export functionality for filtered data
- [ ] Predictive modelling using historical trends
- [ ] Comparison with other UK regions
- [ ] API integration for real-time data updates
- [ ] Advanced charting (line graphs, bar charts)
- [ ] Correlation analysis between mobility and cases

## Data Source

COVID-19 statistics and Google Mobility Reports for London boroughs covering the period from February 2020 to February 2023.

---

**Developed by Fardeen Idrus**

*A comprehensive tool for understanding pandemic patterns through data visualisation and statistical analysis.*

