/*
 * Copyright (c) 2017, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 *
 */

package org.wso2.broker.amqp.codec.frames;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

/**
 * AMQP General Frame.
 */
public abstract class GeneralFrame {
    private final byte type;
    private final int channel;

    public GeneralFrame(byte type, int channel) {
        this.type = type;
        this.channel = channel;
    }

    public abstract long getPayloadSize();

    public abstract void writePayload(ByteBuf buf);

    /**
     * Getter for channel.
     * @return channel
     */
    public int getChannel() {
        return channel;
    }

    public ByteBuf write(ByteBufAllocator out) {
        long payloadSize = getPayloadSize();
        long totalSize = payloadSize + 1 + 2 + 4;
        ByteBuf buf = out.buffer((int) totalSize);

        buf.writeByte(type);
        buf.writeShort(channel);
        buf.writeInt((int) payloadSize);

        writePayload(buf);

        return buf;
    }
}
