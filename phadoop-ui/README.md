# phadoop-ui
curl -X POST --include http://localhost:8080/job
curl --include http://localhost:8080/jobs

FIXME

## Usage
Launch the application by issuing one of the following commands:

```shell
lein run <port>
lein ring server
```

You can generate a standalone jar and run it:

```shell   
lein uberjar
java -jar target/phadoop-ui-0.1.0-SNAPSHOT-standalone.jar
```

You can also generate a war to deploy on a server like Tomcat, Jboss...

```shell
lein ring uberwar
```

## License

Copyright © 2013 FIXME

Distributed under the Eclipse Public License, the same as Clojure.
