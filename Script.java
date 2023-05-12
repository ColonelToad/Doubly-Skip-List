import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Script
{
    
    public static ArrayList<Integer> generateIntegers(int numIntegers, boolean sortedList)
    {
        ArrayList<Integer> integers = new ArrayList<>();
        if (sortedList) {
            for (int i = 0; i < numIntegers; i++)
            {
                integers.add(i);
            }
        }
        else
        {
            Random random = new Random();
            for (int i = 0; i < numIntegers; i++)
            {
                integers.add(random.nextInt(numIntegers * 2));
            }
        }
        return integers;
    }
    
    public static double timeMethod(String methodName, DoublySkipList list, int value)
    {
        long startTime = System.nanoTime();
        switch (methodName)
        {
            case "insertHead":
                list.insertHead(value);
                break;
            case "insertTail":
                list.insertTail(value);
                break;
            case "deleteHead":
                list.deleteHead(value);
                break;
            case "deleteTail":
                list.deleteTail(value);
                break;
            case "searchHead":
                list.searchHead(value);
                break;
            case "searchTail":
                list.searchTail(value);
                break;
            default:
                throw new IllegalArgumentException("Invalid method name: " + methodName);
        }
        long endTime = System.nanoTime();
        return (endTime - startTime) / 1e9;
    }
    
    public static void runTimingTests(DoublySkipList list, ArrayList<Integer> integers)
    {
        Map<String, Map<Integer, ArrayList<Double>>> timingResults = new HashMap<>();
        for (int integer : integers) {
            for (String methodName : new String[]{"insertHead", "insertTail", "deleteHead", "deleteTail", "searchHead", "searchTail"})
            {
                double timeElapsed = timeMethod(methodName, list, integer);
                if (!timingResults.containsKey(methodName))
                {
                    timingResults.put(methodName, new HashMap<>());
                }
                if (!timingResults.get(methodName).containsKey(integer))
                {
                    timingResults.get(methodName).put(integer, new ArrayList<>());
                }
                timingResults.get(methodName).get(integer).add(timeElapsed);
            }
        }
        
        // Export results to file
        try
        {
            FileWriter writer = new FileWriter("timing_results.txt");
            for (String methodName : timingResults.keySet())
            {
                for (int integer : timingResults.get(methodName).keySet())
                {
                    ArrayList<Double> times = timingResults.get(methodName).get(integer);
                    double averageTime = times.stream().mapToDouble(Double::doubleValue).average().orElse(0);
                    writer.write(methodName + " " + integer + " " + averageTime + "\n");
                }
            }
            writer.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args)
    {
        int numIntegers = 10000;
        boolean sortedList = false;
        ArrayList<Integer> integers = generateIntegers(numIntegers, sortedList);
        DoublySkipList list = new DoublySkipList();
        runTimingTests(list, integers);
    }
}