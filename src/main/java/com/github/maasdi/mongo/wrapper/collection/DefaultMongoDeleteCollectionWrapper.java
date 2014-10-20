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
package com.github.maasdi.mongo.wrapper.collection;

import com.github.maasdi.mongo.wrapper.cursor.DefaultCursorWrapper;
import com.github.maasdi.mongo.wrapper.cursor.MongoCursorWrapper;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.WriteResult;
import org.pentaho.di.core.exception.KettleException;

/**
 * DefaultMongoDeleteCollectionWrapper.java
 *
 * @author maasdianto
 */
public class DefaultMongoDeleteCollectionWrapper implements MongoDeleteCollectionWrapper {

    private final DBCollection collection;

    public DefaultMongoDeleteCollectionWrapper(DBCollection collection) {
        this.collection = collection;
    }

    @Override
    public void drop() throws KettleException {
        collection.drop();
    }

    @Override
    public WriteResult drop(DBObject dropQuery) throws KettleException {
        return collection.remove(dropQuery);
    }

    protected MongoCursorWrapper wrap(DBCursor cursor) {
        return new DefaultCursorWrapper(cursor);
    }
}
