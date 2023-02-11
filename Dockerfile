FROM openjdk:11

COPY target/*.jar application.jar
COPY docker/entrypoint.sh entrypoint.sh

ENV LANG en_US.UTF-8
ENV LANGUAGE en_US:en
ENV LC_ALL en_US.UTF-8

RUN chmod +x entrypoint.sh

ENTRYPOINT ["./entrypoint.sh"]