import sys

multitude = []
p = 0

if __name__ == "__main__":
    n = int(input("Введіть кількість елементів множини" + "\n"))

    for i in range(n):
        multitude.append(0)

    i = 0

    for x in range(2 ** n):

        print(multitude)
        i += 1
        p = 0
        j = i

        while (j % 2 == 0):
            j /= 2
            p += 1

        if p < n:
            multitude[p] = 1 - multitude[p]

    pass
