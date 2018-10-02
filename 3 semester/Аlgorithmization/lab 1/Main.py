from SortManager import SortManager
from datetime import datetime


class CoolingChamber(object):
    def __init__(self,
                 manufacturer="Phillips",
                 total_volume=30,
                 weight=20,
                 power_consumption=30):
        self.manufacturer = manufacturer
        self.total_volume = total_volume
        self.weight = weight
        self.power_consumption = power_consumption

    def __str__(self):
        return ("Manufacturer: " + self.manufacturer + ", Total volume: " +
                str(self.total_volume) + ", Weight: " + str(self.weight) +
                ", Power consumprion: " + str(self.power_consumption))


class Main:
    ARRAY_SIZE = 10
    chambers = [CoolingChamber()] * ARRAY_SIZE

    @staticmethod
    def init():
        with open("chambers.txt") as file:
            i = 0
            for line in file:
                array = line.split(",")
                Main.chambers[i] = CoolingChamber(array[0], int(array[1]),
                                                  int(array[2]), int(array[3]))
                i += 1

    @staticmethod
    def print_array(arr_name, arr):
        print("\n" + arr_name + ":")
        for arr_i in range(len(arr)):
            print(arr[arr_i])
        print("\n====================\n")


Main.init()
Main.print_array("Initial array", Main.chambers)

quicksort_arr = Main.chambers
insertion_sort_arr = Main.chambers

# Quicksort
start = datetime.now().microsecond
quicksort_arr = SortManager.quickSort(Main.chambers, 0, len(Main.chambers) - 1)
finish = datetime.now().microsecond - start
print("Quicksort by memory RESULTS:")
print("Time spent: %s mills" %
      (finish))
print("Number of comparisons: " + str(SortManager.compare_num))
print("Number of item swaps: " + str(SortManager.swap_num))
print("Final arr:")
for j in range(len(quicksort_arr)):
    print(quicksort_arr[j])
print("\n====================\n")

SortManager.count_reset()
start = 0
finish = 0

# Insertion sorting
start = datetime.now().microsecond
insertion_sort_arr = SortManager.insertion_sort(Main.chambers)
finish = datetime.now().microsecond - start
print("Insertion sort by memory RESULTS:")
print("Time spent: %s mills" %
      (finish))
print("Number of comparisons: " + str(SortManager.compare_num))
print("Number of item swaps: " + str(SortManager.swap_num))
for j in range(len(insertion_sort_arr)):
    print(insertion_sort_arr[j])
print("\n====================\n")
