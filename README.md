# Bathroom Stall Management System

This Java project simulates a concurrent bathroom stall management system where multiple users (employees and students) compete for access to a limited number of bathroom stalls. The system demonstrates the implementation of concurrent programming concepts using Java's concurrency utilities.

## Project Structure

```
Scenario3/
├── src/
│   └── com/
│       └── company/
│           └── FloorBathroom.java
├── out/
├── .idea/
└── Scenario3.iml
```

## Features

- Simulates multiple users (employees and students) accessing bathroom stalls
- Implements thread-safe stall management using semaphores
- Tracks stall usage statistics and timing
- Handles concurrent access with proper synchronization
- Provides detailed logging of user activities
- Implements error handling for edge cases

## Technical Details

- Uses Java's concurrency utilities:
  - `Semaphore` for managing stall access
  - `ReentrantLock` for thread-safe operations
  - `ExecutorService` for thread pool management
  - `ThreadLocalRandom` for random timing simulation

## Configuration

The system is configured with the following parameters:
- Number of bathroom stalls: 6
- Number of simulated users: 100
- Thread pool size: 10
- Usage time ranges:
  - Employees: 2000-4000ms
  - Students: 1000-3000ms

## Running the Project

1. Ensure you have Java installed on your system
2. Compile the project:
   ```bash
   javac src/com/company/FloorBathroom.java
   ```
3. Run the program:
   ```bash
   java -cp src com.company.FloorBathroom
   ```

## Usage Example

When you run the program, you'll see output similar to this:

```
[EMPLOYEE #1] Waiting for a stall...
[STUDENT #2] Waiting for a stall...
[EMPLOYEE #1] Using stall #1...
[STUDENT #2] Using stall #2...
[EMPLOYEE #1] Left stall #1.
[STUDENT #2] Left stall #2.
...
=== SIMULATION COMPLETED ===

=== STALL USAGE STATISTICS ===
Stall #1: Used 15 times, Total Usage Time: 45000 ms
Stall #2: Used 18 times, Total Usage Time: 52000 ms
Stall #3: Used 16 times, Total Usage Time: 48000 ms
Stall #4: Used 17 times, Total Usage Time: 51000 ms
Stall #5: Used 19 times, Total Usage Time: 55000 ms
Stall #6: Used 15 times, Total Usage Time: 46000 ms
=============================
```

The output shows:
1. Real-time logging of users waiting for and using stalls
2. Different user types (EMPLOYEE/STUDENT) with unique IDs
3. Stall usage tracking
4. Final statistics showing usage patterns

## Output

The program provides real-time logging of:
- User arrival and waiting status
- Stall usage
- User departure
- Final statistics including:
  - Number of times each stall was used
  - Total usage time per stall

## Error Handling

The system includes error handling for:
- Interrupted threads
- Stall allocation failures
- Concurrent access conflicts

## Author

Created as part of a concurrent programming assignment. 