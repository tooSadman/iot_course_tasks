class SparesManager:
    spares_list = []

    def __init__(self):
        pass

    def find_by_category(self, category):
        found_spares = []

        for spares in self.spares_list:
            if spares.category == category:
                found_spares.append(spares)

        return found_spares


    def sort_by_serial_number(self):
        self.spares_list.sort(key=lambda spares: spares.serial_number)

    def print_list(self):
        for spares in self.spares_list:
            print(spares)
        print("\n")
