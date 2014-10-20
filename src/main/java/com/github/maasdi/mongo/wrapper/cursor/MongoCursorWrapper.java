/**
 * Copyright (C) 2014 Maas Dianto (maas.dianto@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.maasdi.mongo.wrapper.cursor;

import org.pentaho.di.core.exception.KettleException;

import com.mongodb.DBObject;
import com.mongodb.ServerAddress;

public interface MongoCursorWrapper {

  boolean hasNext() throws KettleException;

  DBObject next() throws KettleException;

  ServerAddress getServerAddress() throws KettleException;

  void close() throws KettleException;

  MongoCursorWrapper limit( int i ) throws KettleException;

}
