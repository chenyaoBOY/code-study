# KONG use in docker & k8s

simple compose springboot  with kong ...

## KONG
### kong in docker
#### prepare
```
Kong Gateway v2.x
Docker 19.x (Running on Windows)
cURL 7.x
```

#### Installation

#####  Step1: Create a Docker network
```
 docker network create kong-net
```

#####  Step2: Start and prepare Postgres DB
```
docker run -d --name kong-database --network=kong-net \
-e "POSTGRES_USER=kong" -e "POSTGRES_DB=kong" -e "POSTGRES_PASSWORD=kong" \
-p 5432:5432 postgres:9.6
```

```
docker run --rm --network=kong-net \
-e "KONG_DATABASE=postgres" -e "KONG_PG_HOST=kong-database" \
-e "KONG_PG_PASSWORD=kong" kong:latest kong migrations bootstrap
```

##### Step3: Start kong
```
docker run -d --name kong --network=kong-net \
-e "KONG_DATABASE=postgres" -e "KONG_PG_HOST=kong-database" \
-e "KONG_PG_PASSWORD=kong" -e "KONG_PROXY_ACCESS_LOG=/dev/stdout" \
-e "KONG_ADMIN_ACCESS_LOG=/dev/stdout" -e "KONG_PROXY_ERROR_LOG=/dev/stderr" \
-e "KONG_ADMIN_ERROR_LOG=/dev/stderr" -e "KONG_ADMIN_LISTEN=0.0.0.0:8001, 0.0.0.0:8444 ssl" \
-p 8000:8000 -p 8443:8443 -p 8001:8001 -p 8444:8444 kong:latest
```

**note** : default ports


|port|protocol|description|
|---|---|---|
|8000|HTTP|Takes incoming HTTP traffic from Consumers, and forwards it to upstream Services.|
|8443|HTTPS|Takes incoming HTTPS traffic from Consumers, and forwards it to upstream Services.|
|8001|HTTP|Admin API. Listens for calls from the command line over HTTP.|
|8444|HTTPS|Admin API. Listens for calls from the command line over HTTPS.|

##### List docker containers
```
$ docker ps
CONTAINER ID        IMAGE               COMMAND                  CREATED             STATUS              PORTS                                                                NAMES
686b5c07f0d9        kong:latest         "/docker-entrypoint.…"   12 minutes ago      Up 12 minutes       0.0.0.0:8000-8001->8000-8001/tcp, 0.0.0.0:8443-8444->8443-8444/tcp   kong
444e6b2325ce        postgres:9.6        "docker-entrypoint.s…"   18 minutes ago      Up 18 minutes       0.0.0.0:5432->5432/tcp                                               kong-database

```

### Configuring a service


#### Step1: Add a service
```
curl -X POST --url http://***.**.***.**:8001/services/ --data 'name=hello-api' --data 'url=http://***.**.***.**:8080'
```
response：
```
{
	"host": "***.**.***.**",
	"id": "4fab4e6b-f5f1-4098-8aa4-e1d8317c8ad9",
	"protocol": "http",
	"read_timeout": 60000,
	"tls_verify_depth": null,
	"port": 8080,
	"updated_at": 1612752100,
	"ca_certificates": null,
	"created_at": 1612752100,
	"connect_timeout": 60000,
	"write_timeout": 60000,
	"name": "hello-api",
	"retries": 5,
	"path": null,
	"tls_verify": null,
	"tags": null,
	"client_certificate": null
}
```

#### Step2: Add a route

注意 hello-api是之前定义的service name。

```
$ curl -X POST --url http://***.**.***.**:8001/services/hello-api/routes --data 'paths[]=/helloapi'
```
response：
```
{
	"id": "a9a8f419-111f-4d55-ad09-520f29c95a8c",
	"tags": null,
	"paths": [
		"/helloapi"
	],
	"destinations": null,
	"headers": null,
	"protocols": [
		"http",
		"https"
	],
	"strip_path": true,
	"created_at": 1612752259,
	"request_buffering": true,
	"hosts": null,
	"name": null,
	"updated_at": 1612752259,
	"snis": null,
	"preserve_host": false,
	"regex_priority": 0,
	"methods": null,
	"sources": null,
	"response_buffering": true,
	"https_redirect_status_code": 426,
	"path_handling": "v0",
	"service": {
		"id": "4fab4e6b-f5f1-4098-8aa4-e1d8317c8ad9"
	}
}
```

####  Call the API directly
```
$ curl  -i ***.**.***.**:8080/hello
```
response:
```

HTTP/1.1 200
Content-Type: text/plain;charset=UTF-8
Content-Length: 47
Date: Mon, 08 Feb 2021 02:46:33 GMT

Hello World from a Spring boot Java application

```

#### Call the API behind kong
```
$ curl -i -X GET --url http://***.**.***.**:8000/helloapi/hello
```
response:
```
HTTP/1.1 200
Content-Type: text/plain;charset=UTF-8
Content-Length: 47
Connection: keep-alive
Date: Mon, 08 Feb 2021 02:47:42 GMT
X-Kong-Upstream-Latency: 6
X-Kong-Proxy-Latency: 3
Via: kong/2.3.0

Hello World from a Spring boot Java application

```

### configuring a service usr docker but idea

#### Step1:build and run images
```
docker build -t dz/spring-boot-kong:v0.0.1 .

docker run --name spring-boot-kong -d -p  ***.**.***.**:8082:8080 dz/spring-boot-kong:v0.0.1

```

#### Step2 :
之前service route等都已经配置，所以不需要再配置 ，直接调用
```
curl -i -X GET --url http://***.**.***.**:8000/helloapi/hello
```

response：
```
HTTP/1.1 200
Content-Type: text/plain;charset=UTF-8
Content-Length: 47
Connection: keep-alive
Date: Mon, 08 Feb 2021 03:39:46 GMT
X-Kong-Upstream-Latency: 7
X-Kong-Proxy-Latency: 5
Via: kong/2.3.0

Hello World from a Spring boot Java application

```


## kong dashboard

### 1. kong-Dashboard(版本支持落后，不推荐使用)
```
# Start Kong Dashboard
docker run --rm -p 8060:8080 pgbi/kong-dashboard start --kong-url http://kong:8001

# Start Kong Dashboard on a custom port
docker run --rm -p [port]:8080 pgbi/kong-dashboard start --kong-url http://kong:8001

# Start Kong Dashboard with basic auth
docker run --rm -p 8060:8080 pgbi/kong-dashboard start \
  --kong-url http://kong:8001
  --basic-auth user1=password1 user2=password2

# See full list of start options
docker run --rm -p 8060:8080 pgbi/kong-dashboard start --help
```

### 2.konga


```
待补充
```




##  k8s
```
待补充
```
