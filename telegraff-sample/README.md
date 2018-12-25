# Telegraff Sample

## Gradle

### Fat JAR

Unfortunately, some libraries like JRuby or Kotlin embedded compiler have problems while they are a part of "Fat JAR". 
To deal with this issue, you can [unpack these libraries during runtime](https://docs.spring.io/spring-boot/docs/current/gradle-plugin/reference/html/#packaging-executable-configuring-unpacking).

However, all classes and libraries that you want to reference from Kotlin scripts (Handlers) should be unpacked as well.

So, the minimum configuration for Gradle is: 

```
bootJar {
    requiresUnpack "**/**kotlin**.jar"
    requiresUnpack "**/**telegraff**.jar"
}
```

But this won't let you use inner project classes and components from Handlers (see `NameHandler.kts`). 
And I don't know how to handle this and not sure it is feasible.

So, the only reasonable solution is to avoid "Fat JAR" and Gradle `application` plugin will help in it.

### Building

First of all, make sure you activated jar generation and set main class:

```
application {
    mainClassName = "me.ruslanys.telegraff.sample.ApplicationKt"
}

jar {
    enabled = true
}
```

It let you use Gradle `application` plugin:

```
$ ./gradlew build
$ ./gradlew installDist
```

### Running


```
$ ./build/install/telegraff-sample/bin/telegraff-sample
```

## Docker

### Dockerimage

```
$ docker build -t ruslanys/telegraff -f docker/Dockerfile . 
```

**NOTE:** Do not forget to build the application before.

### Dockerimage from sources

```
$ docker build -t ruslanys/telegraff -f docker/build/Dockerfile .
```

### Development

If you are not from the JVM world and don't want to sort out java tools and libraries, 
this section will help you to understand how to develop inside docker image.


1. Build `development` image:

```
$ docker build -t ruslanys/telegraff:development -f docker/development/Dockerfile .
```

2. Run this image by mounting local directory and port:

```
$ docker run -it --name telegraff-development -v $(pwd):/root/application -p 8080:8080 ruslanys/telegraff:development
```

3. You can reuse created container:

```
$ docker start -ai telegraff-development
```
