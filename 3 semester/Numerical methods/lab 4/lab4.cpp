#include <iostream>
#include <math.h>
using namespace std;
float f(float x)
{
        return(log(log(x)));
}
float integralpram(int a,int b,int n)
{
        float h,S,x;
        int i;
        h=(b-a) * 1.0 /n;
        S=0;
        for(i=0; i<n-1; i++)
        {
                x=a+i*h;
                S=S+f(x);
        }
        S=h*S;
        return S;
}
int main(int argc, char **argv)
{
        float y;
        int a=2, b=4, n=15;
        y=integralpram(a,b,n);
        cout<<y<<endl;
        return 0;
}
