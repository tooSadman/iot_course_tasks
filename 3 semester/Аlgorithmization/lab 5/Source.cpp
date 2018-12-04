#include <iostream>
#include <string>
#include <vector>
#include <map>

using namespace std;

void dfs(int v, vector<pair<vector<int>, vector<int>>> &graph, vector<bool> &used, int depth, int &maxDepth)
{
	used[v] = true;
	depth++;
	if (graph[v].second.size() == 0) // перевіряємо чи це не найнижча частина ланцюжка
	{
		if (maxDepth < depth)
		{
			maxDepth = depth;
		}
	}

	for (auto i = graph[v].second.begin(); i != graph[v].second.end(); i++)
		if (!used[*i])
			dfs (*i, graph, used, depth, maxDepth);
}


bool isParent(string father, string son)
{
	for (int i = 0; i < father.length(); i++)
    {
        if (((i != father.length() - 1) && (father.substr(0, i) + father.substr(i + 1, father.length() - i - 1) == son))
            || ((i == father.length() - 1) && (father.substr(0, i) == son)))
            return true;
    }

    return false;
}

pair<vector<vector<string>>, map<string, int>> readInput()
{
	int n;
	cin >> n;

	vector<vector<string>> dictionary(51);
	map<string, int> graphBinds;

	string s;
	for (int i = 0; i < n; i++)
	{
		cin >> s;
		dictionary[s.length()].push_back(s); // записуємо слова в масив, де 40 стрічка = слову із 40 букв
		graphBinds.insert(make_pair(s, i));
	}

	auto result = make_pair(dictionary, graphBinds);

	return result;
}

int main()
{
	auto input = readInput();

	auto dictionary = input.first;
	auto graphBindings = input.second;

	vector<pair<vector<int>, vector<int>>> graph(graphBindings.size()); // вектор векторів ( в кожній комрці по дві pair	)

	for (int i = 50; i != 0; i--)
	{
		if (i == 50)
		{
			for (auto father : dictionary[i])
			{
				for (auto son : dictionary[i - 1])
				{
					if (isParent(father, son))
					{
						graph[graphBindings[father]].second.push_back(graphBindings[son]);
					}
				}
			}
		}
		else
		{
			for (auto father : dictionary[i + 1])
			{
				for (auto son : dictionary[i])
				{
					if (isParent(father, son))
					{
						graph[graphBindings[son]].first.push_back(graphBindings[father]);
					}
				}
			}
			for (auto father : dictionary[i])
			{
				for (auto son : dictionary[i - 1])
				{
					if (isParent(father, son))
					{
						graph[graphBindings[father]].second.push_back(graphBindings[son]);
					}
				}
			}
		}
	}

	int maxDepth1 = 0;
	for (int i = 0; i < graph.size(); i++)
	{
		if (graph[i].first.size() == 0)
		{
			vector<bool> used(graphBindings.size());

			int maxDepth = 0;
			dfs(i, graph, used, 0, maxDepth);
			if (maxDepth1 < maxDepth)
			{
				maxDepth1 = maxDepth;
			}
		}
	}

	cout << maxDepth1 << endl;

}
