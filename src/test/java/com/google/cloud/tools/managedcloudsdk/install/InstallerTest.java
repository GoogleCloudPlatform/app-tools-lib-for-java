/*
 * Copyright 2017 Google Inc.
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

package com.google.cloud.tools.managedcloudsdk.install;

import com.google.cloud.tools.managedcloudsdk.ConsoleListener;
import com.google.cloud.tools.managedcloudsdk.ProgressListener;
import com.google.cloud.tools.managedcloudsdk.command.CommandRunner;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

/** Tests for {@link Installer} */
public class InstallerTest {

  @Mock private InstallScriptProvider mockInstallScriptProvider;
  @Mock private CommandRunner mockCommandRunner;
  @Mock private ProgressListener mockProgressListener;
  @Mock private ConsoleListener mockConsoleListener;

  @Rule public TemporaryFolder tmp = new TemporaryFolder();

  private Path fakeWorkingDirectory;
  private List<String> fakeCommand = Arrays.asList("scriptexec", "test-install.script");

  @Before
  public void setUp() throws IOException, ExecutionException, InterruptedException {
    MockitoAnnotations.initMocks(this);

    fakeWorkingDirectory = tmp.getRoot().toPath();
    Mockito.when(mockInstallScriptProvider.getScriptCommandLine()).thenReturn(fakeCommand);
  }

  @Test
  public void testCall() throws Exception {
    new Installer<>(
            fakeWorkingDirectory,
            mockInstallScriptProvider,
            false,
            mockProgressListener,
            mockConsoleListener,
            mockCommandRunner)
        .install();

    Mockito.verify(mockCommandRunner)
        .run(expectedCommand(false), fakeWorkingDirectory, null, mockConsoleListener);
    Mockito.verifyNoMoreInteractions(mockCommandRunner);

    Mockito.verify(mockProgressListener).update("Installing Cloud SDK");
    Mockito.verify(mockProgressListener).update(250);
    Mockito.verify(mockProgressListener).update(300);
    Mockito.verifyNoMoreInteractions(mockProgressListener);
  }

  @Test
  public void testCall_withUsageReporting() throws Exception {
    new Installer<>(
            tmp.getRoot().toPath(),
            mockInstallScriptProvider,
            true,
            mockProgressListener,
            mockConsoleListener,
            mockCommandRunner)
        .install();

    Mockito.verify(mockCommandRunner)
        .run(expectedCommand(true), fakeWorkingDirectory, null, mockConsoleListener);
    Mockito.verifyNoMoreInteractions(mockCommandRunner);
  }

  private List<String> expectedCommand(boolean usageReporting) {
    List<String> command = new ArrayList<>(fakeCommand);
    command.add("--path-update=false");
    command.add("--command-completion=false");
    command.add("--quiet");
    command.add("--usage-reporting=" + usageReporting);

    return command;
  }
}
