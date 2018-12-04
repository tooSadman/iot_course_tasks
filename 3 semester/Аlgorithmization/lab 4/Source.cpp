#include <iostream>
#include <vector>
#include <string>
#include <set>

using namespace std;



void dfs (int v, vector<vector<int>> &graph, vector<bool> &used, vector<set<int>> &counter)
{
	used[v] = true;


	counter[v].insert(graph[v].begin(), graph[v].end());

	for (vector<int>::iterator j = graph[v].begin(); j != graph[v].end(); ++j)
		counter[v].insert(graph[*j].begin(), graph[*j].end());


	for (vector<int>::iterator i = graph[v].begin(); i != graph[v].end(); ++i)
		if (!used[*i])
			dfs (*i, graph, used, counter);

}

vector<vector<char>> readMatrix()
{
	string s;
	cin >> s;

	vector<vector<char>> matrix;
	matrix.assign(s.length(), vector<char>());

	for (int i = 0; i < s.length(); i++)
	{
		matrix[0].push_back(s[i]);
	}


	for (int i = 1; i < s.length(); i++)
	{
		cin >> s;
		for (int j = 0; j < s.length(); j++)
		{
			matrix[i].push_back(s[j]);
		}
	}

	return matrix;
}

int main()
{
	auto relations = readMatrix();

	vector<vector<int>> graph(relations.size());
	for (int i = 0; i < relations.size(); i++)
	{
		for (int j = 0; j < relations.size(); j++)
		{
			if (relations[i][j] == 'Y')
			{
				graph[i].push_back(j);
			}
		}
	}

	vector<bool> used(relations.size());

	vector<set<int>> counter(relations.size());

	dfs(0, graph, used, counter);

	int max = 0;

	for (auto student : counter)
	{
		if (max < student.size() - 1)
		{
			max = student.size() - 1;
		}
	}

	cout << max << endl;

	system("pause");

	return 0;
}
