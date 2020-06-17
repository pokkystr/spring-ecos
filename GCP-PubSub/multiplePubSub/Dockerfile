FROM build.devops.everydaynext.com/uisp/partner-events-listener-app:__IMAGE_VERSION__

USER root

RUN mkdir -p /etc/ssl/certs/additional
COPY truststore /etc/ssl/certs/additional

RUN for file in /etc/ssl/certs/additional/*; do echo "yes" | $JAVA_HOME/bin/keytool -importcert -file $file -keystore $JAVA_HOME/lib/security/cacerts -storepass changeit -alias $file; done
RUN rm -rf /etc/ssl/certs/additional

USER uisp