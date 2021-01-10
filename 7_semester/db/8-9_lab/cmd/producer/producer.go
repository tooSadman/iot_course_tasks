package main

import (
	"context"
	"encoding/json"
	"fmt"
	"math/rand"
	"strconv"
	"time"

	"github.com/sirupsen/logrus"
	"github.com/thanhpk/randstr"

	eventhub "github.com/Azure/azure-event-hubs-go/v3"
)

func randomTimestamp() time.Time {
	randomTime := rand.Int63n(time.Now().Unix()-94608000) + 94608000

	randomNow := time.Unix(randomTime, 0)

	return randomNow
}

func genJson() []byte {
	id := strconv.Itoa(rand.Int())
	date := randomTimestamp().String()
	name1 := randstr.String(16)
	name2 := randstr.String(16)

	dataMap := map[string]string{
		"id":    id,
		"date":  date,
		"name1": name1,
		"name2": name2,
	}

	data, err := json.Marshal(dataMap)
	if err != nil {
		fmt.Println(err)
	}

	return data
}

func SendMessage(hub *eventhub.Hub, ctx context.Context, c chan error) {
	dataJson := genJson()
	event := eventhub.NewEvent(dataJson)
	event.Set("content_type", "application/json")
	err := hub.Send(ctx, event)

	c <- err
}

func main() {
	connStr := "Endpoint=sb://iot-db-lab.servicebus.windows.net/;SharedAccessKeyName=listen-policy;SharedAccessKey=Bm2aXigdSGXzcBoeB1YYsoT8M9oY0vVDBiwC8yiR6zY=;EntityPath=test-eventhub"
	hub, err := eventhub.NewHubFromConnectionString(connStr)
	if err != nil {
		logrus.Error(err)
	}

	ctx := context.Background()
	//ctx, cancel := context.WithTimeout(context.Background(), 40*time.Second)
	//defer cancel()

	// send a single message into a random partition
	c := make(chan error)
	for j := 1; j <= 20; j++ {
		go SendMessage(hub, ctx, c)
		err := <-c
		if err != nil {
			logrus.Warnln(err)
		} else {
			logrus.Infof("Document %d was sent!", j)
		}

	}

	err = hub.Close(context.Background())
	if err != nil {
		fmt.Println(err)
	}
}
