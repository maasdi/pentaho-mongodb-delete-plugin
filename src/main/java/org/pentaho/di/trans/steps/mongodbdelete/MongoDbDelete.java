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
package org.pentaho.di.trans.steps.mongodbdelete;

import com.mongodb.*;
import com.mongodb.util.JSON;
import org.pentaho.di.core.Const;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.core.row.RowMeta;
import org.pentaho.di.core.row.RowMetaInterface;
import org.pentaho.di.i18n.BaseMessages;
import org.pentaho.di.trans.Trans;
import org.pentaho.di.trans.TransMeta;
import org.pentaho.di.trans.step.*;
import org.pentaho.mongo.MongoDbException;
import org.pentaho.mongo.wrapper.MongoWrapperUtil;

import java.net.UnknownHostException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Class MongoDbDelete, providing MongoDB delete functionality. User able to create criteria base on incoming fields.
 *
 * @author Maas Dianto (maas.dianto@gmail.com)
 */
public class MongoDbDelete extends BaseStep implements StepInterface {

    private static Class<?> PKG = MongoDbDelete.class;
    private MongoDbDeleteMeta meta;
    private MongoDbDeleteData data;
    protected int m_writeRetries = MongoDbDeleteMeta.RETRIES;
    protected int m_writeRetryDelay = MongoDbDeleteMeta.RETRY_DELAY;

    public MongoDbDelete(StepMeta stepMeta, StepDataInterface stepDataInterface, int copyNr, TransMeta transMeta, Trans trans) {
        super(stepMeta, stepDataInterface, copyNr, transMeta, trans);
    }

    @Override
    public boolean processRow(StepMetaInterface stepMetaInterface, StepDataInterface stepDataInterface) throws KettleException {
        Object[] row = getRow();

        if (meta.isUseJsonQuery()) {
            if (first) {
                first = false;

                if (getInputRowMeta() == null) {
                    data.outputRowMeta = new RowMeta();
                } else {
                    data.setOutputRowMeta(getInputRowMeta());
                }
                data.init(MongoDbDelete.this);

                DBObject query = getQueryFromJSON(meta.getJsonQuery(), row);
                commitDelete(query, row);
            } else if (meta.isExecuteForEachIncomingRow()) {

                if (row == null) {
                    disconnect();
                    setOutputDone();
                    return false;
                }

                if (!first) {
                    DBObject query = getQueryFromJSON(meta.getJsonQuery(), row);
                    commitDelete(query, row);
                }
            }

            if (row == null) {
                disconnect();
                setOutputDone();
                return false;
            }

            if (!isStopped()) {
                putRow(data.getOutputRowMeta(), row);
            }

            return true;
        } else {

            if (row == null) {
                disconnect();
                setOutputDone();
                return false;
            }

            if (first) {
                first = false;

                data.setOutputRowMeta(getInputRowMeta());
                // first check our incoming fields against our meta data for
                // fields to delete
                RowMetaInterface rmi = getInputRowMeta();
                // this fields we are going to use for mongo output
                List<MongoDbDeleteMeta.MongoField> mongoFields = meta.getMongoFields();
                checkInputFieldsMatch(rmi, mongoFields);

                data.setMongoFields(meta.getMongoFields());
                data.init(MongoDbDelete.this);
            }

            if (!isStopped()) {

                putRow(data.getOutputRowMeta(), row);

                DBObject query = MongoDbDeleteData.getQueryObject(data.m_userFields, getInputRowMeta(), row, MongoDbDelete.this);
                if (log.isDebug()) {
                    logDebug(BaseMessages.getString(PKG, "MongoDbDelete.Message.Debug.QueryForDelete", query));
                }
                // We have query delete
                if (query != null) {
                    commitDelete(query, row);
                }
            }

            return true;
        }
    }

    @Override
    public boolean init(StepMetaInterface stepMetaInterface, StepDataInterface stepDataInterface) {
        if (super.init(stepMetaInterface, stepDataInterface)) {
            meta = (MongoDbDeleteMeta) stepMetaInterface;
            data = (MongoDbDeleteData) stepDataInterface;

            if (!Const.isEmpty(meta.getWriteRetries())) {
                try {
                    m_writeRetries = Integer.parseInt(meta.getWriteRetries());
                } catch (NumberFormatException ex) {
                    m_writeRetries = MongoDbDeleteMeta.RETRIES;
                }
            }

            if (!Const.isEmpty(meta.getWriteRetryDelay())) {
                try {
                    m_writeRetryDelay = Integer.parseInt(meta.getWriteRetryDelay());
                } catch (NumberFormatException ex) {
                    m_writeRetryDelay = MongoDbDeleteMeta.RETRY_DELAY;
                }
            }

            String hostname = environmentSubstitute(meta.getHostnames());
            int port = Const.toInt(environmentSubstitute(meta.getPort()),
                    MongoDbDeleteData.MONGO_DEFAULT_PORT);
            String db = environmentSubstitute(meta.getDbName());
            String collection = environmentSubstitute(meta.getCollection());

            try {

                if (Const.isEmpty(db)) {
                    throw new Exception(BaseMessages.getString(PKG,
                            "MongoDbDelete.ErrorMessage.NoDBSpecified"));
                }

                if (Const.isEmpty(collection)) {
                    throw new Exception(BaseMessages.getString(PKG,
                            "MongoDbDelete.ErrorMessage.NoCollectionSpecified"));
                }

                if (!Const.isEmpty(meta.getAuthenticationUser())) {
                    String authInfo = (meta.getUseKerberosAuthentication() ? BaseMessages
                            .getString(PKG, "MongoDbDelete.Message.KerberosAuthentication",
                            environmentSubstitute(meta.getAuthenticationUser()))
                            : BaseMessages.getString(PKG,
                            "MongoDbDelete.Message.NormalAuthentication",
                            environmentSubstitute(meta.getAuthenticationUser())));

                    logBasic(authInfo);
                }

                data.setConnection(MongoWrapperUtil.createMongoClientWrapper(meta, this, log));
                if (Const.isEmpty(collection)) {
                    throw new KettleException(BaseMessages.getString(PKG, "MongoDbDelete.ErrorMessage.NoCollectionSpecified"));
                }
                data.createCollection(db, collection);
                data.setCollection(data.getConnection().getCollection(db, collection));

                return true;
            } catch (UnknownHostException ex) {
                logError(BaseMessages.getString(PKG, "MongoDbDelete.ErrorMessage.UnknownHost", hostname), ex);
                return false;
            } catch (Exception e) {
                logError(BaseMessages.getString(PKG,
                        "MongoDbDelete.ErrorMessage.ProblemConnecting", hostname, ""
                        + port), e);
                return false;
            }
        }

        return false;
    }

    @Override
    public void dispose(StepMetaInterface stepMetaInterface, StepDataInterface stepDataInterface) {
        if (data.cursor != null) {
            try {
                data.cursor.close();
            } catch (MongoDbException e) {
                log.logError(e.getMessage());
            }
        }
        if (data.clientWrapper != null) {
            try {
                data.clientWrapper.dispose();
            } catch (MongoDbException e) {
                log.logError(e.getMessage());
            }
        }

        super.dispose(stepMetaInterface, stepDataInterface);
    }

    protected void disconnect() {
        if (data != null) {
            try {
                data.getConnection().dispose();
            } catch (MongoDbException e) {
                log.logError(e.getMessage());
            }
        }
    }

    protected void commitDelete(DBObject deleteQuery, Object[] row) throws KettleException {
        int retrys = 0;
        MongoException lastEx = null;

        while (retrys <= m_writeRetries && !isStopped()) {
            WriteResult result = null;
            try {
                try {
                    logDetailed(BaseMessages.getString(PKG, "MongoDbDelete.Message.ExecutingQuery", deleteQuery));
                    result = data.getCollection().remove(deleteQuery);
                } catch ( MongoDbException e ) {
                    throw new MongoException(e.getMessage(), e);
                }
            } catch (MongoException me) {
                lastEx = me;
                retrys++;
                if (retrys <= m_writeRetries) {
                    logError(BaseMessages.getString(PKG, "MongoDbDelete.ErrorMessage.ErrorWritingToMongo",
                            me.toString()));
                    logBasic(BaseMessages.getString(PKG, "MongoDbDelete.Message.Retry", m_writeRetryDelay));
                    try {
                        Thread.sleep(m_writeRetryDelay * 1000);
                        // CHECKSTYLE:OFF
                    } catch (InterruptedException e) {
                        // CHECKSTYLE:ON
                    }
                }
            }
            if ( result != null ) {
                break;
            }
        }

        if ((retrys > m_writeRetries || isStopped()) && lastEx != null) {

            // Send this one to the error stream if doing error handling
            if (getStepMeta().isDoingErrorHandling()) {
                putError(getInputRowMeta(), row, 1, lastEx.getMessage(), "", "MongoDbDelete");
            } else {
                throw new KettleException(lastEx);
            }
        }
    }

    public DBObject getQueryFromJSON(String json, Object[] row) throws KettleException {
        DBObject query;
        String jsonQuery = environmentSubstitute(json);
        if (Const.isEmpty(jsonQuery)) {
            query = new BasicDBObject();
        } else {
            if (meta.isExecuteForEachIncomingRow() && row != null) {
                jsonQuery = fieldSubstitute(jsonQuery, getInputRowMeta(), row);
            }

            query = (DBObject) JSON.parse(jsonQuery);
        }
        return query;
    }

    final void checkInputFieldsMatch(RowMetaInterface rmi, List<MongoDbDeleteMeta.MongoField> mongoFields)
            throws KettleException {
        if (mongoFields == null || mongoFields.isEmpty()) {
            throw new KettleException(BaseMessages.getString(PKG, "MongoDbDeleteDialog.ErrorMessage.NoFieldPathsDefined"));
        }

        Set<String> expected = new HashSet<String>(mongoFields.size(), 1);
        Set<String> actual = new HashSet<String>(rmi.getFieldNames().length, 1);
        for (MongoDbDeleteMeta.MongoField field : mongoFields) {
            String field1 = environmentSubstitute(field.m_incomingField1);
            String field2 = environmentSubstitute(field.m_incomingField2);
            expected.add(field1);
            if (!Const.isEmpty(field2)) {
                expected.add(field2);
            }
        }
        for (int i = 0; i < rmi.size(); i++) {
            String metaFieldName = rmi.getValueMeta(i).getName();
            actual.add(metaFieldName);
        }

        // check that all expected fields is available in step input meta
        if (!actual.containsAll(expected)) {
            // in this case some fields willn't be found in input step meta
            expected.removeAll(actual);
            StringBuffer b = new StringBuffer();
            for (String name : expected) {
                b.append("'").append(name).append("', ");
            }
            throw new KettleException(BaseMessages.getString(PKG,
                    "MongoDbDelete.MongoField.Error.FieldsNotFoundInMetadata", b.toString()));
        }

        boolean found = actual.removeAll(expected);
        if (!found) {
            throw new KettleException(BaseMessages.getString(PKG, "MongoDbDelete.ErrorMessage.NotDeleteAnyFields"));
        }
    }
}
