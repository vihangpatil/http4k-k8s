# [http4k](https://www.http4k.org) on k8s

Bootstrapped minimal `http4k` app with inbuilt support for `k8s` using [this guide](https://www.http4k.org/guide/reference/cloud_native/). 

Different stages of transition of the app onto k8s
1. Run locally using `gradle`
2. Run using `docker`
3. Run using `docker compose`
4. Run on k8s using `kubectl`
5. Run on k8s using `helm`

For steps 1 to 3, you reach application by
```shell
curl -v http://localhost:8081/liveness
curl -v http://localhost:8081/readiness
curl http://localhost:8080/ping
```

For step 4 & 5, only `http://localhost:8080/ping` is exposed.  
`liveness` and `readiness` probes are not exposed.  
Instead they are used internally by k8s.


## 1. Run locally via `gradle` 

```shell
./gradlew run
```

## 2. Run in Docker

### 2a. Build app for docker

```shell
./gradle install
```

### 2b. Build docker image

```shell
docker build --tag backend .

docker images
```

### 2c. Run in `docker` 

Use `--detach` instead of `--rm` to run in background.

```shell
docker run \
  --name backend \
  --publish "8080:8080" \
  --publish "8081:8081" \
  --rm \
  backend

docker ps -a

docker rm -f backend
```

## 3. Running in `docker compose`

```shell
docker compose up --build

docker ps -a

docker compose down
```

## 4. Run on local k8s using `kubectl`

Enable `k8s` in `Docker Desktop` for macOS.

Check which k8s you are connected to.
```shell
kubectl config get-contexts
kubectl config use-contexts docker-desktop
```

```shell
kubectl get nodes
```

If you are not using `[default] namespace`, you will have to pass `--namespace {{custom_namespace}}`.

[Optional] Install k8s dashboard
```shell
kubectl apply -f https://raw.githubusercontent.com/kubernetes/dashboard/v2.7.0/aio/deploy/recommended.yaml
```

```shell
kubectl apply -f k8s/k8s-backend.yaml 

kubectl get services
kubectl get deployments
kubectl get pods

kubectl delete service backend-service
kubectl delete deployment backend-app
```

## 5. Run on local k8s using `helm`

```shell
helm install backend-app helm/backend
```