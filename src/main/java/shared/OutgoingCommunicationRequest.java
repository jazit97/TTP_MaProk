package shared;

import java.io.Serializable;
import java.sql.Timestamp;

public class OutgoingCommunicationRequest implements Serializable {
    //====provided by User
    private String username;
    private String targetInstitution;
    private String targetDepartment;
    private String recipantID;
    private String patientID;
    //====generated
    private String commID; // id generated for the comm-event
    private Timestamp timestamp;

    public OutgoingCommunicationRequest(){
        this.timestamp = new Timestamp(System.currentTimeMillis());
    }

    public boolean isComplete(){
        boolean complete = false;
        if(username != null && targetDepartment != null && targetInstitution != null && patientID != null){
            complete = true;
        }
        return complete;
    }

    public String getRecipantID() {
        return recipantID;
    }

    public void setRecipantID(String recipantID) {
        this.recipantID = recipantID;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTargetInstitution() {
        return targetInstitution;
    }

    public void setTargetInstitution(String targetInstitution) {
        this.targetInstitution = targetInstitution;
    }

    public String getTargetDepartment() {
        return targetDepartment;
    }

    public void setTargetDepartment(String targetDepartment) {
        this.targetDepartment = targetDepartment;
    }

    public String getPatientID() {
        return patientID;
    }

    public void setPatientID(String patientID) {
        this.patientID = patientID;
    }
}
