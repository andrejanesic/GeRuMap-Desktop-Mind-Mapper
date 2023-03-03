# GeRuMap

gerumap-tim_andrejanesic_aleksavucinic created by GitHub Classroom

Mind map application created in Java (Swing) as part of the [Software Design (3307)](https://raf.edu.rs/en/component/content/article/192-english/subjects/3307-design-of-software) pair assignment.

The project was conducted with the goal of practising:

- Software architecture / requirement modelling
- Design patterns & OOP
- Collaborative programming (Git)
- Client communication, documentation and more

## 1. Requirements & Documentation

The project has been documented using and may be accessed on the links below:

1. Requirements (client request):
   **https://gerumap.notion.site/GeRuMap-Requirements-5484f5c082414cefa93308e405f314be**

2. Documentation:
   **https://andrejanesic.github.io/GeRuMap-Desktop-Mind-Mapper/**

## 2. Authors

_Authored by:_

**Aleksa Vucinic RN60/20** | _Comp Sci Undergrad_ <br>
[School of Computing, Belgrade](https://www.linkedin.com/school/racunarski-fakultet/) <br>
avucinic6020rn@raf.rs

[**Andreja Nesic RN31/19**](https://www.linkedin.com/in/andreja-nesic/) | _Comp Sci Undergrad_ <br>
[School of Computing, Belgrade](https://www.linkedin.com/school/racunarski-fakultet/) <br>
office@andrejanesic.com <br>
anesic3119rn@raf.rs

_Mentored by:_

**dr. Ana Markovic** | Professor at [School of Computing, Belgrade](https://www.linkedin.com/school/racunarski-fakultet/) <br>
[Academic achievements & biography](https://raf.edu.rs/o-nama/nastavnici-i-saradnici/item/6019-ana-markovic) <br>
amarkovic@raf.rs

## 3. Build

It's possible to build this code via 2 methods:

### 3.1. Docker (Recommended)

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

### 3.2. Local

Running this code locally is not recommended due to possible issues caused by your Java installation and JVM parameters.

To build the code locally, run Maven on your local machine and execute the code:

```shell
# Build code
mvn clean install

# Execute main
mvn exec:java -Dexec.mainClass="rs.edu.raf.dsw.rudok.app.AppCore"
```
