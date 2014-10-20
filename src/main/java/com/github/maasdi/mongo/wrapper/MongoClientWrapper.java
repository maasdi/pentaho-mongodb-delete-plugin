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

import java.util.List;
import java.util.Set;

import org.pentaho.di.core.exception.KettleException;

import com.mongodb.DBObject;

import com.github.maasdi.mongo.wrapper.collection.MongoDeleteCollectionWrapper;
import com.github.maasdi.mongo.wrapper.field.MongoField;

public interface MongoClientWrapper {
  public Set<String> getCollectionsNames( String dB ) throws KettleException;

  public List<String> getIndexInfo( String dbName, String collection ) throws KettleException;

  public List<MongoField> discoverFields( String db, String collection, String query, String fields,
      boolean isPipeline, int docsToSample ) throws KettleException;

  /**
   * Retrieve all database names found in MongoDB as visible by the authenticated user.
   *
   * @throws KettleException
   */
  public List<String> getDatabaseNames() throws KettleException;

  /**
   * Get a list of all tagName : tagValue pairs that occur in the tag sets defined across the replica set.
   *
   * @return a list of tags that occur in the replica set configuration
   * @throws KettleException
   *           if a problem occurs
   */
  public List<String> getAllTags() throws KettleException;

  /**
   * Return a list of replica set members whos tags satisfy the supplied list of tag set. It is assumed that members
   * satisfy according to an OR relationship = i.e. a member satisfies if it satisfies at least one of the tag sets in
   * the supplied list.
   *
   * @param tagSets
   *          the list of tag sets to match against
   * @return a list of replica set members who's tags satisfy the supplied list of tag sets
   * @throws KettleException
   *           if a problem occurs
   */
  public List<String> getReplicaSetMembersThatSatisfyTagSets( List<DBObject> tagSets ) throws KettleException;

  /**
   * Return a list of custom "lastErrorModes" (if any) defined in the replica set configuration object on the server.
   * These can be used as the "w" setting for the write concern in addition to the standard "w" values of <number> or
   * "majority".
   *
   * @return a list of the names of any custom "lastErrorModes"
   * @throws KettleException
   *           if a problem occurs
   */
  public List<String> getLastErrorModes() throws KettleException;

  public MongoDeleteCollectionWrapper createCollection( String db, String name ) throws KettleException;

  public MongoDeleteCollectionWrapper getCollection( String db, String name ) throws KettleException;

  public void dispose() throws KettleException;
}
