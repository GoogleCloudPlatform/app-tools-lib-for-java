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

package com.google.cloud.tools.app.impl.cloudsdk.args;

import com.google.common.base.Joiner;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Command Line argument helper.
 */
public class GcloudArgs {

  /**
   * @return [--name, value] or [] if value=null.
   */
  public static List<String> get(String name, String value) {
    if (!Strings.isNullOrEmpty(value)) {
      return Arrays.asList("--" + name, value);
    }
    return Collections.emptyList();
  }

  /**
   * @return [--name, value] or [] if value=null.
   */
  public static List<String> get(String name, Integer value) {
    if (value != null) {
      return Arrays.asList("--" + name, value.toString());
    }
    return Collections.emptyList();
  }

  /**
   * @return [--name] if value=true, [--no-name] if value=false, [] if value=null.
   */
  public static List<String> get(String name, Boolean value) {
    if (value != null) {
      if (value) {
        return Collections.singletonList("--" + name);
      }
      return Collections.singletonList("--no-" + name);
    }
    return Collections.emptyList();
  }

  /**
   * @return [--name, file.getAbsolutePath()] or [] if file=null.
   */
  public static List<String> get(String name, File file) {
    if (file != null && !Strings.isNullOrEmpty(file.getAbsolutePath())) {
      return Arrays.asList("--" + name, file.getAbsolutePath());
    }
    return Collections.emptyList();
  }

  /**
   * @return [key1=value1,key2=value2,...], [] if keyValueMapping=empty/null
   */
  public static List<String> keyValues(Map<?, ?> keyValueMapping) {
    List<String> result = Lists.newArrayList();
    if (keyValueMapping != null && keyValueMapping.size() > 0) {
      for (Map.Entry<?, ?> entry : keyValueMapping.entrySet()) {
        result.add(entry.getKey() + "=" + entry.getValue());
      }
      Joiner joiner = Joiner.on(",");
      return Collections.singletonList(joiner.join(result));
    }

    return Collections.emptyList();
  }
}
