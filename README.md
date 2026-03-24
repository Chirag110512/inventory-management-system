# 📦 Inventory Management System

A command-line Java application for managing product inventory. Supports full CRUD operations, low-stock alerts, category-wise reporting, and CSV export — with automatic data persistence between sessions.

---

## Features

- **Add / Update / Delete** products with auto-generated IDs
- **Search** by product name (keyword) or category
- **Low Stock Alerts** — flag items below a custom quantity threshold
- **Reports** — full inventory view, category-wise summary, and CSV export with timestamps
- **Auto-save** — a JVM shutdown hook writes all changes to `data/products.csv` on exit (including `Ctrl+C`)

---

## Project Structure

```
inventory-management-system/
├── data/
│   └── products.csv          # Persistent data store (auto-created on first run)
├── out/                      # Compiled .class files (auto-generated)
├── src/
│   ├── Main.java             # Entry point — wires services and starts the menu
├── model/
│   ├── Product.java          # Product entity (id, name, category, quantity, price)
│   └── Category.java         # Category entity
├── service/
│   ├── ProductService.java   # CRUD logic + search
│   ├── InventoryService.java # Stock value, low-stock filtering, category summary
│   └── ReportService.java    # Console reporting and CSV export delegation
├── ui/
│   └── MenuHandler.java      # CLI menu loop and user input handling
└── util/
    ├── FileHandler.java      # CSV read/write for data/products.csv
    └── CSVExporter.java      # Timestamped report export to reports/ directory
```

---

## Prerequisites

| Requirement | Version  |
|-------------|----------|
| Java JDK    | 8 or higher |
| Terminal / Command Prompt | Any |

Verify your Java installation:
```bash
java -version
javac -version
```

---

## Setup & Compilation

**1. Clone the repository**
```bash
git clone https://github.com/your-username/inventory-management-system.git
cd inventory-management-system
```

**2. Create the output directory**
```bash
mkdir -p out
```

**3. Compile all source files**
```bash
javac -d out model/Product.java model/Category.java \
             util/FileHandler.java util/CSVExporter.java \
             service/InventoryService.java service/ProductService.java service/ReportService.java \
             ui/MenuHandler.java \
             src/Main.java
```

> **Note:** The compilation order matters because Java resolves dependencies at compile time. Compile model and util classes before services, and services before the UI.

---

## Running the Application

```bash
java -cp out com.inventory.Main
```

On first run, if `data/products.csv` doesn't exist, the system starts fresh and creates it automatically when you exit.

**Expected output:**
```
[INFO] Loaded 8 products from data/products.csv

==========================================
   INVENTORY MANAGEMENT SYSTEM v1.0
==========================================

------------------------------------------
  1. View All Products
  2. Add Product
  3. Update Product
  4. Delete Product
  5. Search Product
  6. Low Stock Alert
  7. Reports & Export
  8. Exit
------------------------------------------
Enter choice:
```

---

## Usage Guide

### 1 — View All Products
Displays the full product list in a formatted table with ID, name, category, quantity, and price.

### 2 — Add Product
Prompts for name, category, quantity, and price. The system auto-generates a sequential ID (e.g., `P009`).

### 3 — Update Product
Enter the product ID, then provide new values. Press **Enter** to keep an existing value unchanged.

### 4 — Delete Product
Enter the product ID and confirm with `y`. The deletion is permanent once you exit.

### 5 — Search Product
Choose to search by:
- **Name** — substring/keyword match (case-insensitive)
- **Category** — exact match (case-insensitive)

### 6 — Low Stock Alert
Enter a quantity threshold. All products with stock **at or below** that number are listed.

### 7 — Reports & Export
Three sub-options:
- **Full Inventory Report** — table with total product count and aggregate stock value
- **Category-wise Summary** — total quantity per category, sorted alphabetically
- **Export to CSV** — writes a timestamped file to `reports/report_YYYYMMDD_HHmmss.csv`, including a grand total row

### 8 — Exit
Triggers the shutdown hook, saves all changes to `data/products.csv`, and exits.

---

## Data Format

`data/products.csv` uses a simple 5-column format:

```
id,name,category,quantity,price
P001,Wireless Mouse,Electronics,45,599.0
P002,USB Keyboard,Electronics,30,899.0
P003,A4 Notebook,Stationery,120,45.0
```

You can pre-populate this file manually before running the application — just follow the same format and ensure IDs are unique.

---

## Sample Data

The repository ships with 8 sample products across three categories (Electronics, Stationery, Furniture) to let you explore the features immediately without adding data first.

---

## Notes

- Prices are displayed with the `Rs.` prefix (Indian Rupee). To change the currency symbol, update the `toString()` format string in `model/Product.java`.
- The `reports/` directory is created automatically on first CSV export.
- All changes are held in memory during a session — they are only written to disk on exit (either menu option 8, or `Ctrl+C`).
