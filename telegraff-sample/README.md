# Telegraff Sample

### Building

```
$ ./gradlew build installDist
```

### Running

```
$ ./build/install/telegraff-sample/bin/telegraff-sample
```

### Known Limitations

* Can not access internal classes from Kotlin compiler with bootJar (fat jar)