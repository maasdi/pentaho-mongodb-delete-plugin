Pentaho MongoDB Delete Plugin
=======================
[![Build Status](https://drone.io/github.com/maasdi/pentaho-mongodb-delete-plugin/status.png?ok=1)](https://drone.io/github.com/maasdi/pentaho-mongodb-delete-plugin/latest)

The Kettle Plugin to provides kettle steps for delete document inside a Mongo DB collection.

Building
--------
The Pentaho MongoDB Delete Plugin is built with Apache Maven for dependency management. To build from source code.

    $ git clone git://github.com/maasdi/pentaho-mongodb-delete-plugin.git
    $ cd pentaho-mongodb-delete-plugin
    $ mvn package

Maven will compile and package all you need and will create new folder 'pentaho-mongodb-delete-plugin' under target directory.

Installation
--------
**Install from Pentaho Marketplace**

1. From PDI, Select menu Help - Marketplace
2. Search MongoDB Delete Plugin, click MongoDB Delete Plugin row from search result
3. To install, click 'Install this plugin' button

**Manual Installation**

1. Download binary [here][download-release] then extract the zip file
   OR If you build from source code go to folder target/pentaho-mongodb-delete-plugin
2. Copy folder pentaho-mongodb-delete-plugin and all contents
3. Paste to folder ${KETTLE_INSTALATION_DIRECTORY}/plugins/steps/
4. If your PDI still running, please restart and you should see new steps MongoDB Delete under Big Data category

![alt text][step]

Documentation
-------------
Please go to [wiki page][docs] for the documentation.

Issue
-----
Do you have issue while using this plugin? Please report [here][issue]

Sample
------
Here are the samples how to use:

1. [delete-all.ktr](https://raw.githubusercontent.com/maasdi/pentaho-mongodb-delete-plugin/master/src/main/resources/sample/delete-all.ktr)
2. [delete-by-incoming-row.ktr](https://raw.githubusercontent.com/maasdi/pentaho-mongodb-delete-plugin/master/src/main/resources/sample/delete-by-incoming-row.ktr)
3. [delete-by-json-query.ktr](https://raw.githubusercontent.com/maasdi/pentaho-mongodb-delete-plugin/master/src/main/resources/sample/delete-by-json-query.ktr)
4. [delete-by-json-query-exec-each-row.ktr](https://raw.githubusercontent.com/maasdi/pentaho-mongodb-delete-plugin/master/src/main/resources/sample/delete-by-json-query-exec-each-row.ktr)

License
-------
Licensed under the Apache License, Version 2.0.

[step]: https://raw.githubusercontent.com/maasdi/pentaho-mongodb-delete-plugin/master/MongDB%20Delete.png "MongoDB Delete Step"
[download-release]: https://github.com/maasdi/pentaho-mongodb-delete-plugin/releases/download/1.0.0-RELEASE/pentaho-mongodb-delete-plugin-1.0.0-RELEASE.zip
[docs]: http://maasdi.github.io/pentaho-mongodb-delete-plugin
[issue]: https://github.com/maasdi/pentaho-mongodb-delete-plugin/issues