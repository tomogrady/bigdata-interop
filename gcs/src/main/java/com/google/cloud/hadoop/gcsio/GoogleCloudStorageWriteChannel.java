/**
 * Copyright 2013 Google Inc. All Rights Reserved.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 *    
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.cloud.hadoop.gcsio;

import com.google.api.client.http.InputStreamContent;
import com.google.api.services.storage.Storage;
import com.google.api.services.storage.Storage.Objects.Insert;
import com.google.api.services.storage.model.StorageObject;
import com.google.cloud.hadoop.util.AbstractGoogleAsyncWriteChannel;
import com.google.cloud.hadoop.util.AsyncWriteChannelOptions;
import com.google.cloud.hadoop.util.ClientRequestHelper;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ExecutorService;

/**
 * Implements WritableByteChannel to provide write access to GCS.
 */
public class GoogleCloudStorageWriteChannel
    extends AbstractGoogleAsyncWriteChannel<Insert, StorageObject> {

  private final Storage gcs;
  private final String bucketName;
  private final String objectName;
  private final ObjectWriteConditions writeConditions;
  private final Map<String, String> metadata;

  /**
   * Constructs an instance of GoogleCloudStorageWriteChannel.
   *
   * @param threadPool thread pool to use for running the upload operation
   * @param gcs storage object instance
   * @param requestHelper a ClientRequestHelper to set extra headers
   * @param bucketName name of the bucket to create object in
   * @param objectName name of the object to create
   * @param objectMetadata metadata to apply to the newly created object
   * @throws IOException on IO error
   */
  public GoogleCloudStorageWriteChannel(
      ExecutorService threadPool, Storage gcs, ClientRequestHelper<StorageObject> requestHelper,
      String bucketName, String objectName,
      AsyncWriteChannelOptions options, ObjectWriteConditions writeConditions,
      Map<String, String> objectMetadata) {
    super(threadPool, options);
    this.setClientRequestHelper(requestHelper);
    this.gcs = gcs;
    this.bucketName = bucketName;
    this.objectName = objectName;
    this.writeConditions = writeConditions;
    this.metadata = objectMetadata;
  }

  @Override
  public Insert createRequest(InputStreamContent inputStream) throws IOException {
    // Create object with the given name and metadata.
    StorageObject object =
        new StorageObject()
            .setName(objectName)
            .setMetadata(metadata);

    Insert insert = gcs.objects().insert(bucketName, object, inputStream);
    writeConditions.apply(insert);
    return insert;
  }
}
