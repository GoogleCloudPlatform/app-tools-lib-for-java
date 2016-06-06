/**
 * Copyright 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package com.google.cloud.tools.app.impl.cloudsdk;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

import com.google.cloud.tools.app.api.AppEngineException;
import com.google.cloud.tools.app.api.deploy.AppEngineFlexibleStaging;
import com.google.cloud.tools.app.api.deploy.StageFlexibleConfiguration;
import com.google.cloud.tools.app.impl.cloudsdk.internal.util.FileUtil;
import com.google.common.base.Preconditions;
import com.google.common.collect.Sets;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.PosixFilePermission;
import java.util.Set;

/**
 * Cloud SDK based implementation of {@link AppEngineFlexibleStaging}.
 */
public class CloudSdkAppEngineFlexibleStaging implements AppEngineFlexibleStaging {

  /**
   * Stages a Java JAR/WAR Managed VMs application to be deployed.
   *
   * <p></p>Copies app.yaml, Dockerfile and the application artifact to the staging area.
   *
   * <p>If app.yaml or Dockerfile do not exist, gcloud cloud will create them.
   */
  @Override
  public void stageFlexible(StageFlexibleConfiguration config) throws AppEngineException {
    Preconditions.checkNotNull(config);
    Preconditions.checkNotNull(config.getStagingDirectory());
    Preconditions.checkNotNull(config.getArtifact());

    if (!config.getStagingDirectory().exists()) {
      throw new AppEngineException("Staging directory does not exist. Location: "
          + config.getStagingDirectory().toPath().toString());
    }
    if (!config.getStagingDirectory().isDirectory()) {
      throw new AppEngineException("Staging location is not a directory. Location: "
          + config.getStagingDirectory().toPath().toString());
    }

    try {

      // Copy docker context to staging
      if (config.getDockerDirectory() != null && config.getDockerDirectory().exists()) {
        FileUtil.copyDirectory(config.getDockerDirectory().toPath(),
            config.getStagingDirectory().toPath());
      }

      // Copy app.yaml to staging.
      if (config.getAppYaml() != null && config.getAppYaml().exists()) {
        Files.copy(config.getAppYaml().toPath(),
            config.getStagingDirectory().toPath()
                .resolve(config.getAppYaml().toPath().getFileName()),
            REPLACE_EXISTING);
      }

      // TODO : looks like this section should error on no artifacts found? and maybe the
      // TODO : earlier ones should warn?
      // Copy the JAR/WAR file to staging.
      if (config.getArtifact() != null && config.getArtifact().exists()) {
        Path destination = config.getStagingDirectory().toPath()
            .resolve(config.getArtifact().toPath().getFileName());
        Files.copy(config.getArtifact().toPath(), destination, REPLACE_EXISTING);

        // Update artifact permissions so docker can read it when deployed
        if (!System.getProperty("os.name").contains("Windows")) {
          Set<PosixFilePermission> permissions = Sets.newHashSet();
          permissions.add(PosixFilePermission.OWNER_READ);
          permissions.add(PosixFilePermission.OWNER_WRITE);
          permissions.add(PosixFilePermission.GROUP_READ);
          permissions.add(PosixFilePermission.OTHERS_READ);

          Files.setPosixFilePermissions(destination, permissions);
        }
      }
    } catch (IOException e) {
      throw new AppEngineException(e);
    }
  }
}
