#include <iostream>
#include <cmath>
#include <locale.h>
#include <cstdlib>
#include <math.h>
#include <stdlib.h>

using namespace std;

#define eps 0.0001

int main()
{
        int m=2, n=2, q=2, p=2;
        double x[m]={0.5,0.5}, f[m], ee = 10^-7;
        double e[n+1][n+1][m], V[m], w;
        bool condition = true;
        int l_it = 0, l_eps = 0; //лічильник ітерацій
        //задання нулів для k=-1 е-3D матриці
        for(int j=0; j<n+1; j++) {
                for(int i=0; i<m; i++) {
                        e[j][0][i]=0;
                }
        }
        do {
                condition = false;
                // початкові ітерації
                for(int j=1; j<=p; j++) {
                        x[0] = 0.4+x[0]/(x[0]*x[0]+x[1]*x[1]);
                        x[1] = -1.4 - x[1]/(x[0]*x[0]+x[1]*x[1]);
                        l_it++;
                }
                //задання s0[]=x[]
                for (int i=0; i<m; i++) {
                        e[0][1][i] = x[i];
                } ee=15;
                // генерація S-послідовності та заповнення нею k=0 стовпця е-3D матриці
                for (int j=0; j<=(2*q-1); j++) {
                        e[j+1][1][0] = x[0] = 0.4+x[0]/(x[0]*x[0]+x[1]*x[1]);
                        e[j+1][1][1] = x[1] = -1.4 - x[1]/(x[0]*x[0]+x[1]*x[1]);
                        l_it++;
                        if( j== 0) {
                                for (int i=0; i<m; i++) { //perevirka umovy
                                        condition=condition || fabs(((e[1][1][i]-e[0][1][i]) / e[1][1][i])*100)>ee;
                                }
                                if (!condition) break;
                        }
                }
                if (condition) {
                        for (int k=1; k<=n-1; k++)
                                for (int j=0; j<n-k; j++) {
                                        for (int i=0; i<m; i++) V[i]=e[j+1][k][i]-e[j][k][i];
                                        w=0;
                                        for (int i=0; i<m; i++) w+=V[i]*V[i];
                                        for (int i=0; i<m; i++) V[i]=V[i]/w; // кінець обертання Самельсона
                                        for (int i=0; i<m; i++) e[j][k+1][i] = e[j+1][k-1][i]+V[i];
                                }
                        l_eps++;
                        for (int i=0; i<m; i++) x[i] = e[0][n][i];
                }
        }
        while (condition);

        std::cout << "Розв'язок системи рівнянь" << '\n';
        for (int i=0; i<m; i++) std::cout << x[i] << '\n';
        f[0]=4*x[0]*x[0]+x[1]*x[1]-4;
        f[1]=x[0]-x[1]*x[1];
        std::cout << "Перевірка" << '\n'<< f[0]<< '\n'<<f[1]<< '\n';
        std::cout << "l_it=" << l_it<< '\n';
        std::cout << "l_eps=" << l_eps<< '\n';
        return 0;
}
