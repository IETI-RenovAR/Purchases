#!/bin/bash

sudo docker stop purchases
git pull
sudo docker rm purchases
sudo docker build -t purchases .
sudo docker run -d -p 8083:8080 --name purchases purchases
