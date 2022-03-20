package com.example.cleanphone.utils.processmanager;


import android.os.Build.VERSION;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import eu.chainfire.libsuperuser.Shell;


public class ProcessManager {
    private static final String APP_ID_PATTERN;
    private static final String TAG = "ProcessManager";

    public static class Process implements Parcelable {
        public static final Creator<Process> CREATOR = new Creator<Process>() {
            public Process createFromParcel(Parcel source) {
                return new Process(source);
            }

            public Process[] newArray(int size) {
                return new Process[size];
            }
        };
        final int cpu;
        final String name;
        final int niceness;
        final String pc;
        public final int pid;
        final String policy;
        final int ppid;
        final int priority;
        final int realTimePriority;
        final long rss;
        final int schedulingPolicy;
        final String state;
        final long systemTime;
        final int uid;
        final String user;
        final long userTime;
        final long vsize;
        final String wchan;

        private Process(String line) throws Exception {
            String[] fields = line.split("\\s+");
            user = fields[0];
            uid = android.os.Process.getUidForName(user);
            pid = Integer.parseInt(fields[1]);
            ppid = Integer.parseInt(fields[2]);
            vsize = (long) (Integer.parseInt(fields[3]) * 1024);
            rss = (long) (Integer.parseInt(fields[4]) * 1024);
            cpu = Integer.parseInt(fields[5]);
            priority = Integer.parseInt(fields[6]);
            niceness = Integer.parseInt(fields[7]);
            realTimePriority = Integer.parseInt(fields[8]);
            schedulingPolicy = Integer.parseInt(fields[9]);
            if (fields.length == 16) {
                policy = "";
                wchan = fields[10];
                pc = fields[11];
                state = fields[12];
                name = fields[13];
                userTime = (long) (Integer.parseInt(fields[14].split(":")[1].replace(",", "")) * 1000);
                systemTime = (long) (Integer.parseInt(fields[15].split(":")[1].replace(")", "")) * 1000);
                return;
            }
            policy = fields[10];
            wchan = fields[11];
            pc = fields[12];
            state = fields[13];
            name = fields[14];
            userTime = (long) (Integer.parseInt(fields[15].split(":")[1].replace(",", "")) * 1000);
            systemTime = (long) (Integer.parseInt(fields[16].split(":")[1].replace(")", "")) * 1000);
        }

        private Process(Parcel in) {
            user = in.readString();
            uid = in.readInt();
            pid = in.readInt();
            ppid = in.readInt();
            vsize = in.readLong();
            rss = in.readLong();
            cpu = in.readInt();
            priority = in.readInt();
            niceness = in.readInt();
            realTimePriority = in.readInt();
            schedulingPolicy = in.readInt();
            policy = in.readString();
            wchan = in.readString();
            pc = in.readString();
            state = in.readString();
            name = in.readString();
            userTime = in.readLong();
            systemTime = in.readLong();
        }

        public String getPackageName() {
            if (!user.matches(ProcessManager.APP_ID_PATTERN)) {
                return null;
            }
            if (name.contains(":")) {
                return name.split(":")[0];
            }
            return name;
        }


        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(user);
            dest.writeInt(uid);
            dest.writeInt(pid);
            dest.writeInt(ppid);
            dest.writeLong(vsize);
            dest.writeLong(rss);
            dest.writeInt(cpu);
            dest.writeInt(priority);
            dest.writeInt(niceness);
            dest.writeInt(realTimePriority);
            dest.writeInt(schedulingPolicy);
            dest.writeString(policy);
            dest.writeString(wchan);
            dest.writeString(pc);
            dest.writeString(state);
            dest.writeString(name);
            dest.writeLong(userTime);
            dest.writeLong(systemTime);
        }
    }

    static {
        if (VERSION.SDK_INT >= 17) {
            APP_ID_PATTERN = "u\\d+_a\\d+";
        } else {
            APP_ID_PATTERN = "app_\\d+";
        }
    }


    public static List<Process> getRunningApps() {
        List<Process> processes = new ArrayList<>();
        List<String> stdout = Shell.SH.run("toolbox ps -p -P -x -c");
        int myPid = android.os.Process.myPid();
        for (String line : stdout) {
            try {
                Process process = new Process(line);
                if (!(!process.user.matches(APP_ID_PATTERN) || process.ppid == myPid || process.name.equals("toolbox"))) {
                    processes.add(process);
                }
            } catch (Exception e) {
                Log.d(TAG, "Failed parsing line " + line);
            }
        }
        return processes;
    }
}
