FROM openjdk:8-jre
LABEL author=Godfunc@outlook.com

EXPOSE 9899
ADD ./build/libs/paopao-*.jar app.jar
RUN bash -c 'touch /app.jar'
ENTRYPOINT ["java","-jar","/app.jar"]