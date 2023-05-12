import matplotlib.pyplot as plt

# Create empty lists to store the method names and timings
method_names = []
timings = []

# Open the file and read its contents
with open("timing_results.txt", "r") as file:
    for line in file:
        # Extract the method name and timing from the line
        parts = line.strip().split()
        if len(parts) < 2:
            print("Invalid line:", line)
            continue
        method_name = parts[0]
        timing = float(parts[1].split()[0])
        
        # Append the method name and timing to their respective lists
        method_names.append(method_name)
        timings.append(timing)
        #data[method].append(timing)

# Create a bar chart of the timings
plt.bar(method_names, timings)
plt.title("Skiplist Method Timing Results")
plt.xlabel("Method")
plt.ylabel("Time (seconds)")
plt.show()