# SRE Lab

Prerequisites: 
- You will need docker installed
- The following ports need to be available:
  - 5432 (postgres)
  - 8080 (dropwizard api)
  - 8081 (dropwizard api admin)
  - 9200 (elasticsearch)
  - 5601 (kibana)
  - 8200 (apm server)



TODO:
- [x] create endpoints that interact with database
  - [x] create new order
  - [x] get all orders
  - [x] get single order
  - [x] update order
- [x] create load generator that exercises all endpoints
- [ ] create endpoint that interacts with an unreliable external dependency 
  - [ ] include load test
- [ ] setup mechanism for triggering load tests
- [ ] ship app logs to elasticsearch
- [ ] interface for scenarios
- [ ] ability to reset
- [ ] Add support for multiple APMs
  - [x] Elastic
  - [ ] Datadog
  - [ ] New Relic
- [ ] Message bus for triggering changes in state


Low Priority:
- [ ] Create K8s configs and deployments
- [ ] linting
- [ ] CI/CD for linting and testing builds