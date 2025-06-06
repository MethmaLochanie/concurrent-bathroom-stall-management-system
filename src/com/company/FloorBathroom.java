package com.company;

import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.ThreadLocalRandom;

public class FloorBathroom {
    private static final int BATHROOM_STALLS = 6;
    private static final int NUM_EMPLOYEES = 100;

    // Enum for user type
    enum UserType {
        EMPLOYEE, STUDENT
    }

    public static void main(String[] args) {
        // Create semaphore to manage bathroom stalls
        Semaphore stalls = new Semaphore(BATHROOM_STALLS, true);

        // Track stall usage and total usage time
        boolean[] stallOccupied = new boolean[BATHROOM_STALLS];
        int[] stallUsage = new int[BATHROOM_STALLS];
        long[] totalUsageTime = new long[BATHROOM_STALLS];
        ReentrantLock lock = new ReentrantLock(true);

        // Create a thread pool to manage threads
        ExecutorService executor = Executors.newFixedThreadPool(10);

        // Submit tasks to the thread pool
        for (int i = 0; i < NUM_EMPLOYEES; i++) {
            final int personId = i + 1;

            executor.submit(() -> {
                UserType userType = ThreadLocalRandom.current().nextBoolean() ? UserType.EMPLOYEE : UserType.STUDENT;
                try {
                    // Simulate real-life delay between arrivals
                    Thread.sleep(ThreadLocalRandom.current().nextInt(50, 200));

                    System.out.printf("[%s #%d] Waiting for a stall...\n", userType, personId);
                    stalls.acquire();

                    // Find and occupy an available stall
                    int stallId = -1;
                    lock.lock();
                    try {
                        for (int j = 0; j < BATHROOM_STALLS; j++) {
                            if (!stallOccupied[j]) {
                                stallOccupied[j] = true;
                                stallId = j;
                                // Increment usage count for the stall
                                stallUsage[stallId]++;
                                break;
                            }
                        }
                    } finally {
                        lock.unlock();
                    }

                    // Error Handling: Check if a valid stall was found
                    if (stallId == -1) {
                        System.err.printf("[ERROR] [%s #%d] No stall found after acquiring semaphore. Releasing semaphore.\n", userType, personId);
                        stalls.release(); // Release the semaphore to avoid deadlock
                        return;
                    }

                    // Use the stall
                    System.out.printf("[%s #%d] Using stall #%d...\n", userType, personId, stallId + 1);
                    long usageTime = userType == UserType.EMPLOYEE
                            ? ThreadLocalRandom.current().nextInt(2000, 4000)
                            : ThreadLocalRandom.current().nextInt(1000, 3000);
                    Thread.sleep(usageTime);

                    // Track total usage time
                    totalUsageTime[stallId] += usageTime;

                    // Release the stall
                    lock.lock();
                    try {
                        stallOccupied[stallId] = false;
                        System.out.printf("[%s #%d] Left stall #%d.\n", userType, personId, stallId + 1);
                    } finally {
                        lock.unlock();
                    }
                    stalls.release();

                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    // Improved Interruption Logging
                    if (Thread.currentThread().isInterrupted()) {
                        System.out.printf("[%s #%d] Interrupted while waiting for a stall.\n", userType, personId);
                    } else {
                        System.out.printf("[%s #%d] Interrupted while using stall.\n", userType, personId);
                    }
                }
            });
        }

        // Shutdown the thread pool and wait for tasks to complete
        executor.shutdown();
        try {
            executor.awaitTermination(1, TimeUnit.HOURS);
        } catch (InterruptedException e) {
            System.out.println("Main thread was interrupted while waiting for tasks to complete.");
        }

        // Display stall usage statistics
        System.out.println("\n=== SIMULATION COMPLETED ===");
        System.out.println("\n=== STALL USAGE STATISTICS ===");
        for (int i = 0; i < BATHROOM_STALLS; i++) {
            System.out.printf("Stall #%d: Used %d times, Total Usage Time: %d ms\n",
                    i + 1, stallUsage[i], totalUsageTime[i]);
        }
        System.out.println("=============================");
    }
}
