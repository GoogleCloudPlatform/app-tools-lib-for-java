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

package com.google.cloud.tools.app.impl.cloudsdk.internal.args;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Unit tests for {@link GcloudArgs}
 */
public class GcloudArgsTest {

  @Test
  public void testGet_string() {
    assertEquals(GcloudArgs.get("name1", "value1"), Arrays.asList("--name1", "value1"));
    assertEquals(GcloudArgs.get("name2", "value2"), Arrays.asList("--name2", "value2"));
  }

  @Test
  public void testGet_boolean() {
    assertEquals(GcloudArgs.get("name1", true), Collections.singletonList("--name1"));
    assertEquals(GcloudArgs.get("name2", true), Collections.singletonList("--name2"));

    assertEquals(GcloudArgs.get("name1", false), Collections.singletonList("--no-name1"));
    assertEquals(GcloudArgs.get("name2", false), Collections.singletonList("--no-name2"));
  }

  @Test
  public void testGet_integer() {
    assertEquals(GcloudArgs.get("name1", 1), Arrays.asList("--name1", "1"));
    assertEquals(GcloudArgs.get("name2", 2), Arrays.asList("--name2", "2"));
  }

  @Rule
  public TemporaryFolder tmpDir = new TemporaryFolder();

  @Test
  public void testGet_file() throws IOException {
    File file1 = tmpDir.newFile("file1");
    File file2 = tmpDir.newFile("file2");
    assertEquals(GcloudArgs.get("name1", file1), Arrays.asList("--name1", file1.getAbsolutePath()));
    assertEquals(GcloudArgs.get("name2", file2), Arrays.asList("--name2", file2.getAbsolutePath()));
  }

  @Test
  public void testKeyValues() {
    Map<String, Double> versionToTrafficSplitMapping =
        new LinkedHashMap<>(); // Preserve the order for the assertion
    versionToTrafficSplitMapping.put("v1", 0.2);
    versionToTrafficSplitMapping.put("v2", 0.3);
    versionToTrafficSplitMapping.put("v3", 0.5);

    assertEquals(Collections.singletonList("v1=0.2,v2=0.3,v3=0.5"),
        GcloudArgs.keyValues(versionToTrafficSplitMapping));

    assertEquals(Collections.emptyList(), GcloudArgs.keyValues(null));
  }
}
