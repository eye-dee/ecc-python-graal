FROM oracle/graalvm-ce:19.3.0

RUN gu install python
RUN yum install git -y
RUN git clone https://github.com/sausageRoll/ecc-python-graal.git
WORKDIR ecc-python-graal
RUN ./gradlew build jar
RUN ls build
ENTRYPOINT ["java", "-jar", "/ecc-python-graal/build/libs/ecc-python-graal-1.0-SNAPSHOT.jar"]
