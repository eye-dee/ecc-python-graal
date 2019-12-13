FROM oracle/graalvm-ce:19.3.0

RUN gu install python

RUN echo 'import java \
          array = java.type("int[]")(4) \
          array[2] = 42 \
          print(array[2])' > polyglot.py
