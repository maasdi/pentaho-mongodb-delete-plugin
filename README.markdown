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

Intallation
--------
1. Download binary [here][download-release] then extract the zip file OR If you build from source code go to folder target/pentaho-mongodb-delete-plugin
2. Copy folder pentaho-mongodb-delete-plugin and all contents
3. Paste to folder ${KETTLE_INSTALATION_DIRECTORY}/plugins/steps/
4. If your PDI still running, please restart and you should see new steps MongoDB Delete under Big Data category

![alt text][step]

Documentation
-------------
Please go to [wiki page][docs] for the documentation.

Issue
-----
Issue !? Please report [here][issue]

License
-------
Licensed under the Apache License, Version 2.0.

[step]: https://raw.githubusercontent.com/maasdi/pentaho-mongodb-delete-plugin/master/MongDB%20Delete.png "MongoDB Delete Step"
[download-release]: https://github.com/maasdi/pentaho-mongodb-delete-plugin/releases/download/1.0.0-RELEASE/pentaho-mongodb-delete-plugin-1.0.0-RELEASE.zip
[docs]: https://github.com/maasdi/pentaho-mongodb-delete-plugin/wiki/MongoDB-Delete
[issue]: https://github.com/maasdi/pentaho-mongodb-delete-plugin/issues