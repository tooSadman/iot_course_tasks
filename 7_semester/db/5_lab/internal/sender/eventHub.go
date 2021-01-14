package sender

import (
	"context"
	"encoding/json"

	eventhub "github.com/Azure/azure-event-hubs-go"
	"github.com/sirupsen/logrus"
)

func operateEventHub(url string, connStr string) {
	// get Json
	dataJson := getJson(url)

	hub, err := eventhub.NewHubFromConnectionString(connStr)
	if err != nil {
		logrus.Info(err)
	}

	ctx := context.Background()

	// send a single message into a random partition
	for i, data := range dataJson {
		dataSingleJson, err := json.Marshal(data)
		if err != nil {
			logrus.Fatal(err)
		}
		event := eventhub.NewEvent(dataSingleJson)
		event.Set("content_type", "application/json")
		err = hub.Send(ctx, event)
		if err != nil {
			logrus.Info(err)
		} else {
			logrus.Infof("Document %d was sent!", i)
		}
	}

	err = hub.Close(context.Background())
	if err != nil {
		logrus.Info(err)
	}

	logrus.Info("Json was sent to EventHub!")
}
