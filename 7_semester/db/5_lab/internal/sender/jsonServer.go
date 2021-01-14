package sender

import (
	"encoding/json"
	"fmt"
	"io/ioutil"
	"iot-lab-db-5/internal/config"
	"net/http"
	"time"

	"github.com/sirupsen/logrus"
)

type JsonServer struct {
	Config config.JsonServerConfig
}

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

func (server *JsonServer) StartJsonServer(w http.ResponseWriter, req *http.Request) {
	decoder := json.NewDecoder(req.Body)
	var request postRequest
	err := decoder.Decode(&request)
	if err != nil {
		panic(err)
	}

	fmt.Println(request.Strategy)

	switch {
	case request.Strategy == "redis":
		operateRedis(request.Url, server.Config.RedisAddr, server.Config.RedisPass)
	case request.Strategy == "eventHub":
		operateEventHub(request.Url, server.Config.EventHubConnStr)
	default:
		logrus.Info("Wrong strategy choosed!")
	}
}
