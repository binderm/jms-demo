# jms-demo
Java EE applications for demonstration of the Java Message Service (JMS) API.

The project consists of the following sub-projects:
* synchronous
* shopping
* shipping
* billing

The sub-project commons contains code which is used by shopping, shipping and billing.

## Synchronous
### Building
```
./gradlew build :synchronous
```
This builds a single WAR named `synchronous.war` which are used for deployment in the next step.

### Deployment
The packaged WAR should work for every Java EE 7 application server with full profile.
Prior to the deployment the following resources have to be configured within the application server:
* a JMS `ConnectionFactory` with JNDI name `jms/SynchronousConnectionFactory` <br />
Client ID have to be set (to an arbitrary name) in order to create a non-shared durable subscription later.
* a JMS `Destination` of type `Queue` with JNDI name `jms/NewOrdersQueue`
* a JMS `Destination` of type `Topic` with JNDI name `jms/NewOrdersTopic`

### Execution
One can open several `sender.xhtml` and `receiver.xhtml` in different browser tabs to send messages over a queue or topic.
Different options for sending and receiving messages synchronously can be configured on these pages.
Furthermore it is possible to deploy the application in to different application server instances to create a more realistic messaging environment.

## Shopping, Shipping, Billing
These applications represent different applications of a online shop.
Shopping generates a order every 2s and sends it to either a queue or topic.
Both shipping and billing receive new orders from the destination to prepare the shipment and create the bill, respectively.
The process can be traced via log messages in the server log.

### Synchronous mode
When deployed in synchronous mode shipping and billing receive new orders synchronously every 2s.

#### Building
```
./gradlew clean build :shopping :shipping :billing build syncWar
```
This builds WARs named `shopping-sync.war`, `shipping-sync.war`, `billing-sync.war` which are used for deployment in the next step.

#### Configuration
Configuration is done via ```<app>.properties``` files located in the ```docker/``` directory.

| Config file | Property | Valid values | Property Description |
| --- | --- | --- | --- |
| **```shopping.property```** | ```jms_demo.destinationType``` | ```queue```, ```topic``` | Type of destination to be used for placing new orders in |
| **```shipping.property```** | ```jms_demo.destinationType``` | ```queue```, ```topic``` | Type of destination to be used for receiving new orders from |
| | ```jms_demo.subscription.durable``` | ```true```, ```false``` | Whether a durable subscription should be used for reception of new orders |
| | ```jms_demo.subscription.shared``` | ```true```, ```false``` | Whether a shared subscription should be used for reception of new orders |
| | ```jms_demo.subscription.name``` | _e.g. ```prepareShipping```_ | Name of the subscription (required for durable and shared subscriptions)  |
| | | ```jms_demo.receiver.timeout``` | _e.g. ```500```_ | Timeout for receiving new orders synchronously in milliseconds |
| **```billing.property```** | ```jms_demo.destinationType``` | ```queue```, ```topic``` | Type of destination to be used for receiving new orders from |
| | ```jms_demo.subscription.durable``` | ```true```, ```false``` | Whether a durable subscription should be used for reception of new orders |
| | ```jms_demo.subscription.shared``` | ```true```, ```false``` | Whether a shared subscription should be used for reception of new orders |
| | ```jms_demo.subscription.name``` | _e.g. ```createBill```_ | Name of the subscription (required for durable and shared subscriptions)  |

### Asynchronous mode
When deployed in synchronous mode shipping and billing receive new orders asynchronously using Message Driven Beans (MDBs).
After processing a new order event messages for _shipping-prepared_ and _bill-created_ are sent to a notification queue.
The notifications are received asynchronously by another MDB in the shopping.

#### Building
```
./gradlew clean build :shopping :shipping :billing build asyncWar
```
This builds WARs named `shopping-async.war`, `shipping-async.war`, `billing-async.war` which are used for deployment in the next step.

#### Configuration
In asynchronous mode only the shopping application needs to be configured to send new orders to a topic.

```docker/shopping.properties```:
```
jms_demo.destinationType=topic
```

### Deployment and execution
To simulate a realistic environment it is recommended to use Docker containers.
To set up the docker images and network run the following scripts which are located in `docker/`.
```
./create-docker-images.sh
./create-docker-network.sh
```

First start the master instance which runs the JMS provider in EMBEDDED mode: ```./run-master.sh```

Slave instances can then be started via ```./run-slave.sh <app> <mode>``` <br />
with ```<app>``` be either ```shopping```, ```shipping``` or ```billing``` <br />
and with ```<mode>``` be either ```sync``` or ```async```.

**NOTE:** To execute these scripts your user must be a member of the ```docker```-group or escalate its privileges to root.
