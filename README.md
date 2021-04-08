
## Coding Question

I had approached the design with the concept of FIFO implementation using a LinkedList and as well storing a incremental sum. The data strcture will define a window size when creating. Then we will use this window to validate whether all the elements exist within the window, if they dont then we will use poll operation to take elements out from front of the queue. While we decrement the incremental sum. Thus, ensuring that the sum in the exact incremented sum of the values that would exist in the window. Thus each add operation is time complexitiy O(1) and decrementing poll operation would be similar O(1). Then when we want the SMA we would just divide by the incremental sum with the window size. 

 - Achieved moving average calculation time complexity O(1) due to internal queue/linked-list structure (FIFO)

## Design Question



The main goal of our design is to demonstrate how we will create a solution that is resilient and fault-tolerant. It will be a data-driven architecture designed for real-time processing, historical storage, and rendering metrics. I've created a high-level overview of what tools we will use, and some reference information.

This design compromises of following components:

Apache Kafka: A publish/subscribe messaging system that is scalable, reliable, and efficient. With the added benefit of retention of the disk, for replay. Its replication protocol guarantees that once a message has been written successfully to the leader replica, it will be replicated to all available replicas. Ingestion portion of the design

> > Data of many different types can easily coexist on the same cluster, divided into topics for each type of data. Producers and consumers only need to concern themselves with the topics they are interested in. LinkedIn goes one step further and defines four categories of messages: queuing, metrics, logs, and tracking data that each lives in their own cluster. When combined, the Kafka ecosystem at LinkedIn is sent over 800 billion messages per day which amounts to over 175 terabytes of data.

Apache Flink: It provides data distribution, communication, state management, and fault tolerance for distributed computations over data streams. We will bind Kafka topics as data sources and will handle the datastream to compute any additional business logic prior to pushing to a sink (DB or another Kafka topic). Given we will have many Flink jobs running in parallel, we will implement alerting mechanism in Prometheus/Grafana to track consumer lag and errors in Splunk(logging).  Preparation & Computation

	Apache Flink provides the following benefits:
		- Stateful Streaming 
		   1. Fault tolerance on processing level (checkpoint)
		   2. Savepoints (update without data loss)
		- Performance 
		  1. High throughput
		  2. Low Latency
		  3. Excellent memory management
		... more

> > Flink is designed to run stateful streaming applications at any scale. Applications are parallelized into possibly thousands of tasks that are distributed and concurrently executed in a cluster. Therefore, an application can leverage virtually unlimited amounts of CPUs, main memory, disk, and network IO. Moreover, Flink easily maintains a very large application state. Its asynchronous and incremental checkpointing algorithm ensures minimal impact on processing latencies while guaranteeing exactly-once state consistency.

It is also noted that Flink's streaming capability is far better than Spark (as spark handles stream in form of micro-batches) and has native support for streaming. and has been heavily adopted by many organizations such as Alibaba. Who has reported that, 

> >During this year's Double 11 Global Shopping Festival, the peak traffic processing rate of Alibaba Cloud Realtime Compute for Apache Flink reached four billion records per second."


TimescalDB: We will handle time-series data and operational analytics, so TimescalDB is an ideal candidate.  It is the only open-source time-series database that supports full SQL. Optimized for fast ingest and complex queries, TimescaleDB is easy to use as a traditional relational database, yet scales in ways previously reserved for NoSQL databases. In regards to complex querying, TimescaleDB offers lower latency in comparison to its peers. As well it provides simple connectors to presentation tools such as power bi.

> > 5 TimescaleDB nodes outperform a 30 node Cassandra cluster, with higher inserts, up to 5800x faster queries, 10% the cost, a much more flexible data model, and full SQL.

Prometheus + Grafana: Prometheus acts as the storage backend and open source Grafana as the interface for analysis and visualization. Prometheus collects metrics from monitored targets by scraping metrics from HTTP endpoints on these targets. Monitoring System

PowerBi: It is a technology-driven business intelligence tool provided by Microsoft for analyzing and visualizing raw data to present actionable information. It combines business analytics, data visualization, and best practices that help an organization to make data-driven decisions. Rendering customer-specific metrics, we can provide them with a unique dashboard they have access to which is connected to our TimescalDB. As well PowerBi has the functionality of refreshing data/metrics/graphs at a scheduled period. So our use case we will schedule it less than 30 min. Presentation

Also, replaying/reprocessing data would be easy at many levels. For instance, you can replay messages from a given offset in Kafka that will be reprocessed by Flink. Or you can use JDBC connector to query the DB from an application that stores aggregations or metrics.