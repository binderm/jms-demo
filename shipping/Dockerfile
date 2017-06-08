FROM payara/server-full

ENV PAYARA_HOME /opt/payara41
ENV DOMAIN_HOME ${PAYARA_HOME}/glassfish/domains/domain1
ENV ASADMIN ${PAYARA_HOME}/bin/asadmin
ENV AS_ADMIN_PASSWORDFILE /opt/pwdfile
ENV jms_demo_configPath=/home/payara/config/shipping.properties

RUN ${ASADMIN} start-domain && \
    ${ASADMIN} create-jms-resource \
        --restype javax.jms.Queue \
        --property Name=new_orders_queue \
        jms/NewOrdersQueue && \
    ${ASADMIN} create-jms-resource \
        --restype javax.jms.Topic \
        --property Name=new_orders_topic \
        jms/NewOrdersTopic && \
    ${ASADMIN} create-jms-resource \
        --restype javax.jms.Queue \
        --property Name=customer_notifications \
        jms/CustomerNotifications && \
    ${ASADMIN} create-jms-resource \
        --restype javax.jms.ConnectionFactory \
        --property ClientId=shipping \
        jms/AppConnectionFactory && \
    ${ASADMIN} create-jms-host --mqhost 172.18.0.2 --mqport 7676 --mquser admin --mqpassword admin jms_demo_master && \
    ${ASADMIN} set configs.config.server-config.jms-service.type=REMOTE && \
    ${ASADMIN} set configs.config.server-config.jms-service.default-jms-host=jms_demo_master && \
    ${ASADMIN} stop-domain

COPY build/libs/shipping-sync.war $DOMAIN_HOME/autodeploy
CMD ["mkdir","-p","/home/payara/config"]

EXPOSE 7676

ENTRYPOINT ${ASADMIN} start-domain -v domain1