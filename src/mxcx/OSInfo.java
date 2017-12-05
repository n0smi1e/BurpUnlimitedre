 package mxcx;
 
 import java.io.IOException;
 import java.util.Locale;
 
 public class OSInfo
 {
   private static String os = "OTHER";
   private static String arch = "X32";
   
   static {
     try {
       String osName = System.getProperty("os.name");
       if (osName == null) {
         throw new IOException("os.name not found");
       }
       osName = osName.toLowerCase(Locale.ENGLISH);
       if (osName.contains("windows")) {
         os = "WINDOWS";
       } else if ((osName.contains("linux")) || (osName.contains("mpe/ix")) || (osName.contains("freebsd")) || (osName.contains("irix")) || (osName.contains("digital unix")) || (osName.contains("unix")))
       {
 
 
 
 
         os = "UNIX";
       } else if (osName.contains("mac os")) {
         os = "MAC";
       } else {
         os = "OTHER";
       }
       
       if (System.getProperty("os.arch").contains("64")) {
         arch = "X64";
       }
     } catch (IOException ex) {
       os = "OTHER";
     }
   }
   
   public static String getOs() {
     return os;
   }
   
   public static String getArch() {
     return arch;
   }
 }