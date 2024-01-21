package br.com.project.todolist.domain.enums;

public enum ToDoStatus {
    IN_PROGRESS,
    PENDING,
    FINISHED,
    CANCELED;

    public static ToDoStatus toEnum(String value) {
        for (ToDoStatus status : ToDoStatus.values()) {
            if (value.equals(status.name())) {
                return status;
            }
        }
        throw new IllegalArgumentException(value);
    }
}
