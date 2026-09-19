# Zip Ride Dispatch System

### 1. TO RUN THE PROGRAM
You have to compile and run, to do so,
#### i. type in "javac *.java" | 
ii. type in "java Menu"

---

## Module 1: Graph-Based Route Planning 
#### steps to run:
1. Select the graph module
2. Test BFS
3. Test DFS
4. Test Dijkstra
#### Expecting output:
1. Graph being displayed
2. Traversal of BFS and DFS
3. Shows the shortest path

---

## Module 2: Hash-Based Passenger and Driver Lookup
#### Steps to run:
1. Load passengers.csv / drivers.csv
2. Search passenger /driver
3. Add passenger / driver
4. Delete passenger / driver
#### Expected output:
1. Success insertion 
2. Proper collision handling
3. Linear probing

---

## Module 3: Heap-Based Pickup Scheduling 
#### Steps to run:
1. Load passenger & driver records
2. Select Ride Scheduling
3. Create ride requests
4. Insert ride requests into heap
5. Display heap
6. Peek highest-priority ride
7. Dispatch ride request
---

# PRIORITY EQUATION:
#### Priority= (6 - MembershipTier) + (1000 / PickupTime)
---

Where:
MembershipTier 1 = Highest Membership and membershipTier 5 = Lowest Membership
- PickupTime = Estimated pickup time from driver to passenger

#### Expected output:
1. Heap state displayed
2. Ride priorities calculated
3. Highest-priority ride dispatched first
4. Correct heap ordering maintained

#### To do above,
- Passenger and driver records must be loaded before creating ride requests
- Road network must exist before pickup times can be calculated

---

## Module 4: Sorting Pickup Requests 
#### Steps to run:
1. Select Sorting Analysis
2. Run sorting benchmark
3. View performance results
#### Dataset sizes implemeneted:
- 100 Ride Requests
- 500 Ride Requests
- 1000 Ride Requests
#### Dataset conditions implemented:
- Random
- Nearly Sorted
- Reversed
#### Expected output:
1. Execution time displayed using nanoseconds(ns)
2. Comparison counts are displayed
3. Performance comparison between merge and quick sort
4. Results for all dataset conditions

---

## CSV files created are:
1. road.csv: 
* Contains road connections and travel times between locations.
2. passengers.csv:
* PassengerID,PassengerName,PickupLocation,MembershipTier
3. drivers.csv:
* DriverID,DriverName,Location,Status

---

## To test in brief...
1. Compile the project
2. Run Menu.java
3. Load graph data
4. Load passenger records
5. Load driver records
6. Test BFS and DFS
7. Test shortest path calculation
8. Create ride requests
9. Test heap scheduling
10. Run sorting analysis
11. Exit program
---

## Extra notes

- Graph implemented using adjacency lists
- Hash Table implemented using linear probing
- Heap implemented as a Max Heap
- Dijkstra's algorithm used for shortest path calculations
- Sorting performance measured using System.nanoTime()
- Merge Sort and Quick Sort implemented without built-in sorting libraries
---

## TO EXIT THE PROGRAM:
To safely exit the system, select Exit from the main menu.


