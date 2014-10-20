Pentaho MongoDB Delete Plugin
=======================

The Kettle Plugin to provides kettle steps for delete document inside a Mongo DB collection.

Building
--------
The Pentaho MongoDB Delete Plugin is built with Apache Maven for dependency management. To build from project.

    $ git clone git://github.com/maasdi/pentaho-mongodb-delete-plugin.git
    $ cd pentaho-mongodb-delete-plugin
    $ mvn package

Maven will compile and package all you need and will create new folder 'pentaho-mongodb-delete-plugin' under target directory.

Intallation
--------
Copy folder pentaho-mongodb-delete-plugin and all contents and paste to ${KETTLE/PDI INSTALATION DIRECTORY}/plugins/steps.
If your PDI still running, please restart and you should see new steps MongoDB Delete under Big Data category
![alt text][step]

License
-------
Licensed under the Apache License, Version 2.0.

[step]: https://raw.githubusercontent.com/maasdi/pentaho-mongodb-delete-plugin/master/MongDB%20Delete.png "MongoDB Delete Step"