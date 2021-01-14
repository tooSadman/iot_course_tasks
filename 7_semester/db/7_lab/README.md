# Підготовка візуалізацій

1. Створюємо візуалізацію типу Pie

![ssh-hd-insight](../img/7/1.png)

2. При створенні візуалізації треба вибрати налаштування Bucket. Зберігаємо візуалізацію.

![ssh-hd-insight](../img/7/2.png)

3. Аналогічно створюємо візуалізацію типу Line, але з налаштуваннями Split

![ssh-hd-insight](../img/7/3.png)

4. Створюємо візуалізацію типу Controls

![ssh-hd-insight](../img/7/4.png)

5. Додаємо створені візуалізації на Dashboard та зберігаємо його.

![ssh-hd-insight](../img/7/5.png)

6. За допомогою Controls можна фільтрувати дані по необхідних характеристиках.

# Kibana devTools запити

1. Топ-3 за полем map.current_budget.keyword
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

2. Фільтрація записів за датою
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
