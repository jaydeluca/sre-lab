Deploy nats jetstreams cluster

```sh
cd stacks/nats_jetstream/1.0.0
terraform init
terraform apply

# to start interacting with nats cluster
kubectl exec -n nats -it $(k get pods -n nats --selector=app=nats-jetstream-box --output=jsonpath={.items..metadata.name}) -- /bin/sh -l
```
