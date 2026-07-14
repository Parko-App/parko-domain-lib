package com.parko.domain.lib.model;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
public class User {

    private UUID id;
    private String studentId;
    private String fullName;
    private Email email;
    private UserRole role;
    @Setter
    private String firebaseUid;
    @Setter
    private boolean active;

    public User (UUID id, String studentId, String fullName, Email email){

        this.id = id;

        if (studentId == null){
            throw new IllegalArgumentException("El legajo no puede estar vacío");
        }

        if (email == null){
            throw new IllegalArgumentException("El email no puede estar vacío");
        }

        if (!studentId.equals(email.getStudentId())){
            throw new IllegalArgumentException(
                    "El legajo no coincide con el legajo del email: " + studentId + " vs " + email.getStudentId()
            );
        }
        this.studentId = studentId;

        if (fullName == null){
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }
        this.fullName = fullName;

        this.email = email;

        this.active = true;
    }
}
