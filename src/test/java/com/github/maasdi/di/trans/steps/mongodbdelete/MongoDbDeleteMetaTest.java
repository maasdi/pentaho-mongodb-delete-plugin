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
package com.github.maasdi.di.trans.steps.mongodbdelete;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.trans.steps.loadsave.LoadSaveTester;
import org.pentaho.di.trans.steps.loadsave.validator.FieldLoadSaveValidatorFactory;
import org.pentaho.di.trans.steps.loadsave.validator.ListLoadSaveValidator;
import org.pentaho.di.trans.steps.loadsave.validator.ObjectValidator;

import com.github.maasdi.di.trans.steps.mongodbdelete.MongoDbDeleteMeta.MongoField;

/**
 * MongoDbDeleteMetaTest.java
 *
 * @author maasdianto
 */
public class MongoDbDeleteMetaTest {

    @Test
    public void testRoundTrips() throws KettleException {

        List<String> commonFields =
                Arrays.asList("mongo_host", "mongo_port", "use_all_replica_members", "mongo_user", "mongo_password",
                "auth_kerberos", "mongo_db", "mongo_collection", "connect_timeout", "socket_timeout",
                "read_preference", "write_concern", "w_timeout", "journaled_writes", "write_retries", "write_retry_delay",
                "use_json_query");

        Map<String, String> getterMap = new HashMap<String, String>();
        getterMap.put("mongo_host", "getHostnames");
        getterMap.put("mongo_port", "getPort");
        getterMap.put("use_all_replica_members", "getUseAllReplicaSetMembers");
        getterMap.put("mongo_user", "getAuthenticationUser");
        getterMap.put("mongo_password", "getAuthenticationPassword");
        getterMap.put("auth_kerberos", "getUseKerberosAuthentication");
        getterMap.put("mongo_db", "getDbName");
        getterMap.put("mongo_collection", "getCollection");
        getterMap.put("journaled_writes", "getJournal");
        getterMap.put("write_retries", "getWriteRetries");
        getterMap.put("use_json_query", "isUseJsonQuery");
        getterMap.put("execute_each_incomming_row", "isExecuteForEachIncomingRow");
        getterMap.put("json_query", "getJsonQuery");

        Map<String, String> setterMap = new HashMap<String, String>();
        setterMap.put("mongo_host", "setHostnames");
        setterMap.put("mongo_port", "setPort");
        setterMap.put("use_all_replica_members", "setUseAllReplicaSetMembers");
        setterMap.put("mongo_user", "setAuthenticationUser");
        setterMap.put("mongo_password", "setAuthenticationPassword");
        setterMap.put("auth_kerberos", "setUseKerberosAuthentication");
        setterMap.put("mongo_db", "setDbName");
        setterMap.put("mongo_collection", "setCollection");
        setterMap.put("journaled_writes", "setJournal");
        setterMap.put("write_retries", "setWriteRetries");
        setterMap.put("use_json_query", "setUseJsonQuery");
        setterMap.put("execute_each_incomming_row", "setExecuteForEachIncomingRow");
        setterMap.put("json_query", "setJsonQuery");

        LoadSaveTester tester = new LoadSaveTester(MongoDbDeleteMeta.class, commonFields, getterMap, setterMap);

        FieldLoadSaveValidatorFactory validatorFactory = tester.getFieldLoadSaveValidatorFactory();

        validatorFactory.registerValidator(validatorFactory.getName(List.class, MongoField.class),
                new ListLoadSaveValidator<MongoField>(new ObjectValidator<MongoField>(validatorFactory, MongoField.class,
                Arrays.<String>asList("m_mongoDocPath", "m_comparator", "m_incomingField1", "m_incomingField2"))));

        tester.testXmlRoundTrip();
        tester.testRepoRoundTrip();
    }
}
