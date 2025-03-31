#!/bin/bash

sudo docker stop purchases
git pull
sudo docker rm purchases
sudo docker build -t purchases .
sudo docker run -d --name purchases purchases
