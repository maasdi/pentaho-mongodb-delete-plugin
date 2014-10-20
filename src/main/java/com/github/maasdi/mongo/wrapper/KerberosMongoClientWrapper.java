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
package com.github.maasdi.mongo.wrapper;

import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.core.logging.LogChannelInterface;
import org.pentaho.di.core.variables.VariableSpace;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;

import com.github.maasdi.di.trans.steps.mongodb.MongoDbMeta;
import com.github.maasdi.mongo.AuthContext;
import com.github.maasdi.mongo.KettleKerberosHelper;
import com.github.maasdi.mongo.wrapper.collection.KerberosMongoDeleteCollectionWrapper;
import com.github.maasdi.mongo.wrapper.collection.MongoDeleteCollectionWrapper;

public class KerberosMongoClientWrapper extends UsernamePasswordMongoClientWrapper {
  private final AuthContext authContext;

  public KerberosMongoClientWrapper( MongoDbMeta meta, VariableSpace vars, LogChannelInterface log ) throws KettleException {
    super( meta, vars, log );
    authContext = new AuthContext( KettleKerberosHelper.login( vars, getUser() ) );
  }

  public KerberosMongoClientWrapper( MongoClient client, LogChannelInterface log, String username, AuthContext authContext ) {
    super( client, log, username, null );
    this.authContext = authContext;
  }

  @Override
  protected MongoCredential getCredential( MongoDbMeta meta, VariableSpace vars ) {
    return MongoCredential.createGSSAPICredential( vars.environmentSubstitute( meta.getAuthenticationUser() ) );
  }

  @Override
  protected void authenticateWithDb( DB db ) throws KettleException {
    // noop
  }

  @Override
  protected MongoDeleteCollectionWrapper wrap( DBCollection collection ) {
    return KerberosInvocationHandler.wrap( MongoDeleteCollectionWrapper.class, authContext,
        new KerberosMongoDeleteCollectionWrapper( collection, authContext ) );
  }

  public AuthContext getAuthContext() {
    return authContext;
  }
}
