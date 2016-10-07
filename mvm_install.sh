#!/bin/sh


mvn install:install-file -Dfile=./lib/spring-patch-1.0.2.jar -DgroupId=org.springframework -DartifactId=spring-patch -Dversion=1.0.2 -Dpackaging=jar

