# BurpUnlimitedre update BurpSuite v1.7.27

s/1ht1Fw2K h4ip
为买正版不心疼奋斗

# 目录不要有中文和空格，64位操作系统不要使用32位的Jdk。酱紫你们就可以愉快的运行了！！！

+ Created by: x-Ai
+ Based on: BurpLoader by larry_lau && BurpUnlimited by mxcxvn
+ Github repos: https://github.com/x-Ai/BurpUnlimitedre

This project !replace! BurpUnlimited of depend (BurpSuite version 1.7.27). It is NOT intended to replace BurpLoader and BurpUnlimited！

To run the project from the command line: 

`java -javaagent:BurpUnlimitedre.jar -agentpath:lib/libfaketime<osverion> -jar BurpUnlimitedre.jar`

or double click on BurpUnlimited.jar (set permision before)

## Notes: 
1. There are some requirements files in lib at current folder:
 + **burpsuite_pro_v1.7.27.jar** is main object
 + **libfaketime*** Lib for hook time activation. Sourcecode is at https://github.com/faketime-java/faketime


## File Hash MD5
```
BurpUnlimited.jar                     AC4725EA240E4E5F916708262C4F2C78  
lib/
    burpsuite_pro_v1.7.27.jar	      F579B2B8692DDE5D0EF6388D91A98D55
    libfaketime32.dll		      E3842711A065B672DEC322C4140B950F
    libfaketime32.jnilib	      D2B62D06A972035149BFDEFE1605C041
    libfaketime32.so		      5C2BAA272037207533D74FAA4291E91D
    libfaketime64.dll		      6659EFEEE9698609A9FFD9EA8C9D07D1
    libfaketime64.jnilib	      FF3DBDE6A28F1C59D829CF5665C8E628
    libfaketime64.so		      5C2BAA272037207533D74FAA4291E91D  
windows_vcredist/
    vcruntime140_x32.dll	      B77EEAEAF5F8493189B89852F3A7A712
    vcruntime140_x64.dll	      6C2C88FF1B3DA84B44D23A253A06C01B
```
## This project references some codes and some projects bellow:
+ Decompile BurpLoader's larry_lau
+ https://github.com/mxcxvn/BurpUnlimited 
