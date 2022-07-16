# Load Generation



## SRELab Orders API

Uses [K6](https://k6.io/docs/)

Scenario:

- Creates new order
- Requests Order
- Updates Order
- Fetches all orders



```
k6 run srelab-api.js
```