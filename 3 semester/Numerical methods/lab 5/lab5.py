import math

import matplotlib.pyplot as plt
from numpy.core.multiarray import arange

Umax = 100
f = 50
R1 = 5
R2 = 4
R3 = 7
R4 = 2
L1 = 0.01
L2 = 0.02
L3 = 0.015
C1 = 300 * math.pow(10, -6)
C2 = 150 * math.pow(10, -6)
C3 = 200 * math.pow(10, -6)
T = 0.2
h = 0.0001
i1, i3, Uc1=0, 0, 0
i1_new, i3_new, Uc1_new = 0, 0, 0


def func1(i1, i3, Uc1):
    return (Umax * math.sin(2 * math.pi * f * t)-i1*R1-Uc1-(i1-i3)*R2)/L1


def func2(i1, i3, Uc1):
    return ((i1-i3)*R2+Uc1-i3*(R3+R4)) / L2

def func3(i1, i3):
    return ((i1-i3)/C1)


if __name__ == '__main__':
    for t in arange(0, (T - h / 2), h):
        U1 = Umax * math.sin(2 * math.pi * f * t)


        i1_new = i1 + h * func1(i1, i3, Uc1)
        i3_new = i3 + h * func2(i1, i3, Uc1)
        Uc1_new = Uc1 + h * func3(i1,i3)

        i1, i3, Uc1 = i1_new, i3_new, Uc1_new
        U2 = i3 * R4
        print(str(U2))
        plt.plot(t + h, U2, 'ro')

    plt.xlabel(r'$t$')
    plt.ylabel(r'$U2$')
    plt.grid(True)
    plt.show()
