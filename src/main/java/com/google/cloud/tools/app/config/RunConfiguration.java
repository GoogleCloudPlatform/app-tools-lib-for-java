/**
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
package com.google.cloud.tools.app.config;

import com.google.cloud.tools.app.action.RunAction;

import java.nio.file.Path;
import java.util.Collection;

/**
 * Configuration of {@link RunAction}.
 */
public interface RunConfiguration {
  // TODO(joaomartins): Only contains common, jvm, Python, VM and misc flags for now. Need to add
  // PHP, AppIdentity, Blobstore, etc.
  Collection<Path> getAppYamls();

  boolean isRunAsync();

  String getHost();

  Integer getPort();

  String getAdminHost();

  Integer getAdminPort();

  String getAuthDomain();

  String getStoragePath();

  String getLogLevel();

  Integer getMaxModuleInstances();

  boolean isUseMtimeFileWatcher();

  String getThreadsafeOverride();

  String getPythonStartupScript();

  String getPythonStartupArgs();

  Collection<String> getJvmFlags();

  String getCustomEntrypoint();

  String getRuntime();

  boolean isAllowSkippedFiles();

  Integer getApiPort();

  boolean isAutomaticRestart();

  String getDevAppserverLogLevel();

  boolean isSkipSdkUpdateCheck();

  String getDefaultGcsBucketName();
}
