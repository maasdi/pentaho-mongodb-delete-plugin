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
package com.github.maasdi.mongo;

import org.pentaho.di.core.exception.KettleException;

public class MongoDbException extends KettleException {

  public MongoDbException() {
    super();
  }

  public MongoDbException(String message, Throwable cause) {
    super(message, cause);
  }

  public MongoDbException(String message) {
    super(message);
  }

  public MongoDbException(Throwable cause) {
    super(cause);
  }

  private static final long serialVersionUID = -5312035742249234075L;

}
