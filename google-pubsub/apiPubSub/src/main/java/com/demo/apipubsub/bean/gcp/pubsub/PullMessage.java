package com.demo.apipubsub.bean.gcp.pubsub;

public class PullMessage {
    private int maxMessages;
    private boolean returnImmediately;

    public int getMaxMessages() {
        return maxMessages;
    }

    public void setMaxMessages(int maxMessages) {
        this.maxMessages = maxMessages;
    }

    public boolean isReturnImmediately() {
        return returnImmediately;
    }

    public void setReturnImmediately(boolean returnImmediately) {
        this.returnImmediately = returnImmediately;
    }

    @Override
    public String toString() {
        return "PullRequestBean [maxMessages=" + maxMessages + ", returnImmediately=" + returnImmediately + "]";
    }


}
