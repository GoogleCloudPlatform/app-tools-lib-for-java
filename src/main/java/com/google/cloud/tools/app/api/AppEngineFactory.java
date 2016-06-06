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

package com.google.cloud.tools.app.api;

import com.google.cloud.tools.app.api.deploy.AppEngineDeployment;
import com.google.cloud.tools.app.api.deploy.AppEngineFlexibleStaging;
import com.google.cloud.tools.app.api.deploy.AppEngineStandardStaging;
import com.google.cloud.tools.app.api.devserver.AppEngineDevServer;
import com.google.cloud.tools.app.api.genconfig.GenConfigUtility;
import com.google.cloud.tools.app.api.instances.AppEngineInstances;
import com.google.cloud.tools.app.api.logs.AppEngineLogs;
import com.google.cloud.tools.app.api.services.AppEngineServices;
import com.google.cloud.tools.app.api.versions.AppEngineVersions;

/**
 * A factory interface for creating App Engine services.
 */
public interface AppEngineFactory {
  AppEngineDeployment deployment();

  AppEngineDevServer devServer();

  AppEngineFlexibleStaging flexibleStaging();

  GenConfigUtility genConfigUtility();

  AppEngineInstances instances();

  AppEngineLogs logs();

  AppEngineServices services();

  AppEngineStandardStaging standardStaging();

  AppEngineVersions versions();
}
