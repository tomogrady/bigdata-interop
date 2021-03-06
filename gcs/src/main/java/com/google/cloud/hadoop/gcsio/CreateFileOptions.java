/**
 * Copyright 2014 Google Inc. All Rights Reserved.
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

import com.google.common.collect.ImmutableMap;

import java.util.Map;

/**
 * Options that can be specified when creating a file in the {@code GoogleCloudFileSystem}.
 */
public class CreateFileOptions {

  public static final Map<String, byte[]> EMPTY_ATTRIBUTES = ImmutableMap.of();
  public static final CreateFileOptions DEFAULT = new CreateFileOptions(true, EMPTY_ATTRIBUTES);

  private final boolean overwriteExisting;
  private final Map<String, byte[]> attributes;

  /**
   * Create a file with empty attributes and optionally overwriting any existing file.
   * @param overwriteExisting True to overwrite an existing file with the same name
   */
  public CreateFileOptions(boolean overwriteExisting) {
    this(overwriteExisting, EMPTY_ATTRIBUTES);
  }

  /**
   * Create a file with specified attributes and optionally overwriting an existing file.
   * @param overwriteExisting True to overwrite an existing file with the same name
   * @param attributes File attributes to apply to the file at creation
   */
  public CreateFileOptions(boolean overwriteExisting, Map<String, byte[]> attributes) {
    this.overwriteExisting = overwriteExisting;
    this.attributes = attributes;
  }

  /**
   * Get the value of overwriteExisting.
   */
  public boolean overwriteExisting() {
    return overwriteExisting;
  }

  /**
   * Extended attributes to set when creating a file.
   */
  public Map<String, byte[]> getAttributes() {
    return attributes;
  }
}
