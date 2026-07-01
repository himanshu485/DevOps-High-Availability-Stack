# DevOps Infrastructure Stack 🚀

A production-style DevOps infrastructure built with Docker Compose, featuring load balancing, database replication, centralized logging, and monitoring. The application is a Java-based visitor counter deployed on Apache Tomcat.

---

## 📌 Project Overview

This project demonstrates a complete DevOps ecosystem consisting of:

* HAProxy load balancing
* Apache Tomcat application servers
* PostgreSQL master-slave replication
* Pgpool-II connection pooling and failover
* Centralized logging with ELK Stack
* Monitoring with Prometheus and Grafana
* Containerized deployment using Docker Compose

---

## 🏗 Architecture

```
                    Client
                       |
                  HAProxy (Port 8082)
                 Round Robin Balancing
                   /              \
                  /                \
          Tomcat1 (8080)       Tomcat2 (8080)
                  \                /
                   \              /
                     Pgpool-II (5432)
                           |
              ----------------------------
              |                          |
      PostgreSQL Master           PostgreSQL Slave

────────────────────────────────────────────

Logs
All Services
     ↓
 Rsyslog
     ↓
 Logstash
     ↓
 Elasticsearch
     ↓
 Kibana

────────────────────────────────────────────

Metrics
HAProxy + Tomcat + Node Exporter
                ↓
          Prometheus
                ↓
            Grafana
```

---

# ⚙️ Components

## Load Balancer

* **HAProxy 2.8**
* Round-robin load balancing
* Health checks every 2 seconds
* Prometheus metrics endpoint
* Stats dashboard

---

## Application Layer

Two Apache Tomcat 9 containers running a Java visitor counter application.

* Java 11
* JSP + Servlets
* Active-active deployment
* Connected through Pgpool-II

---

## Database Layer

### PostgreSQL 15

* Master-Slave streaming replication
* Fault tolerant architecture

### Pgpool-II 4.6.4

Provides:

* Connection pooling
* Read/write splitting
* Load balancing
* Automatic failover

---

## Centralized Logging

ELK Stack with Rsyslog:

```
Services
   ↓
Rsyslog
   ↓
Logstash
   ↓
Elasticsearch
   ↓
Kibana
```

Features:

* Centralized log collection
* Grok parsing
* Log search and visualization

---

## Monitoring

Prometheus scrapes metrics from:

* HAProxy
* Tomcat MetricsServlet
* Node Exporter

Grafana provides dashboards for visualization.

---

# 🛠 Tech Stack

| Component          | Technology           |
| ------------------ | -------------------- |
| Orchestration      | Docker Compose 3.8   |
| Load Balancer      | HAProxy 2.8          |
| Application Server | Apache Tomcat 9      |
| Language           | Java 11              |
| Database           | PostgreSQL 15        |
| Connection Pooling | Pgpool-II 4.6.4      |
| Logging            | Rsyslog + ELK Stack  |
| Monitoring         | Prometheus + Grafana |
| Build Tool         | Maven                |

---

# 📂 Project Structure

```
Devops-project/
│
├── docker-compose.yml
├── prometheus.yml
├── logstash.conf
│
├── haproxy/
│   └── haproxy.cfg
│
├── pgpool/
│   ├── Dockerfile
│   ├── pgpool.conf
│   ├── pcp.conf
│   └── pool_passwd
│
├── rsyslog-config/
│   └── rsyslog.conf
│
├── test-app/
│   ├── pom.xml
│   ├── index.jsp
│   └── src/main/java/com/
│       ├── HelloServlet.java
│       ├── DBUtil.java
│       ├── MetricsServlet.java
│       └── AppMetrics.java
│
└── logs/
    └── remote.log
```

---

# ✨ Features

* ✅ HAProxy load balancing
* ✅ High availability architecture
* ✅ PostgreSQL master-slave replication
* ✅ Pgpool-II connection pooling
* ✅ Read/write splitting
* ✅ Centralized logging with ELK Stack
* ✅ Prometheus monitoring
* ✅ Grafana dashboards
* ✅ Containerized deployment with Docker Compose
* ✅ Scalable architecture

---

# 🚀 Getting Started

## Prerequisites

* Docker
* Docker Compose
* Maven
* Java 11

---

## Build Application

```bash
cd test-app
mvn clean package
```

---

## Start Services

```bash
docker compose up -d
```

---

## Verify Containers

```bash
docker ps
```

---

# 🌐 Access URLs

| Service         | URL                           |
| --------------- | ----------------------------- |
| Application     | http://localhost:8082         |
| HAProxy Stats   | http://localhost:8404/stats   |
| HAProxy Metrics | http://localhost:8404/metrics |
| Kibana          | http://localhost:5601         |
| Prometheus      | http://localhost:9090         |
| Grafana         | http://localhost:3000         |

---

# 📊 Monitoring Stack

```
HAProxy Metrics
Tomcat Metrics
Node Exporter
        ↓
   Prometheus
        ↓
     Grafana
```

---

# 📝 Logging Stack

```
Application Logs
        ↓
     Rsyslog
        ↓
     Logstash
        ↓
 Elasticsearch
        ↓
      Kibana
```

---

# 📜 License

This project is intended for learning and demonstration purposes.

---

## Author
Himanshu Pujari
