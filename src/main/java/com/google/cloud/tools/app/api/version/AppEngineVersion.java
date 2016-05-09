/*
 * Copyright 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.cloud.tools.app.api.version;

import com.google.cloud.tools.app.api.AppEngineException;

/**
 * This set of commands can be used to manage existing App Engine versions.
 */
public interface AppEngineVersion {

  /**
   * Start serving a specific version of an App Engine Application.
   */
  void start(VersionSelectionConfiguration configuration) throws AppEngineException;

  /**
   * Stop serving a specific version of an App Engine application.
   */
  void stop(VersionSelectionConfiguration configuration) throws AppEngineException;

  /**
   * Delete a specific version of an App Engine application.
   */
  void delete(VersionSelectionConfiguration configuration) throws AppEngineException;

  /**
   * List your existing deployed versions.
   */
  void list(ListConfiguration configuration) throws AppEngineException;

}
