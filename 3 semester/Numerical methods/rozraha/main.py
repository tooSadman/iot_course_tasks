from math import fabs
from math import log, e, floor
import matplotlib.pyplot as plt



c2 = 0.02
l_max = 1
l_min = 0.1
i_min = 1
i_max = 2
u1 = 10
r1 = 1.5
r2 = 7
r3 = 380
L2 = 2
a = 0.1
T = a
h = T/400
t = 0
it = 0
x = [0] * 3
c1 = 0.034

t_ar = []
u1_ar = []
u2 = []
uc1 = []
uc2 = []
i3 = []


def __get_u(a, t):
    if t < a:
        return 20*t/a-10
    elif t < 2*a:
        return 20*(t-a)/a-10
    elif t < 3*a:
        return 20*(t-2*a)/a-10
    elif t < 4*a:
        return 20*(t-3*a)/a-10
    elif t < 5*a:
        return 20*(t-4*a)/a-10
    elif t < 6*a:
        return 20*(t-5*a)/a-10
    elif t < 7*a:
        return 20*(t-6*a)/a-10
    elif t < 8*a:
        return 20*(t-7*a)/a-10
    elif t < 9*a:
        return 20*(t-8*a)/a-10
    elif t < 10*a:
        return 20*(t-9*a)/a-10
    elif t < 11*a:
        return 20*(t-10*a)/a-10
    elif t < 12*a:
        return 20*(t-11*a)/a-10
    elif t < 13*a:
        return 20*(t-12*a)/a-10
    elif t < 14*a:
        return 20*(t-13*a)/a-10
    elif t < 15*a:
        return 20*(t-14*a)/a-10
    elif t < 16*a:
        return 20*(t-15*a)/a-10
    elif t < 17*a:
        return 20*(t-16*a)/a-10
    elif t < 18*a:
        return 20*(t-17*a)/a-10
    elif t < 19*a:
        return 20*(t-18*a)/a-10
    elif t < 20*a:
        return 20*(t-19*a)/a-10
    elif t < 21*a:
        return 20*(t-20*a)/a-10
    elif t < 22*a:
        return 20*(t-21*a)/a-10
    elif t < 23*a:
        return 20*(t-22*a)/a-10
    elif t < 24*a:
        return 20*(t-23*a)/a-10
    elif t < 25*a:
        return 20*(t-24*a)/a-10
    elif t < 26*a:
        return 20*(t-25*a)/a-10
    elif t < 27*a:
        return 20*(t-26*a)/a-10
    elif t < 28*a:
        return 20*(t-27*a)/a-10
    elif t < 29*a:
        return 20*(t-28*a)/a-10
    elif t < 30*a:
        return 20*(t-29*a)/a-10
    elif t < 31*a:
        return 20*(t-30*a)/a-10
    elif t < 32*a:
        return 20*(t-31*a)/a-10
    elif t < 33*a:
        return 20*(t-32*a)/a-10
    elif t < 34*a:
        return 20*(t-33*a)/a-10
    elif t < 35*a:
        return 20*(t-34*a)/a-10
    elif t < 36*a:
        return 20*(t-35*a)/a-10
    elif t < 37*a:
        return 20*(t-36*a)/a-10
    elif t < 38*a:
        return 20*(t-37*a)/a-10
    elif t < 39*a:
        return 20*(t-38*a)/a-10
    elif t < 40*a:
        return 20*(t-39*a)/a-10
    else:
        return 10

def __get_cubic_spline(x):
    b1 = (2*(x-i_min)+h)*(i_max-x)**2
    b2 = (2*(i_max-x)+h)*(x-i_min)**2
    b3 = (x-i_min)*(i_max-x)**2
    b4 = (x-i_max)*(x-i_min)**2
    m1 = (l_max - l_min)/(i_max - i_min)
    m2 = m1/(i_max - i_min)
    return (b1 * l_min + b2 * i_max) / h ** 3 + (b3 * m1 + m2 * b4) / h ** 2


def __eq_1(par1, par2):
    global c1
    return ((par2 * r1 - par1 + u1)/(r3 - r1) + par2)/c1


def __eq_2(par1, par2, par3):
    return ((par2 * r1 - par1 + u1) / (r3 - r1) * r3 - par2 * r2 - par3)/L2


def __eq_3(par2):
    return par2/c2


def __runge_kutt_b():
    global L2
    if fabs(x[1]) <= i_min:
        L2 = l_max
    elif fabs(x[1]) >= i_max:
        L2 = l_min
    else:
        L2 = __get_cubic_spline(x[1])

    k1 = h * __eq_1(x[0], x[1])
    k2 = h * __eq_1(x[0]+1/3*k1, x[1] + 1/3*h)
    k3 = h * __eq_1(x[0] + 2/3*k2, x[1] + 2/3*h)
    x[0] = x[0] + (k1 + 3*k3)/4

    k1 = h * __eq_2(x[0], x[1], x[2])
    k2 = h * __eq_2(x[0]+1/3*k1, x[1] + 1/3*h, x[2] + 1/3*h)
    k3 = h * __eq_2(x[0] + 2/3*k2, x[1] + 2/3*h, x[2] + 2/3*h)
    x[1] = x[1] + (k1 + 3*k3)/4

    k1 = h * __eq_3(x[1])
    k2 = h * __eq_3(x[1] + 1/3*h)
    k3 = h * __eq_3(x[1] + 2/3*h)
    x[2] = x[2] + (k1 + 3*k3)/4

    uc1.append(x[0])
    i3.append(x[1])
    uc2.append(x[2])
    u2.append(x[2])


def __make_plot(x_ar, y_ar, x_label, y_label):
    plt.plot(x_ar, y_ar)
    plt.xlabel(x_label)
    plt.ylabel(y_label)
    plt.show()


if __name__ == '__main__':
    while t <= 40 * T:
        c2 = 0.026 ;l_max = 8.3; l_min = 0.83
        r1 = 11; r2 = 22;r3 = 33;L2 = 0
        t_ar.append(t)
        u1 = __get_u(a, t)
        u1_ar.append(u1)
        __runge_kutt_b()
        if t % (2*a) == 0:
            it += 2
        t += h
    __make_plot(t_ar, u1_ar, 'time, (c)', 'u_1, (V)')
    __make_plot(t_ar, uc1, 'time, (c)', 'u_c1, (V)')
    __make_plot(t_ar, i3, 'time, (c)', 'i_3, (A)')
    __make_plot(t_ar, uc2, 'time, (c)', 'u_c2, (V)')
    __make_plot(t_ar, u2, 'time, (c)', 'u_2, (V)')
