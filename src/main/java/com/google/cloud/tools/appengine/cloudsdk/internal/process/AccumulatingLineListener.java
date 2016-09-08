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

package com.google.cloud.tools.appengine.cloudsdk.internal.process;

import com.google.cloud.tools.appengine.cloudsdk.process.ProcessOutputLineListener;

public class AccumulatingLineListener implements ProcessOutputLineListener {

  private StringBuilder output = new StringBuilder();
  
  @Override
  public void onOutputLine(String line) {
    output.append(line + "\n");
  }

  // todo: maybe this should be a standard part of ProcessRunner API instead?
  public String getOutput() {
    return output.toString(); 
  }

  public void clear() {
    output = new StringBuilder();
  }
  
}
