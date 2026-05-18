Live API link: https://intens-api-2022-server-production.up.railway.app/

Note: A few additional tests were added in the base project for the test report demo in [this workflow summary](https://github.com/K-atarinaVujovic/intens-api-2022/actions/runs/26008916796?pr=3).

# About
A simple Java API with a multi-stage Dockerfile, setup for local deployment with Docker and Kubernetes, and a CI/CD pipeline with deployment to [Railway](https://railway.com).

The Dockerfile uses a multi-stage build with layer caching.

Local Docker and Kubernetes deployments pull the image from the [GitHub Container Registry](https://github.com/K-atarinaVujovic/intens-api-2022/pkgs/container/intens-api-2022) instead of using a locally built image.

The CI workflow builds and tests pull requests to master. A test report is published in the workflow run summary, which would help improve PR reviewing speed.

The CD workflow builds and pushes the image to GHCR, then deploys to Railway.

# Deploying locally
The API can also be deployed locally either with [Docker](https://www.docker.com/) or [Kubernetes](https://kubernetes.io/).

## Docker
**Prerequisites:** [Docker Desktop](https://www.docker.com/products/docker-desktop/)  

From the project root:  
```
docker compose up
```  
Runs on http://localhost:8080  

## Kubernetes
**Prerequisites:** [Docker Desktop](https://www.docker.com/products/docker-desktop/), [Minikube](https://minikube.sigs.k8s.io/docs/start/)  

In the Kubernetes/ folder run:  
```
minikube start
kubectl apply -f deployment.yaml
kubectl apply -f service.yaml
minikube service intens-service
```  
The API URL will be printed afterwards, and the page will open automatically.  

To stop:  
```
minikube stop
```
