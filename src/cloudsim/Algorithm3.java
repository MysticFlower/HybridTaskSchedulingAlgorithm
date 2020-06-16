/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cloudsim;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;
import org.cloudbus.cloudsim.Cloudlet;
import org.cloudbus.cloudsim.CloudletSchedulerSpaceShared;
import org.cloudbus.cloudsim.Datacenter;
import org.cloudbus.cloudsim.DatacenterBroker;
import org.cloudbus.cloudsim.DatacenterCharacteristics;
import org.cloudbus.cloudsim.Host;
import org.cloudbus.cloudsim.Log;
import org.cloudbus.cloudsim.Pe;
import org.cloudbus.cloudsim.Storage;
import org.cloudbus.cloudsim.UtilizationModel;
import org.cloudbus.cloudsim.UtilizationModelFull;
import org.cloudbus.cloudsim.Vm;
import org.cloudbus.cloudsim.VmAllocationPolicySimple;
import org.cloudbus.cloudsim.VmSchedulerTimeShared;
import org.cloudbus.cloudsim.core.CloudSim;
import org.cloudbus.cloudsim.provisioners.BwProvisionerSimple;
import org.cloudbus.cloudsim.provisioners.PeProvisionerSimple;
import org.cloudbus.cloudsim.provisioners.RamProvisionerSimple;

/**
 *
 * @author shabeena
 */
public class Algorithm3 {

    private static List<Cloudlet> cloudletList;
    private static List<Vm> vmList;
    private static Datacenter[] datacenter;
    private static double[][] arriveMatrix;
    private static double[][] execMatrix;

    public static int datacenter_num = 0;
    public static int jobs_num = 0;
    public static double wait_limit = 0;
    
    public static List<Cloudlet> resultList;
    
    public static double min_time = 0;
    public static double max_time = 0;

     public static long total_size = 60000;
    private static long[] job_size;

    private static void divideJobs() {
        job_size = new long[jobs_num];
        long buf_total = total_size;
        for (int i = 0; i < jobs_num - 1; i++) {
            job_size[i] = ThreadLocalRandom.current().nextLong(buf_total);
            buf_total -= job_size[i];
        }
        job_size[job_size.length-1] = buf_total;
    }
    private static List<Vm> createVM(int userId, int vms) {
        LinkedList<Vm> list = new LinkedList<Vm>();
        long size = ConfigParams.vm_size; //image size (MB)
        int ram = ConfigParams.vm_ram; //vm memory (MB)
        int mips = ConfigParams.vm_mips;
        long bw = ConfigParams.vm_bw;
        int pesNumber = ConfigParams.vm_pesNumber; //number of cpus
        String vmm = ConfigParams.vm_name; //VMM name

        //create VMs
        Vm[] vm = new Vm[vms];

        for (int i = 0; i < vms; i++) {
            vm[i] = new Vm(datacenter[i].getId(), userId, mips, pesNumber, ram, bw, size, vmm, new CloudletSchedulerSpaceShared());
            list.add(vm[i]);
        }

        return list;
    }

    private static List<Cloudlet> createCloudlet(int userId, int cloudlets, int idShift) {
        // Creates a container to store Cloudlets
        LinkedList<Cloudlet> list = new LinkedList<Cloudlet>();

        long fileSize = 300;
        long outputSize = 300;
        int pesNumber = 1;
        UtilizationModel utilizationModel = new UtilizationModelFull();

        Cloudlet[] cloudlet = new Cloudlet[cloudlets];

        for (int i = 0; i < cloudlets; i++) {
            Random rand = new Random();
            int dcId = (int) (Math.random() * datacenter_num);
            long length = (long) (job_size[i] * (arriveMatrix[i][dcId] + execMatrix[i][dcId]));
            cloudlet[i] = new Cloudlet(idShift + i, length, pesNumber, fileSize, outputSize, utilizationModel, utilizationModel, utilizationModel);
            cloudlet[i].setUserId(userId);
            cloudlet[i].setVmId(dcId + 2);
            list.add(cloudlet[i]);
        }
        return list;
    }

    private static void GenerateMatrices() {
        arriveMatrix = new double[jobs_num][datacenter_num];
        execMatrix = new double[jobs_num][datacenter_num];
        try {
            initCostMatrix();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void initCostMatrix() throws IOException {
        System.out.println("Initializing new Matrices...");
        for (int i = 0; i < jobs_num; i++) {
            for (int j = 0; j < datacenter_num; j++) {
                arriveMatrix[i][j] = Math.random() * 600 + 20;
                execMatrix[i][j] = Math.random() * 500 + 10;
            }
        }
    }

    public static void StartAlgo3() {
        Log.printLine("Starting Job Scheduler...");
        divideJobs();
        GenerateMatrices();

        try {
            int num_user = 1;  
            Calendar calendar = Calendar.getInstance();
            boolean trace_flag = false;  // mean trace events

            CloudSim.init(num_user, calendar, trace_flag);

            datacenter = new Datacenter[datacenter_num];
            for (int i = 0; i < datacenter_num; i++) {
                datacenter[i] = createDatacenter("Datacenter_" + i);
            }

            DatacenterBroker broker = createBroker("Broker_0");
            int brokerId = broker.getId();
            vmList = createVM(brokerId, datacenter_num);
            cloudletList = createCloudlet(brokerId, jobs_num, 0);

            broker.submitVmList(vmList);
            broker.submitCloudletList(cloudletList);
            CloudSim.startSimulation();

            resultList = broker.getCloudletReceivedList();
            CloudSim.stopSimulation();
            Log.printLine(Algorithm2.class.getName() + " finished!");
        } catch (Exception e) {
            e.printStackTrace();
            Log.printLine("The simulation has been terminated due to an unexpected error");
        }
    }

    private static DatacenterBroker createBroker(String name) {
        DatacenterBroker broker = null;
        try {
            broker = new DatacenterBroker(name);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return broker;
    }

    public static Datacenter createDatacenter(String name) {
        List<Host> hostList = new ArrayList<Host>();
        List<Pe> peList = new ArrayList<Pe>();

        int mips = 1000;

        peList.add(new Pe(0, new PeProvisionerSimple(mips)));

        int hostId = 0;
        int ram = 2048; //host memory (MB)
        long storage = 1000000; //host storage
        int bw = 10000;

        hostList.add(
                new Host(
                        hostId,
                        new RamProvisionerSimple(ram),
                        new BwProvisionerSimple(bw),
                        storage,
                        peList,
                        new VmSchedulerTimeShared(peList)
                )
        ); 
        String arch = ConfigParams.arch;      // system architecture
        String os = ConfigParams.os;        // operating system
        String vmm = ConfigParams.host_name;
        double time_zone = ConfigParams.time_zone;     // time zone this resource located
        double cost = ConfigParams.cost;              // the cost of using processing in this resource
        double costPerMem = ConfigParams.costPerMem;       // the cost of using memory in this resource
        double costPerStorage = ConfigParams.costPerStorage;    // the cost of using storage in this resource
        double costPerBw = ConfigParams.costPerBw;           // the cost of using bw in this resource
        LinkedList<Storage> storageList = new LinkedList<Storage>();    //we are not adding SAN devices by now

        DatacenterCharacteristics characteristics = new DatacenterCharacteristics(
                arch, os, vmm, hostList, time_zone, cost, costPerMem, costPerStorage, costPerBw);

        // 6. Finally, we need to create a PowerDatacenter object.
        Datacenter datacenter = null;
        try {
            datacenter = new Datacenter(name, characteristics, new VmAllocationPolicySimple(hostList), storageList, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return datacenter;
    }

    public static void calcMaxEstimatedTime() {
        max_time = 0;
        double[] dcWorkingTime = new double[datacenter_num];

        for (int i = 0; i < jobs_num; i++) {
            int dcId = resultList.get(i).getVmId() % datacenter_num;
            if (dcWorkingTime[dcId] != 0) {
                --dcWorkingTime[dcId];
            }
            dcWorkingTime[dcId] += execMatrix[i][dcId] + arriveMatrix[i][dcId];
            max_time = Math.max(max_time, dcWorkingTime[dcId]);
        }
    }
    public static void calcMinEstimatedTime() {
        min_time = Double.MAX_VALUE;
        double[] dcWorkingTime = new double[datacenter_num];

        for (int i = 0; i < jobs_num; i++) {
            int dcId = resultList.get(i).getVmId() % datacenter_num;
            if (dcWorkingTime[dcId] != 0) {
                --dcWorkingTime[dcId];
            }
            dcWorkingTime[dcId] += execMatrix[i][dcId] + arriveMatrix[i][dcId];
            min_time = Math.min(min_time, dcWorkingTime[dcId]);
        }
    }
}
