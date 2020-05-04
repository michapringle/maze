# Maze Making Program

## Introduction
I wrote this program back in university days to experiment with 
algorithms to generate a random maze as fast as (big-oh) possible.
The result is a program that generates a maze (actually DAG) in 
O(n) time.
Recently I decided to polish up the U/I and bring it up to snuff 
for consumption for the masses.

![Maze](47x72_s2523584398559625748.png)
<sub><sup>_Example 47 row x 72 column maze made with seed 2523584398559625748_</sub></sup>

I decided to keep the generated code base small, so I have not used
a bunch of libraries I would normally include, for example Guava, and Apache Commons. The 
result is a fat-jar under 30k in size.

## Building and Running

### Requires
* Java 11
* Maven 3.6.1


### Building
To build the fat jar
```
mvn clean package assembly:single
```
look for `target/maze-1.0-SNAPSHOT-jar-with-dependencies.jar`

### Running

Run the included `maze.sh` (OS X and linux), or type 
`java -jar target/maze-1.0-SNAPSHOT-jar-with-dependencies.jar` on the command line, 
or double click on the jar. 
Saved mazes are saved in the directory the jar is run from in a directory called mazes.


### Warnings
* The path finding algorithm is experimental, and can cause a stack overflow for larger mazes.
  If you want to generate a maze that is larger than 500 x 500, then you should increase the
  stack size, for example
  `java -Xss16M -jar target/maze-1.0-SNAPSHOT-jar-with-dependencies.jar`, which is what is
  specified in the `maze.sh` file.
* The application allows you to specify a maximum size maze of 9,999 x 9,999. 
The largest maze I have generated with the path finding algorithm is 2,000 x 2,000. It
took 10 seconds to create the maze, and 7 seconds to find the longest path. When I saved it, the 
program generated a 49.3 MB png. I *strongly* recommend sticking to smaller mazes even though this 
program can generate larger ones, if you dare.
