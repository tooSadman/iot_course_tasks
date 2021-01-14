package main

import (
	"flag"
	"iot-lab-db-5/internal/config"
	"iot-lab-db-5/internal/sender"
	"log"
	"net/http"

	"github.com/BurntSushi/toml"
)

var (
	configPath string
)

func init() {
	flag.StringVar(&configPath, "config-path", "config/jsonServer.toml", "path to config file")
}

func main() {
	flag.Parse()

	config := config.NewJsonServerConfig()
	_, err := toml.DecodeFile(configPath, config)
	if err != nil {
		log.Fatal(err)
	}

	jsonServer := &sender.JsonServer{Config: *config}

	http.HandleFunc("/url", jsonServer.StartJsonServer)
	http.ListenAndServe(":9000", nil)
}
