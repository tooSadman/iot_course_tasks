from datetime import datetime


class SortManager:
    # sorts arr elements by total volume in decreasing order
    @staticmethod
    def insertion_sort(arr):
        start_time = datetime.now().microsecond
        compare_count = 0
        swap_count = 0
        # sorting
        for index in range(len(arr) - 1, -1, -1):
            current_value = arr[index].total_volume
            position = index
            while position < (
                    len(arr) -
                    1) and arr[position + 1].total_volume > current_value:
                arr[position].total_volume = arr[position + 1].total_volume
                position += 1
                compare_count += 2
            compare_count += 2
            swap_count += 1
            arr[position].total_volume = current_value

        # output
        print("Insertion sort by memory RESULTS:")
        print("Time spent: %s mills" %
              (datetime.now().microsecond - start_time))
        print("Number of comparisons: " + str(compare_count))
        print("Number of item swaps: " + str(swap_count))
        print("Final arr:")
        for j in range(len(arr)):
            print(arr[j])
        print("\n====================\n")

    #sorts elemnets with Quicksort by power consumption in increasing order

    # This function takes last element as pivot, places
    # the pivot element at its correct position in sorted
    # array, and places all smaller (smaller than pivot)
    # to left of pivot and all greater elements to right
    # of pivot
    def partition(arr, low, high):
        i = (low - 1)  # index of smaller element
        pivot = arr[high].power_consumption  # pivot

        for j in range(low, high):
            # If current element is smaller than or
            # equal to pivot
            if arr[j].power_consumption <= pivot:

                # increment index of smaller element
                i = i + 1
                arr[i], arr[j] = arr[j], arr[i]

        arr[i + 1], arr[high] = arr[high], arr[i + 1]
        return (i + 1)

    # The main function that implements QuickSort
    # arr[] --> Array to be sorted,
    # low  --> Starting index,
    # high  --> Ending index

    # Function to do Quick sort

    def quickSort(arr, low, high):
        start_time = datetime.now().microsecond
        compare_count = 0
        swap_count = 0

        # sorting
        if low < high:

            # pi is partitioning index, arr[p] is now
            # at right place
            pi = SortManager.partition(arr, low, high)

            # Separately sort elements before
            # partition and after partition
            SortManager.quickSort(arr, low, pi - 1)
            SortManager.quickSort(arr, pi + 1, high)

        # output
        print("Quicksort by memory RESULTS:")
        print("Time spent: %s mills" %
              (datetime.now().microsecond - start_time))
        print("Number of comparisons: " + str(compare_count))
        print("Number of item swaps: " + str(swap_count))
        print("Final arr:")
        for j in range(len(arr)):
            print(arr[j])
        print("\n====================\n")
