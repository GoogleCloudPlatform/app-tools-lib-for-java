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

package com.google.cloud.tools.managedcloudsdk;

import com.google.cloud.tools.managedcloudsdk.process.ByteHandler;

/** {@link ByteHandler} that redirects to {@link MessageListener}. */
public class MessageListenerForwardingHandler implements ByteHandler {

  private final MessageListener messageListener;

  public MessageListenerForwardingHandler(MessageListener messageListener) {
    this.messageListener = messageListener;
  }

  @Override
  public void bytes(byte[] bytes, int length) {
    messageListener.message(new String(bytes, 0, length));
  }

  @Override
  public String getResult() {
    return null;
  }
}
