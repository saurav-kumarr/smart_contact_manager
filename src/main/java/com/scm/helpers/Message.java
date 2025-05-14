package com.scm.helpers;

public class Message {
    private String content;
    private MessageType type = MessageType.blue;

    // No-Args Constructor
    public Message() {}

    // All-Args Constructor
    public Message(String content, MessageType type) {
        this.content = content;
        this.type = type;
    }

    // Getters
    public String getContent() {
        return content;
    }

    public MessageType getType() {
        return type;
    }

    // Setters
    public void setContent(String content) {
        this.content = content;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    // Builder Class
    public static class MessageBuilder {
        private String content;
        private MessageType type = MessageType.blue; // Default value

        public MessageBuilder setContent(String content) {
            this.content = content;
            return this;
        }

        public MessageBuilder setType(MessageType type) {
            this.type = type;
            return this;
        }

        public Message build() {
            return new Message(content, type);
        }
    }

    // Builder Method
    public static MessageBuilder builder() {
        return new MessageBuilder();
    }
}


