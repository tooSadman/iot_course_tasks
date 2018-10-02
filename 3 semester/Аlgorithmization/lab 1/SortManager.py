from datetime import datetime


class SortManager:

    compare_num = 0
    swap_num = 0

    @staticmethod
    def compare_count(n):
        SortManager.compare_num += n
        pass

    @staticmethod
    def swap_count(n):
        SortManager.swap_num += n
        pass

    @staticmethod
    def count_reset():
        SortManager.compare_num = 0
        SortManager.swap_num = 0

    # sorts arr elements by total volume in decreasing order
    @staticmethod
    def insertion_sort(arr):
        # sorting
        for index in range(len(arr) - 1, -1, -1):
            current_value = arr[index].total_volume
            current_index = arr[index]
            position = index
            while position < (
                    len(arr) -
                    1) and arr[position + 1].total_volume > current_value:
                arr[position] = arr[position + 1]
                position += 1
                SortManager.compare_count(2)
            SortManager.compare_count(2)
            SortManager.swap_count(1)
            arr[position] = current_index

        return arr

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
            SortManager.compare_count(1)
            if arr[j].power_consumption <= pivot:

                # increment index of smaller element
                i = i + 1
                SortManager.swap_count(2)
                arr[i], arr[j] = arr[j], arr[i]

        SortManager.swap_count(2)
        arr[i + 1], arr[high] = arr[high], arr[i + 1]
        return (i + 1)

    # The main function that implements QuickSort
    # arr[] --> Array to be sorted,
    # low  --> Starting index,
    # high  --> Ending index

    # Function to do Quick sort

    def quickSort(arr, low, high):

        if low < high:

            # pi is partitioning index, arr[p] is now
            # at right place
            pi = SortManager.partition(arr, low, high)

            # Separately sort elements before
            # partition and after partition
            SortManager.quickSort(arr, low, pi - 1)
            SortManager.quickSort(arr, pi + 1, high)

        return arr
