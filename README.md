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
* a JMS `ConnectionFactory` with JNDI name `jms/SynchronousConnectionFactory` \
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

### Configuration


### Synchronous mode
When deployed in synchronous mode shipping and billing receive new orders synchronously every 2s.

#### Building
```
./gradlew clean build :shopping :shipping :billing build syncWar
```
This builds WARs named `shopping-sync.war`, `shipping-sync.war`, `billing-sync.war` which are used for deployment in the next step.

#### Deployment and execution

### Asynchronous mode
When deployed in synchronous mode shipping and billing receive new orders asynchronously using Message Driven Beans (MDBs).
After processing a new order event messages for _shipping-prepared_ and _bill-created_ are sent to a notification queue.
The notifications are received asynchronously by another MDB in the shopping.

#### Build
```
./gradlew clean build :shopping :shipping :billing build asyncWar
```
This builds WARs named `shopping-async.war`, `shipping-async.war`, `billing-async.war` which are used for deployment in the next step.

#### Deployment and execution
