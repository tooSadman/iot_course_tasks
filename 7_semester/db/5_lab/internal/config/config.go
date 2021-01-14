package config

type JsonServerConfig struct {
	EventHubConnStr string `toml:"eventHub_conn_str"`
	RedisAddr       string `toml:"redis_addr"`
	RedisPass       string `toml:"redis_pass"`
}

func NewJsonServerConfig() *JsonServerConfig {
	return &JsonServerConfig{}
}
