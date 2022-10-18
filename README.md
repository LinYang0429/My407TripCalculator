This is a simple toll calculator for 407 etr in java.

Build and run the project with two location name strings as input with command line.

Solution 1: (Completed)
Read data from json file and calculate the cost with user input.

Solution 2: (Completed)
Cache the cost and distance of each two of the locations. Calculate user input by directly return the value.

Comparing the above solutions, solution 1 cost less space of memory, but need more running time when calculating (O(n)); solution 2 cost more space of memory, but the running time for calculation is O(1). Considering the case that we have only fixed number of locations and great amount of calculating requset, solution would be beter in real time.

Testings provided under src/test directory.
