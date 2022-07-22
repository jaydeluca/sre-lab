# SRE Lab

## Setup

### Prerequisites 
Current setup supports docker only at the moment

- You will need docker installed
- The following ports need to be available:
  - 5432 (postgres)
  - 8080 (dropwizard api)
  - 8081 (dropwizard api admin)
  - 9200 (elasticsearch)
  - 5601 (kibana)
  - 8200 (apm server)
  - 8125 (StatsD via metricbeat)
  - 12201 (logstash)


### About
This lab contains the following components:
- Kotlin Dropwizard API
- Postgres DB
- K6 load generation
- Elasticsearch observability tools
    - Kibana for logs
    - APM
    - Metrics

## TODO
- [ ] create endpoint that interacts with an unreliable external dependency 
  - [ ] include load test
- [ ] setup mechanism for triggering load tests
- [ ] Look into Grafana vs Elastic for charting metrics
- [ ] interface for scenarios
- [ ] ability to reset
- [ ] Add support for multiple APMs
  - [x] Elastic
  - [ ] Datadog
  - [ ] New Relic
- [ ] Message bus for triggering changes in state


Low Priority:
- [ ] Create K8s configs and deployments
- [ ] Improve indexing/readability of app logs in kibana

