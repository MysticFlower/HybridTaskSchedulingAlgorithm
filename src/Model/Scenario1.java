/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author shabeena
 */
public class Scenario1 {
    private String task_id;
    private String datacenter_id;
    private String vm_id;
    private String start_time;
    private String end_time;
    private String estimate_time;
    private String status;
    
    public Scenario1(String task_id, String datacenter_id, String vm_id, String start_time, String end_time, String eastimate_time, String status){
        this.task_id = task_id;
        this.datacenter_id = datacenter_id;
        this.vm_id = vm_id;
        this.start_time = start_time;
        this.end_time = end_time;
        this.estimate_time = eastimate_time;
        this.status = status;
    }
    public String getTaskId(){
        return task_id;
    }
    public String getDataCenterId(){
        return datacenter_id;
    }
    public String getVmID(){
        return vm_id;
    }
    public String getStartTime(){
        return start_time;
    }
    public String getEndTime(){
        return end_time;
    }
    public String getEstimate(){
        return estimate_time;
    }
    public String getStatus(){
        return status;
    }
}
