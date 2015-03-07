Swagger plugin for Elasticsearch [![Build Status](https://travis-ci.org/TimSchlechter/elasticsearch-swagger.svg?branch=master)](https://travis-ci.org/TimSchlechter/elasticsearch-swagger)
================================
Provides [Swagger](http://swagger.io/) documentation for your Elasticsearch cluster.

__Note:__ This plugin does not aim to be the ultimate management tool for Elasticsearch, there are [far better plugins for doing this](http://www.elasticsearch.org/guide/en/elasticsearch/reference/current/modules-plugins.html#known-plugins). It tries to make it easy to discover and learn about the APIs, and makes it possible to invoke/test APIs quickly.

### Features###
* API documentation per index/alias
* Documented APIs for every stored type
* Automatically adapts to the version of your Elasticsearch installation
* Includes a slightly modified version of [Swagger UI](https://github.com/swagger-api/swagger-ui) to make it easy to switch index/alias API's
* Dynamic generation of the documentation, just install the plugin and go!
* Supports Java 7 and 8, no support for Java 6

Development
-----------
### Package ###
```
mvn package
```
This will 
* generate the release package in ```/target/releases```
* put the Swagger UI in ```/target/elasticsearch/plugins/swagger/_site``` so it can be used during debugging

### Run/debug ###
```
mvn exec:java
```
This will start Elasticsearch on [http://localhost:9200](http://localhost:9200). You can access the plugin by going to [http://localhost:9200/_plugin/swagger](http://localhost:9200/_plugin/swagger).

Installation
------------
This plugin is currently in development, no releases have been published yet. If you would like to install the plugin manually, you can do so by getting the build release package (see Development) and run the following command from within your Elasticsearch folder:

```
./bin/plugin --u file:///path/to/plugin.zip --i swagger 
```
 

