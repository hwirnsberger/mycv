
# mycv
Playground from DEV to OPS

## What purpose does this project have
It is a learning playground from writing software to deploy software in production.
I want to implement a CI/CD pipeline where every commit will get deployed in production if tests are green.
Also i want to play around with tools like github actions, kubernetes, etc.

## How to develop
First you need to start a mongodb:

podman run -it --rm --name mycv-mongo -p 27017:27017 -e MONGO_INITDB_ROOT_USERNAME=mycv -e MONGO_INITDB_ROOT_PASSWORD=mycv mongo:4.0.14-xenial

Start the application in dev mode:

mvn compile quarkus:dev

Happy coding :-)
