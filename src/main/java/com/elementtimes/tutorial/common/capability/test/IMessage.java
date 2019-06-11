package com.elementtimes.tutorial.common.capability.test;

public interface IMessage {

    String getMessage();

    void setMessage(String message);

    class Impl implements IMessage {

        private String message = "当你看到这条消息时，说明测试 Capability 注册成功，并能正常使用";

        @Override
        public String getMessage() {
            return message;
        }

        @Override
        public void setMessage(String message) {
            this.message = message;
        }
    }
}
