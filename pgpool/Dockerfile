FROM ubuntu:22.04

ENV DEBIAN_FRONTEND=noninteractive

RUN apt-get update && \
    apt-get install -y pgpool2 postgresql-client && \
    apt-get clean && rm -rf /var/lib/apt/lists/*

EXPOSE 9999 9898

CMD ["pgpool", "-n", "-f", "/etc/pgpool2/pgpool.conf"]
