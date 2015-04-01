Swagger for Elasticsearch [![Build Status](https://travis-ci.org/timschlechter/swagger-for-elasticsearch.svg?branch=master)](https://travis-ci.org/timschlechter/swagger-for-elasticsearch)
=========================
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
### Getting started ###
Clone this repo:
```
git clone https://github.com/timschlechter/swagger-for-elasticsearch.git
```

This plugin uses the sources of some submodules to keep in sync with the [official spec files](https://github.com/elastic/elasticsearch/tree/master/rest-api-spec/api) and [swagger-ui](https://github.com/timschlechter/swagger-for-elasticsearch-ui). The spec files are in the main Elasticsearch repo, so we have to pull in every version of Elasticsearch we would like to support. I don't have a better way to do this, so:
```
cd swagger-for-elasticsearch
git submodule init
git submodule update
```
...and grab youself a cup of coffee. If someone has a suggestion to make this easier, please contact me :-)

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

Screenshots
-----------

<img src="https://dl.dropboxusercontent.com/u/17880436/swagger-for-elasticsearch.png" width="100%" />
