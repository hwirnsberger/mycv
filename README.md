
# mycv
Playground from DEV to OPS

## What purpose does this project have
It is a learning playground from writing software to deploy software in production.
I want to implement a CI/CD pipeline where every commit will get deployed in production if tests are green.
Also i want to play around with tools like github actions, kubernetes, etc.

## How to develop
First you need to start a mongodb:

docker run --rm --name mycv-mongodb -p 27017:27017  -e MONGODB_USERNAME=mycv -e MONGODB_PASSWORD=mycv -e MONGODB_DATABASE=mycv bitnami/mongodb:latest

Start the application in dev mode:

mvn compile quarkus:dev

Happy coding :-)
