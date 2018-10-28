package jcarklin.co.za.bakingrecipes.repository.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import static jcarklin.co.za.bakingrecipes.repository.model.Resource.Status.ERROR;
import static jcarklin.co.za.bakingrecipes.repository.model.Resource.Status.LOADING;
import static jcarklin.co.za.bakingrecipes.repository.model.Resource.Status.SUCCESS;

public class Resource<T> {

        public enum Status {
            SUCCESS, ERROR, LOADING
        }

        private final Status status;
        private final T data;
        private final Exception exception;

        private Resource(@NonNull Status status, @Nullable T data, @Nullable Exception exception) {
            this.status = status;
            this.data = data;
            this.exception = exception;
        }

        public Status getStatus() {
            return status;
        }

        public T getData() {
            return data;
        }

        public Exception getException() {
            return exception;
        }

        public static <T> Resource<T> success(@NonNull T data) {
            return new Resource<>(SUCCESS, data, null);
        }

        public static <T> Resource<T> error(Exception exception, @Nullable T data) {
            return new Resource<>(ERROR, data, exception);
        }

        public static <T> Resource<T> loading(@Nullable T data) {
            return new Resource<>(LOADING, data, null);
        }
}
