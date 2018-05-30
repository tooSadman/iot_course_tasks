from spares.Chassis import *
from spares.Wheels import *
from spares.Electronics import *
from enum_category.SparesCategory import *
from SparesManager import SparesManager

if __name__ == '__main__':
    spares_manager = SparesManager()

    chassis1 = Chassis(182348, 345, 4)
    wheel1 = Wheels(185682, 487,  56, 34)
    wheel2 = Wheels(183642, 322,  65, 48)
    smallTv = Electronics(128908, 400, "34b")
    sensors = Electronics(125688, 200, "48cb")

    spares_manager.spares_list = [wheel1, chassis1, wheel2, smallTv, sensors]
    print("Initial list:")
    spares_manager.print_list()

    spares_manager.spares_list = spares_manager.find_by_category(
        SparesCategory.RUNNINGGEAR)
    print("Found list with Running Gear:")
    spares_manager.print_list()

    spares_manager.sort_by_serial_number()
    print("Sorted list:")
    spares_manager.print_list()

    pass
