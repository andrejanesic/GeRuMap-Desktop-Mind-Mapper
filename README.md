# gerumap-tim_andrejanesic_aleksavucinic
gerumap-tim_andrejanesic_aleksavucinic created by GitHub Classroom

Studenti:
Aleksa Vucinic RN60/20
Andreja Nesic RN31/19

## 1. Build

It's possible to build this code via 2 methods:

### 1.1. Docker (Recommended)

The Docker-based build provides a tested, working environment where you'll be able to execute the code.

The provided Dockerfile sets up a Linux distro with the necessary libraries for displaying the app GUI.

Firstly, build the Dockerfile:

```shell
docker build -t gerumap .
```

Next, you'll need a server/relay tool to be able to visualize the app GUI on your local machine (such as [socat](https://www.redhat.com/sysadmin/getting-started-socat) for Linux or [VcXsrv](https://sourceforge.net/projects/vcxsrv/) for Windows.)

_Socat example for Linux:_

```shell
# Run socat listener
socat TCP-LISTEN:6000,reuseaddr,fork UNIX-CLIENT:\"$DISPLAY\"

# Run image
docker run -v /tmp/.X11-unix:/tmp/.X11-unix -e DISPLAY=$(ipconfig getifaddr en0):0 gerumap
```

_VcXsrv example for Windows:_

```shell
# Make sure VcXsrv is running
# ...

# Run image
docker run -it --rm -d -e DISPLAY={Your IP address}:0 gerumap
```

### 1.2. Local

Running this code locally is not recommended due to possible issues caused by your Java installation and JVM parameters.

To build the code locally, run Maven on your local machine and execute the code:

```shell
# Build code
mvn clean install

# Execute main
mvn exec:java -Dexec.mainClass="Main"
```