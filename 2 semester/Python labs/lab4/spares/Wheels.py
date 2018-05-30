from spares.Chassis import *
from enum_category.SparesCategory import *


class Wheels(Chassis):
    category = SparesCategory.RUNNINGGEAR

    def __init__(self, serial_number, price, number_of, size):
        Chassis.__init__(self, serial_number, price, number_of)
        self.size = size

    def __str__(self):
        return Chassis.__str__(self) + " Size: " + str(self.size)
