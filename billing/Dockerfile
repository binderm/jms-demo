FROM payara/server-full

ENV PAYARA_HOME /opt/payara41
ENV DOMAIN domain1
ENV DOMAIN_HOME ${PAYARA_HOME}/glassfish/domains/${DOMAIN}
ENV ASADMIN ${PAYARA_HOME}/bin/asadmin
ENV AS_ADMIN_PASSWORDFILE /opt/pwdfile
ENV jms_demo_configPath=/config.properties

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
        --property ClientId=shopping \
        jms/AppConnectionFactory && \
    ${ASADMIN} set configs.config.server-config.jms-service.type=REMOTE && \
    ${ASADMIN} set configs.config.server-config.jms-service.jms-host.default_JMS_host.host=172.18.0.2 && \
    ${ASADMIN} stop-domain

EXPOSE 7676

ENTRYPOINT ${ASADMIN} start-domain -v ${DOMAIN}