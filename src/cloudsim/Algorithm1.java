/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cloudsim;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;
import org.cloudbus.cloudsim.Cloudlet;
import org.cloudbus.cloudsim.CloudletSchedulerTimeShared;
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
public class Algorithm1 {

    private static List<Cloudlet> task_list;
    private static List<Vm> vmlist;
    public static List<Cloudlet> resultList;

    public static long total_size = 600000;
    public static int task_nums = 0;

    private static long[] job_size;

    private static void divideJobs() {//user defined method
        job_size = new long[task_nums];//we have took task_num in array
        long buf_total = total_size;//storing total size in buf_total
        for (int i = 0; i < task_nums - 1; i++) {
            job_size[i] = ThreadLocalRandom.current().nextLong(buf_total);
            buf_total -= job_size[i];
        }
        job_size[job_size.length-1] = buf_total;
    }

    public static void StartAlgo1() {
        Log.printLine("Starting CloudSimExample1...");
        divideJobs();
        try {
            int num_user = 1; // number of cloud users
            Calendar calendar = Calendar.getInstance();
            boolean trace_flag = false; // trace events

            CloudSim.init(num_user, calendar, trace_flag);
            Datacenter datacenter0 = createDatacenter("Datacenter_0");
            DatacenterBroker broker = createBroker();
            int brokerId = broker.getId();
            vmlist = new ArrayList<Vm>();
            // VM description
            int vmid = 0;
            int mips = ConfigParams.vm_mips;
            long size = ConfigParams.vm_size; // image size (MB)
            int ram = ConfigParams.vm_ram; // vm memory (MB)
            long bw = ConfigParams.vm_bw;
            int pesNumber = ConfigParams.vm_pesNumber; // number of cpus
            String vmm = ConfigParams.vm_name; // VMM name

            // create VM
            Vm vm = new Vm(vmid, brokerId, mips, pesNumber, ram, bw, size, vmm, new CloudletSchedulerTimeShared());
            vmlist.add(vm);
            broker.submitVmList(vmlist);

            task_list = createCloudlet(brokerId);
            broker.submitCloudletList(task_list);
            CloudSim.startSimulation();
            resultList = broker.getCloudletReceivedList();
            CloudSim.stopSimulation();
        } catch (Exception e) {
            e.printStackTrace();
            Log.printLine("Unwanted errors happen");
        }
    }

    private static List<Cloudlet> createCloudlet(int userId) {
        // Creates a container to store Cloudlets
        LinkedList<Cloudlet> list = new LinkedList<Cloudlet>();
        //generate random length
        Cloudlet[] cloudlet = new Cloudlet[task_nums];
        for (int i = 0; i < task_nums; i++) {
            long length = job_size[i];
            long fileSize = 300;
            long outputSize = 300;
            int pesNumber = 1;
            UtilizationModel utilizationModel = new UtilizationModelFull();
            cloudlet[i] = new Cloudlet(i, length, pesNumber, fileSize, outputSize, utilizationModel, utilizationModel, utilizationModel);
            // setting the owner of these Cloudlets
            cloudlet[i].setUserId(userId);
            list.add(cloudlet[i]);
        }

        return list;
    }

    private static Datacenter createDatacenter(String name) {

        List<Host> hostList = new ArrayList<Host>();
        // A Machine contains one or more PEs or CPUs/Cores.
        // it will have only one core.
        List<Pe> peList = new ArrayList<Pe>();
        int mips = 1000;

        peList.add(new Pe(0, new PeProvisionerSimple(mips)));

        int hostId = 0;
        int ram = 2048; // host memory (MB)
        long storage = 1000000; // host storage
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
        double costPerBw = ConfigParams.costPerBw;
        LinkedList<Storage> storageList = new LinkedList<Storage>(); // we are not adding SAN
        // devices by now
        DatacenterCharacteristics characteristics = new DatacenterCharacteristics(
                arch, os, vmm, hostList, time_zone, cost, costPerMem,
                costPerStorage, costPerBw);

        Datacenter datacenter = null;
        try {
            datacenter = new Datacenter(name, characteristics, new VmAllocationPolicySimple(hostList), storageList, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return datacenter;
    }

    private static DatacenterBroker createBroker() {
        DatacenterBroker broker = null;
        try {
            broker = new DatacenterBroker("Broker");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return broker;
    }
}
