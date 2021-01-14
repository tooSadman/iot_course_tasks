## lab 7

1. Create Pie Visualization

![ssh-hd-insight](../img/7/1.png)

2. We need to choose Bucket setting and save visualization. 

![ssh-hd-insight](../img/7/2.png)

3. The same way create Line visualization, but with Split setting.

![ssh-hd-insight](../img/7/3.png)

4. Create **Controls** visualization.

![ssh-hd-insight](../img/7/4.png)

5. Add saved visualizations to the dashboard and save it.

![ssh-hd-insight](../img/7/5.png)

6. We can use Controls filters to filter requests.

### Kibana Dev Tools requests

1. Top-6 results for budget_fiscal_year.keyword fiels 
```
GET _search
{
"size": 0,
"aggs" : {
    "langs" : {
        "terms" : { "field" : "budget_fiscal_year.keyword",  "size" : 6 }
    }
}}
```

![ssh-hd-insight](../img/7/6.png)

2. current_budget.keyword filtration with field match
```
GET _search
{
  "query": {
    "bool": {
      "filter": {
        "bool": {
          "must": [
            {
              "range": {
                "current_budget.keyword": {
                  "gt": 5000000
                }
              }
            },
            {"match": {"fundtype":"General Fund"}}
          ]
        }
      }
    }
  }
}
```

![ssh-hd-insight](../img/7/7.png)
