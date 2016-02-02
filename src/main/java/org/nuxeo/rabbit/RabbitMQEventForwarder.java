/*
 * (C) Copyright 2016 Nuxeo SA (http://nuxeo.com/) and others.
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
 *
 * Contributors:
 *         Vladimir Pasquier <vpasquier@nuxeo.com>
 */
package org.nuxeo.rabbit;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.nuxeo.ecm.core.api.DocumentModel;
import org.nuxeo.ecm.core.event.Event;
import org.nuxeo.ecm.core.event.EventBundle;
import org.nuxeo.ecm.core.event.PostCommitEventListener;
import org.nuxeo.ecm.core.event.impl.DocumentEventContext;
import org.nuxeo.runtime.api.Framework;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * @since 8.2
 */
public class RabbitMQEventForwarder implements PostCommitEventListener {

    public final static String QUEUE_NAME = "nuxeo";

    public static final String RABBITMQ_URL = "org.nuxeo.rabbitmq.location";

    protected Connection connection;

    protected Channel channel;

    protected void produceRabbitMQMessage(Event event) throws RabbitMQException, IOException, TimeoutException {
        DocumentEventContext context = (DocumentEventContext) event.getContext();
        DocumentModel doc = context.getSourceDocument();
        if (!doc.hasSchema("file")) {
            return;
        }
        if (connection == null) {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost(Framework.getProperty(RABBITMQ_URL, "localhost"));
            connection = factory.newConnection();
        }
        if (channel == null) {
            channel = connection.createChannel();
        }
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        channel.basicPublish("", QUEUE_NAME, null, message(event, doc).getBytes("UTF-8"));
        channel.close();
        connection.close();
    }

    private String message(Event event, DocumentModel doc) {
        StringBuilder message = new StringBuilder();
        // message.append(new GregorianCalendar(event.getTime()).getDateWithTime());
        message.append(" - ");
        message.append(event.getName());
        message.append(" - Doc: ");
        message.append(doc.getName());
        message.append(" with UUID ");
        message.append(doc.getId());
        return message.toString();
    }

    @Override
    public void handleEvent(EventBundle events) {
        for (Event event : events) {
            try {
                produceRabbitMQMessage(event);
            } catch (IOException | TimeoutException e) {
                throw new RabbitMQException(e);
            }
        }
    }
}
