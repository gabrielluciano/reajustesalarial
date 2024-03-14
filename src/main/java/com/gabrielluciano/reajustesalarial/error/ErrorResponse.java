package com.gabrielluciano.reajustesalarial.error;

public class ErrorResponse {

    private int status;
    private String error;
    private String path;
    private String timestamp;

    public ErrorResponse(int status, String error, String path, String timestamp) {
        this.status = status;
        this.error = error;
        this.path = path;
        this.timestamp = timestamp;
    }

    public static Builder builder() {
        return new Builder();
    }

    public int getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    public String getPath() {
        return path;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public static class Builder {
        private int status;
        private String error;
        private String path;
        private String timestamp;

        public ErrorResponse build() {
            return new ErrorResponse(status, error, path, timestamp);
        }

        public Builder status(int status) {
            this.status = status;
            return this;
        }

        public Builder error(String error) {
            this.error = error;
            return this;
        }

        public Builder path(String path) {
            this.path = path;
            return this;
        }

        public Builder timestamp(String timestamp) {
            this.timestamp = timestamp;
            return this;
        }
    }
}
