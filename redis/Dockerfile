FROM redis
FROM redislabs/redisgears:latest

RUN apt-get update -qq
RUN apt-get upgrade -y
RUN apt-get install -y git
RUN apt-get install -y build-essential
RUN apt-get install -y python3-pip
RUN pip install gears-cli

COPY register.py register.py
