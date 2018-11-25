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
double x[m]=(0.5,0.5), f[m];
double e[n+1][n+1][m], V[m], w;
bool condition = true;
int l_it = 0, l_eps = 0;  //лічильник ітерацій
//задання нулів для k=-1 е-3D матриці
for(int j=0;j<n+1;j++){
  for(int i=0;i<m;i++){
    e[j][0][i]=0;
  }
}
while (condition){
  condition = false
  // початкові ітерації
  for(int j=1;j<=p;j++){
    x[0] = 0.4+x[0]/(x[0]*x[0]+x[1]*x[1]);
    x[1] = -1.4 - x[1]/(x[0]*x[0]+x[1]*x[1]);
    l_it++;
  }
  //задання s0[]=x[]
  for (int i=0;i<m;i++){
    e[0][1][i] = x[i];
  }
  // генерація S-послідовності та заповнення нею k=0 стовпця е-3D матриці
  for (int j=0;j<=(2*q-1);j++){
    e[j+1][1][0] = x[0] = 0.4+x[0]/(x[0]*x[0]+x[1]*x[1]);
    e[j+1][1][1] = x[1] = -1.4 - x[1]/(x[0]*x[0]+x[1]*x[1]);
    l_it++
    if( j== 0){
      for (int i=0;i<m;i++){  //perevirka umovy
        condition
      }
    }
  }
}
        return 0;
}
