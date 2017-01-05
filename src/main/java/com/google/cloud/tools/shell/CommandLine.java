/* Copyright 2017 Google Inc.
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

package com.google.cloud.tools.shell;

import com.google.common.annotations.Beta;

import java.util.ArrayList;

/**
 * Utilities for working with command line arguments.
 */
@Beta
public class CommandLine {

  /**
   * Split a full command line into an array of individual arguments,
   * similar to the shlex function in Python according to POSIX rules. 
   * All input characters are preserved except for separating whitespace. 
   * This function tokenizes the input string, but does not attempt to parse it.
   * 
   * @param line the input line
   * @return a non-null but possibly empty array of arguments
   */
  public static String[] split(String line) {    
    char quote = '"';
    boolean quoted = false;
    
    ArrayList<String> result = new ArrayList<>();
    StringBuilder arg = null;
    for (char c : line.toCharArray()) {
      if (!Character.isWhitespace(c)) {
        if (arg == null) { // start of token
          arg = new StringBuilder();
        }
        if (!quoted && (c == '"' || c == '\'')) { // opening quote
          quoted = true;
          quote = c;
        } else if (quoted && c == quote) { // closing quote
          quoted = false;
        }
        arg.append(c);
      } else if (quoted) { // quoted whitespace
        arg.append(c);
      } else { // end of token
        if (arg != null) {
          result.add(arg.toString());
          arg = null;
        }      
      }
    }
    
    if (arg != null) { // final token
      result.add(arg.toString());
      arg = null;
    }
    
    return result.toArray(new String[0]);
  }

}
