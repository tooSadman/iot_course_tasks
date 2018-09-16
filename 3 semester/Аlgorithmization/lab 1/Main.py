from SortManager import SortManager


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

SortManager.quickSort(Main.chambers, 0, len(Main.chambers) - 1)
SortManager.insertion_sort(Main.chambers)
