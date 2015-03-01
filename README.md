Swagger plugin for Elasticsearch
================================
Provides [Swagger](http://swagger.io/) documentation for your Elasticsearch cluster. Makes it real easy to discover and learn the possibilities of the Elasticsearch API.

### Features###
* API documentation per index/alias
* Documented APIs for every stored type
* Automatically adapts to the version of your Elasticsearch installation
* Includes a slightly modified version of [Swagger UI](https://github.com/swagger-api/swagger-ui) to make it easy to switch index/alias API's
* Dynamic generation of the documentation, just install the plugin and go!
 

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
 

