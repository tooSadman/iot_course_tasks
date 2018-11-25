#include <iostream>
#include <vector>
#include <algorithm>
#include <bitset>

using namespace std;

int main()
{
	clock_t tStart = clock();

	unsigned n;
	cout << "Введіть розмір масиву: ";
	cin >> n;

	vector<unsigned> numbers;

	for (unsigned i = 0; i < n; i++)
	{
		unsigned a;
		cout << "Введіть " << i+1 << "-ий елемент масиву: ";
		cin >> a;

		numbers.push_back(a);
	}

	unsigned ans;
	cout << "Введіть шукане число: " ;
	cin >> ans;

	sort(numbers.begin(), numbers.end());

	//Create bit array where "true" values are on indexes that exist in numbers (input array)
	int j = 0;
	bitset<1000000000> *numbersMask = new bitset<1000000000>();
	numbersMask->reset();
	for (unsigned i = 0; i < n; i++)
	{
		numbersMask->set(numbers[i]);
	}
	//-------------------------------------------------------


	for (unsigned i = 0; i < n; i++)
	{
		unsigned tempAns;
		if (numbers[i] < ans)
		{
			tempAns = ans - numbers[i];
		}

		for (unsigned j = 0; j < n; j++)
		{
			if (((int)tempAns - (int)numbers[j] >= 0) && (numbersMask->test(tempAns - numbers[j])) && (i != j) && ((tempAns - numbers[j]) != (ans - tempAns)))
			{
				cout << "YES: " << numbers[i] << " + " << numbers[j] << " + " << (tempAns - numbers[j]) << endl;
				printf("Time taken: %.2fs\n", (double)(clock() - tStart)/CLOCKS_PER_SEC);
				return 0;
			}
		}
	}

	cout << "NO";
	printf("Time taken: %.2fs\n", (double)(clock() - tStart)/CLOCKS_PER_SEC);
}
