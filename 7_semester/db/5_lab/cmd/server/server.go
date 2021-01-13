package main

import (
	"context"
	"crypto/tls"
	"encoding/json"
	"fmt"
	"io/ioutil"
	"net/http"
	"time"

	eventhub "github.com/Azure/azure-event-hubs-go"
	"github.com/go-redis/redis"
	"github.com/sirupsen/logrus"
)

type postRequest struct {
	Url      string `json:"url"`
	Strategy string `json:"strategy"`
}

func getJson(url string) []map[string]string {
	getClient := http.Client{
		Timeout: time.Second * 2, // Timeout after 2 seconds
	}

	req, err := http.NewRequest(http.MethodGet, url, nil)
	if err != nil {
		logrus.Fatal(err)
	}

	res, err := getClient.Do(req)
	if err != nil {
		logrus.Fatal(err)
	}

	if res.Body != nil {
		defer res.Body.Close()
	}

	body, err := ioutil.ReadAll(res.Body)
	if err != nil {
		logrus.Fatal(err)
	}

	var jsonArr []map[string]string

	err = json.Unmarshal(body, &jsonArr)
	if err != nil {
		logrus.Fatal(err)
	}

	return jsonArr
}

func operateRedis(url string) {
	dataJson := getJson(url)
	client := redis.NewClient(&redis.Options{
		Addr:      "iot-lab.redis.cache.windows.net:6380",
		Password:  "IoQgH3VNqLD8OJ6o7r4HB57AIVZvp64ADmIdP4f4bfU=",
		DB:        0,
		TLSConfig: &tls.Config{InsecureSkipVerify: true},
	})

	_, err := client.Ping().Result()
	if err != nil {
		logrus.Fatal(err)
	}

	for i, data := range dataJson {
		dataSingleJson, err := json.Marshal(data)
		if err != nil {
			logrus.Fatal(err)
		}
		err = client.Set(fmt.Sprintf("data_%d", i), dataSingleJson, 0).Err()
		if err != nil {
			logrus.Warn(err)
		} else {
			logrus.Infof("Document %d is written!", i)
		}
	}
}

func operateEventHub(url string) {
	// get Json
	dataJson := getJson(url)
	connStr := "Endpoint=sb://iot-db-lab.servicebus.windows.net/;SharedAccessKeyName=test-policy;SharedAccessKey=brnwqHa3XCqVFX43t0oWaC1uEwUarV0ahTG/0NmX/GY=;EntityPath=test-lab5"

	hub, err := eventhub.NewHubFromConnectionString(connStr)
	if err != nil {
		logrus.Info(err)
	}

	ctx := context.Background()

	// send a single message into a random partition
	for _, data := range dataJson {
		dataSingleJson, err := json.Marshal(data)
		if err != nil {
			logrus.Fatal(err)
		}
		event := eventhub.NewEvent(dataSingleJson)
		event.Set("content_type", "application/json")
		err = hub.Send(ctx, event)
		if err != nil {
			logrus.Info(err)
		}
	}

	err = hub.Close(context.Background())
	if err != nil {
		logrus.Info(err)
	}

	logrus.Info("Json was sent to EventHub!")
}

func HelloServer(w http.ResponseWriter, req *http.Request) {
	decoder := json.NewDecoder(req.Body)
	var request postRequest
	err := decoder.Decode(&request)
	if err != nil {
		panic(err)
	}

	fmt.Println(request.Strategy)

	switch {
	case request.Strategy == "redis":
		operateRedis(request.Url)
	case request.Strategy == "eventHub":
		operateEventHub(request.Url)
	default:
		logrus.Info("Wrong strategy choosed!")
	}
}

func main() {
	http.HandleFunc("/url", HelloServer)
	http.ListenAndServe(":9000", nil)
}
