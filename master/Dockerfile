FROM payara/server-full

ENV PAYARA_HOME /opt/payara41
ENV DOMAIN_HOME ${PAYARA_HOME}/glassfish/domains/domain1
ENV ASADMIN ${PAYARA_HOME}/bin/asadmin
ENV AS_ADMIN_PASSWORDFILE /opt/pwdfile

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
    ${ASADMIN} stop-domain

EXPOSE 7676

ENTRYPOINT ${ASADMIN} start-domain -v domain1