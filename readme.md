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
- [ ] ship app logs to elasticsearch
- [x] create load generator (maybe rewrite as gatling for more complex scenarios)
- [ ] create endpoint that interacts with database
- [ ] interface for scenarios
- [ ] ability to reset