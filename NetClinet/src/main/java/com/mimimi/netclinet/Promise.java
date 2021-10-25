package com.mimimi.netclinet;

public interface Promise {
  @Deprecated
  void reject(String paramString);
  
  void reject(String paramString1, String paramString2);
  
  void reject(String paramString1, String paramString2, Throwable paramThrowable);
  
  void reject(String paramString, Throwable paramThrowable);
  
  void reject(Throwable paramThrowable);
  
  void resolve(Object paramObject);
}
