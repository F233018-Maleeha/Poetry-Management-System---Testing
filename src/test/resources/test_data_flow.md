# Test Data Flow Documentation

## CSV Files Connected to Test Suite

### CSV Files Used:
- book_test_data.csv - has 9 test cases
- book_boundary_data.csv - has 4 test cases

### How They Connect to Tests:
- BookDTOTest.java has 2 special tests with @CsvFileSource
- These tests automatically read from the CSV files
- Each row in CSV becomes a separate test run

### Proof It Works:
- Without CSV: 22 tests run
- With CSV: 37 tests run (22 + 15 from CSV rows)
- This proves CSV files are feeding into tests