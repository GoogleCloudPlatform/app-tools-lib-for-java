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

package com.google.cloud.tools.appengine.v2.internal.cloudsdk;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.google.cloud.tools.appengine.v2.AppEngineRequest;
import com.google.cloud.tools.appengine.v2.OutputHandler;
import com.google.cloud.tools.appengine.v2.internal.process.CliProcessManagerProvider;
import com.google.cloud.tools.appengine.v2.internal.process.io.StringResultConverter;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;
import java.util.concurrent.Future;

@RunWith(MockitoJUnitRunner.class)
public class CloudSdkRequestTest {

  @Rule
  public ExpectedException exception = ExpectedException.none();

  @Mock
  private CloudSdkProcessFactory processFactory;
  @Mock
  private CliProcessManagerProvider<String> processManagerProvider;
  @Mock
  private StringResultConverter<String> resultConverter;
  @Mock
  private OutputHandler outputHandler;
  @Mock
  private Process process;
  @Mock
  private Future<String> manager;

  @Before
  public void configureMocks() throws IOException {
    when(processFactory.newProcess()).thenReturn(process);
    when(processManagerProvider.manage(process, resultConverter, outputHandler))
        .thenReturn(manager);
  }

  @Test
  public void testExecute_success() throws IOException {
    AppEngineRequest<String> request = new CloudSdkRequest<String>(processFactory,
        processManagerProvider, resultConverter);
    request.outputHandler(outputHandler);
    request.execute();
    verify(processFactory).newProcess();
    verify(processManagerProvider).manage(process, resultConverter, outputHandler);
  }

  @Test
  public void testExecute_configureAfterExecute() {
    AppEngineRequest<String> request = new CloudSdkRequest<String>(processFactory,
        processManagerProvider, resultConverter);
    request.execute();

    exception.expect(IllegalStateException.class);
    exception.expectMessage("Already executed");
    request.outputHandler(outputHandler);
  }
}