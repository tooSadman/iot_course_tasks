package sender

import (
	"crypto/tls"
	"encoding/json"
	"fmt"

	"github.com/go-redis/redis"
	"github.com/sirupsen/logrus"
)

func operateRedis(url string, redisAddr string, redisPass string) {
	dataJson := getJson(url)
	client := redis.NewClient(&redis.Options{
		Addr:      redisAddr,
		Password:  redisPass,
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
