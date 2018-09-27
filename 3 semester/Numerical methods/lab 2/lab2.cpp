#include <iostream>
#include <cmath>
#include <locale.h>
#include <cstdlib>
#include <math.h>
#include <stdlib.h>


double f(double x)
{
        return cos(x)+1/(x+2);
}

double rightLimit (double a, double h){
        double b, fa, fb;
        b = a+h;
        fa = f(a);
        fb = f(b);

        if(fabs(f(b))>fabs(f(a)) && f(a)*f(b)>0) {h=-1*h;}
        b = a+h;
        fb = f(b);

        while (fa*fb>0) {
                a=b;
                b=a+h;
                fa=f(a);
                fb=f(b);
        }
        return b;
}

double findRoot(double a, double b, double e)
{
        while(fabs(f(b)) > e)
        {
                a = b - ((b - a) * f(b))/(f(b) - f(a));
                b = a - ((a - b) * f(a))/(f(a) - f(b));
        }
        return b;
}
int main()
{
        setlocale(LC_ALL,"Russian");
        double a, b, e;
        a=0.0; b=rightLimit(a, 0.3); e=0.0001;
        std::cout << "Права межа = " << b;
        std::cout <<"\n"<<"Корінь рівняння x="<<findRoot(a, b, e);
}
