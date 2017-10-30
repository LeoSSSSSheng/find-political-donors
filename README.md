# find-political-donors

Code component:

1. ElectionMonitor.java: contains main program to processing data stream and handling logics and I/O.

2. Median.java: Main data structure for optimal median find, and data storage.

Approach:

Use a two heap data structure to quickly find median of data stream, min heap storing the larger half of data, max heap storing the smaller half.

Number of transactions, and total number of donations are stored in this Median data structure.

For the Median_By_zip, used a HashMap of recipient ID as key, and a HashMap<String, Median> as value, this HashMap stores the Median data of corresponding to this recipient at certain zip, which is the key of the secondary HashMap.

For the Median_By_date, used a TreeMap, inwhich the concartination of ID and date seperated by "|" is used as key, and Median corresponding to that key is used as value. TreeMap can efficiently sort value by key alphabetically, the order of concartination satisfied the requirement of listing order, first alphabetically then chronologically.

No external dependencies used, simply classical java environment is sufficient.

To run the program use sh run.sh or ./run.sh

3. run.sh

javac command is used to compile the code,java command is used to execute the program.

 .class files are cleared after running 
