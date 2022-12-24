# SRE Lab

## Setup

### Docker compose

```
docker compose up -d
```

- The following ports need to be available:
  - 5432 (postgres)
  - 8080 (dropwizard api)
  - 8081 (dropwizard api admin)
  - 9200 (elasticsearch)
  - 5601 (kibana)
  - 8200 (apm server)
  - 8125 (StatsD via metricbeat)
  - 12201 (logstash)
  - 9996 (users api)

###  K8s
Requirements:  
Kubernetes version >= 1.21  
Helm version >= 3.8

```
./build-containers.sh
./k8s-bootstrap.sh
```


### About
This lab contains the following components:
- Kotlin Dropwizard API (api)
- Golang API (users-api)
- Postgres DB
- K6 load generation
- Elasticsearch observability tools
    - Kibana for logs
    - APM
    - Metrics

### Current Topology

![Topology](./docs/media/2022-09-06-docker-topology.png)


## TODO
- [ ] Create K8s configs and deployments for minikube
- [ ] Control plane (istio)
- [ ] Look into Grafana vs Elastic for charting metrics
- [ ] Message bus for triggering changes in state
- [ ] Mechanism for triggering load tests
- [ ] interface for scenarios
- [ ] ability to reset
- [ ] Add support for multiple APMs
  - [x] Elastic
  - [ ] SigNoz
  - [ ] Datadog
  - [ ] New Relic


Low Priority:
- [ ] Improve indexing/readability of app logs in kibana

