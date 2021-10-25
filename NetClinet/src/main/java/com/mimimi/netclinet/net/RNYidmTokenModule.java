package com.mimimi.netclinet.net;

import com.blankj.utilcode.util.StringUtils;
import com.mimimi.netclinet.Promise;
import com.mimimi.netclinet.path.Host;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class RNYidmTokenModule {

  
  private static String salt = "yidmcom!@%^$$&**";

  
  private static Long getTodayZeroPointTimestampsLong() {
    Long long_ = Long.valueOf(System.currentTimeMillis());
    return Long.valueOf(long_.longValue() - (long_.longValue() + 28800000L) % Long.valueOf(86400000L).longValue());
  }
  
  private static String setTodayZeroPointTimestamps() {
    return Long.toString(Math.round((float)(getTodayZeroPointTimestampsLong().longValue() / 1000L)));
  }
  
  public static String MD5(String paramString) {
    // Byte code:
    //   0: bipush #16
    //   2: newarray char
    //   4: astore #7
    //   6: aload #7
    //   8: dup
    //   9: iconst_0
    //   10: ldc 48
    //   12: castore
    //   13: dup
    //   14: iconst_1
    //   15: ldc 49
    //   17: castore
    //   18: dup
    //   19: iconst_2
    //   20: ldc 50
    //   22: castore
    //   23: dup
    //   24: iconst_3
    //   25: ldc 51
    //   27: castore
    //   28: dup
    //   29: iconst_4
    //   30: ldc 52
    //   32: castore
    //   33: dup
    //   34: iconst_5
    //   35: ldc 53
    //   37: castore
    //   38: dup
    //   39: bipush #6
    //   41: ldc 54
    //   43: castore
    //   44: dup
    //   45: bipush #7
    //   47: ldc 55
    //   49: castore
    //   50: dup
    //   51: bipush #8
    //   53: ldc 56
    //   55: castore
    //   56: dup
    //   57: bipush #9
    //   59: ldc 57
    //   61: castore
    //   62: dup
    //   63: bipush #10
    //   65: ldc 97
    //   67: castore
    //   68: dup
    //   69: bipush #11
    //   71: ldc 98
    //   73: castore
    //   74: dup
    //   75: bipush #12
    //   77: ldc 99
    //   79: castore
    //   80: dup
    //   81: bipush #13
    //   83: ldc 100
    //   85: castore
    //   86: dup
    //   87: bipush #14
    //   89: ldc 101
    //   91: castore
    //   92: dup
    //   93: bipush #15
    //   95: ldc 102
    //   97: castore
    //   98: pop
    //   99: aload_1
    //   100: invokevirtual getBytes : ()[B
    //   103: astore_1
    //   104: ldc 'MD5'
    //   106: invokestatic getInstance : (Ljava/lang/String;)Ljava/security/MessageDigest;
    //   109: astore #8
    //   111: aload #8
    //   113: aload_1
    //   114: invokevirtual update : ([B)V
    //   117: aload #8
    //   119: invokevirtual digest : ()[B
    //   122: astore_1
    //   123: aload_1
    //   124: arraylength
    //   125: istore #4
    //   127: iload #4
    //   129: iconst_2
    //   130: imul
    //   131: newarray char
    //   133: astore #8
    //   135: iconst_0
    //   136: istore_2
    //   137: iconst_0
    //   138: istore_3
    //   139: goto -> 157
    //   142: new java/lang/String
    //   145: dup
    //   146: aload #8
    //   148: invokespecial <init> : ([C)V
    //   151: astore_1
    //   152: aload_1
    //   153: areturn
    //   154: astore_1
    //   155: aconst_null
    //   156: areturn
    //   157: iload_2
    //   158: iload #4
    //   160: if_icmpge -> 142
    //   163: aload_1
    //   164: iload_2
    //   165: baload
    //   166: istore #5
    //   168: iload_3
    //   169: iconst_1
    //   170: iadd
    //   171: istore #6
    //   173: aload #8
    //   175: iload_3
    //   176: aload #7
    //   178: iload #5
    //   180: iconst_4
    //   181: iushr
    //   182: bipush #15
    //   184: iand
    //   185: caload
    //   186: castore
    //   187: iload #6
    //   189: iconst_1
    //   190: iadd
    //   191: istore_3
    //   192: aload #8
    //   194: iload #6
    //   196: aload #7
    //   198: iload #5
    //   200: bipush #15
    //   202: iand
    //   203: caload
    //   204: castore
    //   205: iload_2
    //   206: iconst_1
    //   207: iadd
    //   208: istore_2
    //   209: goto -> 157
    // Exception table:
    //   from	to	target	type
    //   99	135	154	java/lang/Exception
    //   142	152	154	java/lang/Exception
    return getMD5Str(paramString);
  }
  public static String getMD5Str(String str) {
    byte[] digest = null;
    try {
      MessageDigest md5 = MessageDigest.getInstance("md5");
      digest  = md5.digest(str.getBytes(StandardCharsets.UTF_8));
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }
    //16是表示转换为16进制数
    String md5Str = new BigInteger(1, digest).toString(28);
    return md5Str;
  }

  public static String generateToken(String paramString) {
  return Host.TEST_APP_TOKEN;
  }

  
  public String getName() {
    return "RNYidmToken";
  }
}