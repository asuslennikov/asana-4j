# Asana4J [![Build Status](https://travis-ci.org/asuslennikov/asana4j.svg?branch=master)](https://travis-ci.org/asuslennikov/asana4j)
An unofficial Java client library for the [Asana](https://asana.com/) API v1.0.

## License
This library licensed under the [MIT License (MIT)](https://opensource.org/licenses/MIT)

## Current status
### Features
* Authentication - ok (Api Key, Implicit Grant, Authorization Code Grant, Personal Access Token)
* API options - ok
* Errors - basic handling
* Custom External Data - ok

### Entities
* Attachments - ok
* Events - no support
* Projects - ok
* Stories - ok
* Tags - ok
* Tasks - ok (except the membership field)
* Teams - ok
* Typeahead - no support
* Users - ok
* Webhooks - no support
* Workspaces - ok


## Requirements & dependencies
* Java 7 or greater
* [org.json](http://www.json.org/java/)

## Building The Code
Clone the repo and perform the following command from the root directory:
```shell
mvn clean install -P all
```

## Usage & examples
You can add this library to your project via maven dependencies:
```shell
<dependency>
  <groupId>ru.jewelline.asana4j</groupId>
  <artifactId>api</artifactId>
  <version>1.0.0-SNAPSHOT</version>
</dependency>
<dependency>
  <groupId>ru.jewelline.asana4j</groupId>
  <artifactId>core</artifactId>
  <version>1.0.0-SNAPSHOT</version>
</dependency>
```
See examples in the /examples sub-directory
