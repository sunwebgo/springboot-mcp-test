@echo off
set "JAVA_HOME=C:\Program Files\Java\latest\jdk-17"
set "Path=C:\Program Files\Java\latest\jdk-17\bin;%Path%"
D:\apache-maven-3.8.8\bin\mvn.cmd -q -DskipTests compile -f D:\springboot-mcp-test\pom.xml