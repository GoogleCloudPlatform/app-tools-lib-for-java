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
package com.google.cloud.tools.app;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.google.cloud.tools.app.action.StageAction;
import com.google.cloud.tools.app.config.DefaultStageConfiguration;
import com.google.cloud.tools.app.config.StageConfiguration;
import com.google.cloud.tools.app.executor.StageExecutor;
import com.google.common.collect.ImmutableList;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.nio.file.Paths;
import java.util.List;

/**
 * Unit tests for {@link StageAction}.
 */
@RunWith(MockitoJUnitRunner.class)
public class StageActionTest {

  @Mock
  StageExecutor stageExecutor;

  @Test
  public void testCheckFlags_allFlags() {

    StageConfiguration configuration = DefaultStageConfiguration
        .newBuilder(Paths.get("source"), Paths.get("destination"))
        .enableQuickstart(true)
        .disableUpdateCheck(true)
        .version("v1")
        .applicationId("project")
        .enableJarSplitting(true)
        .jarSplittingExcludes("suffix1,suffix2")
        .compileEncoding("UTF8")
        .deleteJsps(true)
        .enableJarClasses(true)
        .build();

    StageAction action = new StageAction(configuration, stageExecutor);

    List<String> expected = ImmutableList
        .of("source", "destination", "--enable_quickstart", "--disable_update_check", "--version",
            "v1", "-A", "project", "--enable_jar_splitting", "--jar_splitting_excludes",
            "suffix1,suffix2", "--compile_encoding", "UTF8", "--delete_jsps",
            "--enable_jar_classes");

    action.execute();
    verify(stageExecutor, times(1)).runStage(eq(expected));
  }

  @Test
  public void testCheckFlags_noFlags() {

    StageConfiguration configuration = DefaultStageConfiguration
        .newBuilder(Paths.get("source"), Paths.get("destination")).build();

    StageAction action = new StageAction(configuration, stageExecutor);

    List<String> expected = ImmutableList.of("source", "destination");

    action.execute();
    verify(stageExecutor, times(1)).runStage(eq(expected));
  }
}
