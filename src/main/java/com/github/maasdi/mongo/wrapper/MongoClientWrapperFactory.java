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

import java.lang.reflect.Proxy;

import org.pentaho.di.core.Const;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.core.logging.LogChannelInterface;
import org.pentaho.di.core.variables.VariableSpace;

import com.github.maasdi.di.trans.steps.mongodb.MongoDbMeta;

public class MongoClientWrapperFactory {
  public static MongoClientWrapper createMongoClientWrapper( MongoDbMeta meta, VariableSpace vars, LogChannelInterface log )
    throws KettleException {
    if ( meta.getUseKerberosAuthentication() ) {
      KerberosMongoClientWrapper wrapper = new KerberosMongoClientWrapper( meta, vars, log );
      return (MongoClientWrapper) Proxy.newProxyInstance( wrapper.getClass().getClassLoader(),
          new Class<?>[] { MongoClientWrapper.class }, new KerberosInvocationHandler( wrapper.getAuthContext(), wrapper ) );
    } else if ( !Const.isEmpty( meta.getAuthenticationUser() ) || !Const.isEmpty( meta.getAuthenticationPassword() ) ) {
      return new UsernamePasswordMongoClientWrapper( meta, vars, log );
    } else {
      return new NoAuthMongoClientWrapper( meta, vars, log );
    }
  }
}
