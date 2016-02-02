# Nuxeo RabbitMQ Event Forwarder

The Nuxeo RabbitMQ event forwarder is a listener which forward all default document events to a given RabbitMQ server on a `nuxeo` queue.

## Getting Started

###Server

- [Download a Nuxeo server](http://www.nuxeo.com/en/downloads) (the zip version)

- Unzip it

- Linux/Mac:
    - `NUXEO_HOME/bin/nuxeoctl start`
- Windows:
    - `NUXEO_HOME\bin\nuxeoctl.bat start`

- From your browser, go to `http://localhost:8080/nuxeo`

- Follow Nuxeo Wizard by clicking 'Next' buttons, re-start once completed

- Check Nuxeo correctly re-started `http://localhost:8080/nuxeo`
  - username: Administrator
  - password: Administrator

## Build - Deploy - Configure

- `mvn clean install`
- `cp target/nuxeo-rabbitmq-*.jar` to `NUXEO_HOME/nxserver/bundles`
- Specify RabbitMQ server Location in `NUXEO_HOME/bin/nuxeo.conf`:
	
		org.nuxeo.rabbitmq.location=<url_ip_server>

## Reporting Issues

We are glad to welcome new developers on this initiative, and even simple usage feedback is great.

- Ask your questions on [Nuxeo Answers](http://answers.nuxeo.com)
- Report issues on this GitHub repository (see [issues link](http://github.com/nuxeo-sandbox/nuxeo-rabbitmq/issues) on the right)
- Contribute: Send pull requests!


# About Nuxeo

Nuxeo dramatically improves how content-based applications are built, managed and deployed, making customers more agile, innovative and successful. Nuxeo provides a next generation, enterprise ready platform for building traditional and cutting-edge content oriented applications. Combining a powerful application development environment with SaaS-based tools and a modular architecture, the Nuxeo Platform and Products provide clear business value to some of the most recognizable brands including Verizon, Electronic Arts, Netflix, Sharp, FICO, the U.S. Navy, and Boeing. Nuxeo is headquartered in New York and Paris. More information is available at [www.nuxeo.com](http://www.nuxeo.com/).
