# find-political-donors

Code component:

1. ElectionMonitor.java: This file contains the main program to process data stream. It handles main logics as well as I/O.

2. Median.java: Main data structure for optimal median finding and data storage.

Approach:

I used a two-heap data structure to quickly find median of the input data stream, in which min heap stores the larger half and max heap stores the smaller half.

The number of transactions, and the total number of donations are stored in this Median data structure.

For the Median_By_Zip output, I used a HashMap in which recipient ID is key, and HashMap<String, Median> as the corresponding value. This HashMap stores the Median data corresponding to this recipient at certain zip, this zip is the key of the secondary HashMap.

For the Median_By_Date output, I used a TreeMap, in which the concartination of ID and date seperated by "|" are used as keys, and Median corresponding to that key is used as value. TreeMap can efficiently sort values by keys alphabetically, the order of concartination satisfies the requirement of required list order: first alphabetically then chronologically.

No external dependencies are used, classical java environment will be sufficient.

To run the program run the shell script use sh run.sh or ./run.sh

3. run.sh file

"javac" command is used to compile the code, "java" command is used to execute the program.

".class" files are cleared after running 
