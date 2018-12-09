# Telegraff Sample

## Gradle

### Far JAR

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

So, the only reasonable solution is to avoid "Fat JAR" and Gradle `application` plugin will help with this.

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

Which let you use Gradle `application` plugin:

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

NOTE: Do not forget to build the application before.