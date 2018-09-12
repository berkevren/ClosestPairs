# ClosestPairs
The algorithm finds the closest pairs in a .tsv file.

The algorithm creates a 2D Float array by reading a TSV file.
All the values in a single line are added to the array. If the file contains 100 lines and each line contains 5 numbers, then a [100][5] array is created.

# Run Time
There is a special case for points in two dimensions where the running time is O(nlogn).
For all other dimensions, the running time is O(n^2m) where n is the number of lines and m is the number of dimensions.

# How To Run
To run the program, navigate to the directory of the file in terminal. Then use

javac TaskManager.java
java TasKManager

The first line will compile the file and the second line will run the program.

To use, the address of the file should be given as input to the program.

# Limitations
The program assumes there are no blank lines, all values are separated by tabs and the points are 1 dimensional or higher. Empty files will cause errors. There is no high end on the number of dimensions. There can be up to 2000 lines on a given file, or the memory will not be enough.
The input file must be a .tsv file.

# Logging
All the information on the console and any possible errors are logged in a logging file with the name logFile.log in the directory of the program. The results are written to a file named FILENAME_result where the FILENAME corresponds to the name of the input file.
