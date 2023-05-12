# Doubly SkipList

An implentation of the skipList data structure in Java where the list maintains both a head and tail pointer, `Node Head` and `Node Tail` in the code. 

## Goal

The goal was to answer the following question:

`Given a skiplist where all the values (integers in this case) are maintained in a strict ordering within the list, does adding a tail pointer make insertion, search, and deletion any faster than without?` 

To do this, I created the following methods:

* insertHead
* insertTail
* deleteHead
* deleteTail
* searchHead
* searchTail

Where the corresponding head and tail methods do the same thing, just start from a different node. I also created a `determinePointer` method to specify where to initially start from given certain conditions, and a `getRandomHeight` to generate a random height for every level of the skiplist.

## How to Run

In the `Script.java` file there is the following line:
* `int numIntegers = 10000;`

in the main method where the user can set the number of integers to generate as high as they would like. Once set, the user can run
* `javac DoublySkipList.java Script.java`

where the results of the timing test are recorded in `timing_results.txt`. Finally, using the `Visual.py` file, users can generate a bar graph to visualize how every method fared against each other.
