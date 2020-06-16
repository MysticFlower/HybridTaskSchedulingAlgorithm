/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cloudsim;

/**
 *
 * @author shabeena
 */
public class ConfigParams {

    public static long vm_size = 10000; //image size (MB)
    public static int vm_ram = 512; //vm memory (MB)
    public static int vm_mips = 250;
    public static long vm_bw = 1000;
    public static int vm_pesNumber = 1; //number of cpus
    public static String vm_name = "Xen"; //VMM name

    public static String arch = "x86";      // system architecture
    public static String os = "Linux";          // operating system
    public static String host_name = "Xen";
    public static double time_zone = 10.0;         // time zone this resource located
    public static double cost = 3.0;              // the cost of using processing in this resource
    public static double costPerMem = 0.05;        // the cost of using memory in this resource
    public static double costPerStorage = 0.1;    // the cost of using storage in this resource
    public static double costPerBw = 0.1;            // the cost of using bw in this resource
}
