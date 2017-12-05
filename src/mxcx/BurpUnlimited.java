/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mxcx;

import java.io.File;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import larry.lau.BurpLoader;

/**
 *
 * @author mxcx@fosec.vn
 * 
 */
public class BurpUnlimited {
    private static final String AGENTPATH_MAC_32 = "lib/libfaketime32.jnilib";
    private static final String AGENTPATH_MAC_64 = "lib/libfaketime64.jnilib";
    private static final String AGENTPATH_LINUX_32 = "lib/libfaketime32.so";
    private static final String AGENTPATH_LINUX_64 = "lib/libfaketime64.so";
    private static final String AGENTPATH_WINDOWS_32 = "lib/libfaketime32.dll";
    private static final String AGENTPATH_WINDOWS_64 = "lib/libfaketime64.dll";
    
    public static boolean checkFakeTime(){
        return System.currentTimeMillis() < 1506790800000L;
    }
    
    public static String getAgentpath(){
        if(BurpUnlimited.checkFakeTime()){
            return "";
        }
        String currentjarpath = ManagementFactory.getRuntimeMXBean().getClassPath();
        File file = new File(currentjarpath); 
        String currentjardir = file.getParent() + File.separator;
        String nativeagentpath = AGENTPATH_MAC_64;
        switch(OSInfo.getOs()+OSInfo.getArch()){
            case "MACX32":
                nativeagentpath = AGENTPATH_MAC_32;
                break;
            case "MACX64":
                nativeagentpath = AGENTPATH_MAC_64;
                break;
            case "UNIXX32":
                nativeagentpath = AGENTPATH_LINUX_32;
                break;
            case "UNIX64":
                nativeagentpath = AGENTPATH_LINUX_64;
                break;
            case "WINDOWSX32":
                nativeagentpath = AGENTPATH_WINDOWS_32;
                break;
            case "WINDOWSX64":
                nativeagentpath = AGENTPATH_WINDOWS_64;
                break;
            default:
                nativeagentpath = "UNKNOWN";
                JOptionPane.showMessageDialog(null, BurpLoader.z[5], "Cannot detect OS to attach faketime agent for activation!\nPlease change manually your date time to before Dec 2 2017\nYou can restore to true time after Burpsuite loaded!", 0);
                System.exit(-1);
        }
        Path path = Paths.get(currentjardir + nativeagentpath);
        if (Files.notExists(path)) {
            JOptionPane.showMessageDialog(null, "The native agent file " + currentjardir + nativeagentpath + " is not exists!", BurpLoader.z[5], 0);
            System.exit(-2);
        }
        return String.format("-agentpath:%s%s -XX:+UnlockDiagnosticVMOptions -XX:DisableIntrinsic=_currentTimeMillis -XX:CompileCommand=quiet -XX:CompileCommand=exclude,java/lang/System.currentTimeMillis ", currentjardir, nativeagentpath);
    }
    
    public static void setupValidationEnv(){
        RuntimeMXBean mxb = ManagementFactory.getRuntimeMXBean();
        List<String> arguments = mxb.getInputArguments(); // init args field
        boolean bfoundagent = false;
        boolean bfoundnativeagent = false;
        for (int i = 0; i < arguments.size(); i++) {
            if (arguments.get(i).toLowerCase().contains("-javaagent:")) {
                bfoundagent = true;
            } else if (arguments.get(i).toLowerCase().contains("-agentpath:")) {
                bfoundnativeagent = true;
            }
        }
        if (!bfoundagent) {  // restart app with add javaagent parameter
            System.out.println("BurpUnlimited will restart with javaagent parameter");
            StringBuilder cmd = new StringBuilder();
            if(System.getProperty("java.home").contains(" ")){
                cmd.append("java ");
            } else {
                cmd.append(System.getProperty("java.home")).append(File.separator).append("bin").append(File.separator).append("java ");    
            }
            for (String jvmArg : ManagementFactory.getRuntimeMXBean().getInputArguments()) {
                cmd.append(jvmArg).append(" ");
            }
            cmd.append("-javaagent:").append(ManagementFactory.getRuntimeMXBean().getClassPath()).append(" ");
            if(!bfoundnativeagent){
                cmd.append(BurpUnlimited.getAgentpath());   
            }
            cmd.append("-jar ").append(ManagementFactory.getRuntimeMXBean().getClassPath());
            System.out.println(cmd.toString());
            try {
                Runtime.getRuntime().exec(cmd.toString());
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Fail in restart app to add javaagent parameter!\n"+ex.getLocalizedMessage()+"\n"+cmd.toString(), BurpLoader.z[5], 0);
            }
            System.exit(-3);
        }
        try {
            StrictPA.setValue(StrictPA.getValue(mxb, "jvm"), "vmArgs", Collections.unmodifiableList(new ArrayList<Object>()));
        } catch (NoSuchFieldException | IllegalAccessException ex) {
            JOptionPane.showMessageDialog(null, "Fail in set JavaRuntime to bypass Antidebug!", BurpLoader.z[5], 0);
            System.exit(-4);
        }

        System.setProperty("faketime.absolute.ms", "1420045200000");
        System.out.println("Time before activation: " + new Date());
        if(!BurpUnlimited.checkFakeTime()){
            JOptionPane.showMessageDialog(null, "So, I tried some way to change time for activation. However, it has been seem not successul!\nPlease change manually your date time to before Dec 2 2017\nYou can restore to true time after Burpsuite loaded!", BurpLoader.z[5], 0);
            System.exit(-2);
        }
    }
    
    public static void uninstallFakeTime(){
        System.clearProperty("faketime.absolute.ms");
        System.out.println("Time after remove Faketime: " + new Date());
    }
}