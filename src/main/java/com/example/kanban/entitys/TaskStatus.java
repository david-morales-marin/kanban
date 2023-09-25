package com.example.kanban.entitys;


public enum TaskStatus {
    TODO,
    INPROGRESS,
    BLOCKED,
    DONE;

    public boolean isValidStatus(TaskStatus newStatus) {
        switch (this) {
            case TODO:
                return newStatus == TaskStatus.INPROGRESS;
            case INPROGRESS:
                return newStatus == TaskStatus.DONE || newStatus == TaskStatus.BLOCKED;
            case BLOCKED:
                return newStatus == TaskStatus.INPROGRESS || newStatus == TaskStatus.DONE;
            //   case DONE:
            //     return newStatus == TaskStatus.BLOCKED; CREO QUE DONE NO SE ACTUALIZA A NADA
            default:
                return false;
        }
    }

}
