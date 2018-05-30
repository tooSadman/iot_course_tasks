from spares.Spares import *
from enum_category.SparesCategory import *


class Electronics(Spares):
    category = SparesCategory.ELECTRONICS

    def __init__(self, serial_number, price, type):
        self.serial_number = serial_number
        self.price = price
        self.type = type

    def __str__(self):
        return "Serial Number: " + str(self.serial_number) + "  Price: " + str(self.price) + " Category: " + str(self.category.value) + " Type: " + str(self.type)
