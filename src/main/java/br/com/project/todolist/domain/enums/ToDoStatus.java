package br.com.project.todolist.domain.enums;

public enum ToDoStatus {

    PENDING,
    FINISHED,
    CANCELED;

    public static ToDoStatus toEnum(Integer value) {
        for (ToDoStatus status : ToDoStatus.values()) {
            if (value.equals(status.ordinal())) {
                return status;
            }
        }
        throw new IllegalArgumentException("Id inválido: " + value);
    }
}
