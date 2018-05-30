from spares.Spares import *
from enum_category.SparesCategory import *


class Chassis(Spares):
    category = SparesCategory.RUNNINGGEAR

    def __init__(self, serial_number, price, number_of):
        self.serial_number = serial_number
        self.price = price
        self.number_of = number_of

    def __str__(self):
        return "Serial Number: " + str(self.serial_number) + "  Price: " + str(self.price) + " Category: " + str(self.category.value) + " Number of: " + str(self.number_of)
