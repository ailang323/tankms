# tankms
Tank Message Service  
技术栈：Spring Boot、Spring Kafka
组件：
tankms-consumer：无需关心消息的订阅读取，只需关心Topic下的消息处理，并将消息体直接转换成实体，提高代码的复用性以及接口约束；
