# ClosestPairs
The algorithm finds the closest pairs in a .tsv file.

For each line in the .tsv file where each line represent the coordinates of a point, the coordinates are stored in an ArrayList<Float>. These coordinates are used to set up Point objects with the coordinates and their original index. The original index refers to the line in .tsv file which it was read from.
These Point objects are stored in an ArrayList<Point>.

This means, if there were 100 lines in the .tsv file and each line had 5 values, then there would be an ArrayList<Point> with 100 Point objects where each Point object had an ArrayList<Float> with 5 Float values.

# Run Time
There is a special case for points in two dimensions where the running time is O(nlogn).
For all other dimensions, the running time is O(n^2d) where n is the number of lines and d is the number of dimensions.

# How To Run
To run the program, navigate to the directory of the file in terminal. Then use


javac ApplicationManager.java

java ApplicationManager


The first line will compile the file and the second line will run the program.

To use, the address of the file should be given as input to the program.

# Limitations
The program assumes there are no blank lines, all values are separated by tabs and the points are 1 dimensional or higher.It is also assumed all lines have the same number of values, i.e each Point has the same dimension. Empty files might cause errors. There is no high end on the number of dimensions. The highest number of tested lines were 1000 and the highest number of dimensions tested was 100.
The input file must be a .tsv file.

# Logging
All the information on the console and any possible errors are logged in a logging file with the name logFile.log in the directory of the program. The results are written to a file named FILENAME_result where the FILENAME corresponds to the name of the input file.
