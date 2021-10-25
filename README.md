
ko## jetPact之WorkManger详解

### 前言

大量应用程序都有在后台执行任务的需求。根据需求的不同，Android为后台任务提供了多种解决方案，如JobScheduler，Loader，Service等。如果这些API没有被适当地使用，可能会消耗大量的电量。Android在解决应用程序耗电问题上做了各种尝试，从Doze到App Standby，通过各种方式限制和管理应用程序，以保证应用程序不会在后台过量消耗设备电量。WorkManager的出现，则是为应用程序中那些不需要及时完成的任务，提供统一的解决方案，以便在设备电量和用户体验之间达到一个比较好的平衡。

### 兼容性

![在这里插入图片描述](https://img-blog.csdnimg.cn/a10c24f2c20b49d69f6c4bef4c77dffe.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBA5LuK5aSp55qE6aOO5YS_5aW96ICA55y8,size_15,color_FFFFFF,t_70,g_se,x_16#pic_center)


WorkManager最低能兼容API Level 14，并且不需要设备安装有Google Play Services。因此，不用过于担心兼容性问题。

WorkManager依据设备情况选择方案
WorkManager能依据设备的情况，选择不同的执行方案。在API Level 23+，通过JobScheduler来完成任务，而在API Level 23以下的设备中，通过AlarmManager和Broadcast Receivers组合完成任务。但无论采用哪种方案，任务最终都是交由Executor来完成。

### 使用方法

1.建立任务容器继承Worker抽象类，实现其抽象方法

public class BackGroundTask extends Worker {

```java
public BackGroundTask(@NonNull Context context, @NonNull WorkerParameters workerParams) {
    super(context, workerParams);
}

@NonNull
@Override
public Result doWork() {
    return null;
}
```

}

2.建立任务请求,同时setInputData()方法向Worker传递数据。

分为两种

OneTimeWorkRequest：是只会执行一次的任务请求

PeriodicWorkRequestBuilder：可以周期性的执行任务请求

这里使用OneTimeWorkRequest为例

```kotlin
  val task = OneTimeWorkRequest.Builder(NetAutoInfoTask::class.java)
        .setInputData(Data.Builder()
            .putString("id",id)
            .build())
        .build()
```

3.在容器里的dowork可以进行处理后台操作,下面的操作就是简单的在workmananger里使用了同步网络请求作为示例，获取到任务执行请求里设置的data里的数据进行一个参数的填充，在网络请求完成后，通过返回码进行不同的处理

```kotlin
override fun doWork(): Result {
  //获取data里的id
    val id = inputData.getString("id")
    //使用okhttp来请求网络
    val okHttpClient = OkHttpClient()
    val request: Request = Request.Builder()
        .url(url+id)
        .addHeader("Cookie",Constants.TOKEN)
        .addHeader("tenantId",Constants.TENANTID.toString())
        .addHeader("currentUser",Constants.CURRENTUSER)
        .build()
    val call: Call = okHttpClient.newCall(request)
    val response = call.execute()
    val json = response.body?.string()
    val jsonobject = JSONObject(json)
    val code = jsonobject.getInt("code")
    when(code)
    {
        200->
        {
            //布置值入code
            val content = Gson().fromJson(jsonobject.getString("result"),FramWorkAutoList.ResultDTO.ContentDTO::class.java)
            MyApplication.getViewModelCenter().frameAutoFrameInfoModel.setDatainfo(content)
            return Result.success()
        }
        else ->{
            //再请求失败的时候可以建立一个新的data容器保存失败结果并提交
            var data = Data.Builder()
            data.putString("errorCode",code.toString())
            return Result.failure(data.build())
        }

    }
    
    return Result.failure()
}
```

在处理请求失败的片段里将错误码放入data容器里，然后回到WorkMananger观察任务的状态

任务在提交给系统后，通过WorkInfo获知任务的状态，WorkInfo包含了任务的id，tag，以及Worker对象传递过来的outputData，以及任务当前的状态。有三种方式可以得到WorkInfo对象。

WorkManager.getWorkInfosByTag()

WorkManager.getWorkInfoById()

WorkManager.getWorkInfosForUniqueWork()

如果你希望能够实时获知任务的状态。这三个方法还有对应的LiveData方法。

WorkManager.getWorkInfosByTagLiveData()

WorkManager.getWorkInfoByIdLiveData()

WorkManager.getWorkInfosForUniqueWorkLiveData()

通过LiveData，我们便可以在任务状态发生变化的时候，收到通知。

<br/>

```kotlin
   fun taskPost(workRequest:WorkRequest):WorkManager
    {
      val  workManager = WorkManager.getInstance(MyApplication.getContext())
      workManager.enqueue(workRequest)
      return workManager
    }
    taskPost(task).getWorkInfoByIdLiveData(task.id).observe(this,{
        when(it.state)
        {
            WorkInfo.State.SUCCEEDED->
            {
                Log.e("加载成功","成功")
            }
            WorkInfo.State.FAILED->
            {
                postToast(this,"获取详情失败")
            }
            else ->{}
        }
    })
```

6.取消任务。与观察任务类似的，我们也可以根据Id或者Tag取消某个任务，或者取消所有任务。

WorkManager.cancelAllWork()
WorkManager.cancelAllWorkByTag(tag)
WorkManager.cancelWorkById(id)

有三种方式取消任务执行请求。

第一种是取消所有的任务

第二种是通过给任务请求设置的tag(OneTimeWorkRequest.Builder().setTag().Build())来取消某个或某些任务,多个任务可以使用同一个tag

第三种是通过任务请求的id来取消指定的任务,通过源码发现任务请求在执行完build时会生成一个唯一的id

<br/>

```kotlin
    /**
     * Builds a {@link WorkRequest} based on this {@link Builder}.
     *
     * @return A {@link WorkRequest} based on this {@link Builder}
     */
    public final @NonNull W build() {
        W returnValue = buildInternal();
        // Create a new id and WorkSpec so this WorkRequest.Builder can be used multiple times.
        mId = UUID.randomUUID();
        mWorkSpec = new WorkSpec(mWorkSpec);
        mWorkSpec.id = mId.toString();
        return returnValue;
    }
```

7.对任务设置约束条件

可以指定任务请求在某些条件下执行或者不执行

```kotlin
 val constraints = Constraints.Builder()
  //需要在网络链接上的时候执行
    constraints.setRequiredNetworkType(NetworkType.CONNECTED)
    //需要在电量不低的时候执行
    constraints.setRequiresBatteryNotLow(true)
    //需要在设备挂起的时候执行
    constraints.setRequiresDeviceIdle(true)
    //充电的时候执行
    constraints.setRequiresCharging(true)
    ........//其他约束条件
    constraints.build()
    
    
 val task = OneTimeWorkRequest.Builder(NetAutoInfoTask::class.java)
           .setConstraints(constraints.build())
           .setInputData(Data.Builder()
                .putString("id",id)
                .build())
            .build()
    
    
```

#### （Demo环节）

##### 实现一个基于Workmananger编写的小说阅读器

<br/>

8.使用任务链高效的组合业务逻辑

使用workmananger执行小说后台下载任务以及压缩任务，先对指定点击的章节进行下载，然后再对指定的章节文件进行解压，并且在过程中持续返回进度

可以进行对当前所有的livedatalist进行一个数据观察来决定之后的流程


![在这里插入图片描述](https://img-blog.csdnimg.cn/d5bdf1d08ada48669f11a1869d5fcb56.jpg?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBA5LuK5aSp55qE6aOO5YS_5aW96ICA55y8,size_20,color_FFFFFF,t_70,g_se,x_16#pic_center)


```kotlin
/**
 * 开始进行指定章节下载任务
 */
fun startNewDownloadTask(aid: String, vid: String) {
    if (!checkFileExits(context?.externalCacheDir?.path + "/" + aid + vid + ".zip")) {
        //下载
        val downloadReqsuet = OneTimeWorkRequest.Builder(DownLoadTask::class.java)
            .addTag("download")
            .setInputData(Data.Builder()
                .putString("aid",aid)
                .putString("vid",vid)
                .build()).build()
        listUUID.add(downloadReqsuet.id)
        workManager.enqueue(downloadReqsuet)

    }

}

/**
 * 开始进行指定章节解压任务
 */
fun startGzip(aid: String,vid: String)
{
    //目录不存在
    if (!FileUtils.isDir(context.externalCacheDir?.path + "/" + aid+"/"+vid)) {
        val gzipRequset = OneTimeWorkRequest.Builder(GZipTask::class.java)
            .addTag("gzip")
            .setInputData(Data.Builder().putString("aid",aid)
                .putString("vid",vid).build())
            .build()
        listUUID.add(gzipRequset.id)
        workManager.enqueue(gzipRequset)
    }
}
```

使用addTag函数对任务请求进行标识,然后我们在workmananger里通过tag获取对应的workinfoslivedata进行数据的处理

![在这里插入图片描述](https://img-blog.csdnimg.cn/51e576ed847b4b148d51ecbee30fde1a.jpg?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBA5LuK5aSp55qE6aOO5YS_5aW96ICA55y8,size_20,color_FFFFFF,t_70,g_se,x_16#pic_center)


<br/>

```kotlin
    //对于压缩的处理
   workManager.getWorkInfosByTagLiveData("gzip").observe(this,{
        it.forEach {
            when (it.state) {
                WorkInfo.State.SUCCEEDED -> {
                   //解压成功
                   //执行视图更新任务
                }
                WorkInfo.State.ENQUEUED -> {

                }
                WorkInfo.State.RUNNING -> {

                }
                WorkInfo.State.FAILED -> {
                //解压出现问题

                }
                else -> {

                }
            }
        }
    })
    

workManager.getWorkInfosByTagLiveData("download").observe(this,
            {
                it.forEach {
                    when (it.state) {
                        WorkInfo.State.SUCCEEDED -> {

                            //下载完成
                            //开始执行对应章节的压缩任务
                            //示例代码
                            if (FileUtils.isFileExists(context?.externalCacheDir?.path+"/"+aid+vid+".zip"))
                            {
                               workCenter.startGzip(aid,vid)
                            }
                            

                        }
                        WorkInfo.State.ENQUEUED -> {

                        }
                        WorkInfo.State.RUNNING -> {
                        //下载中的状态

                        }
                        WorkInfo.State.FAILED -> {
                          //下载失败
                          //执行适配器更新
                        }
                        else -> {

                        }
                    }
                }

        })
```

这一阶段已经处理完毕了，接下来就是点击阅读了，下载的文件为电子书epub格式，epub格式简单的来看其实就是压缩包，里面包含了图片和文本以及目录文件，我们只需要取得目录文件，使用固定的规则读取即可。

![在这里插入图片描述](https://img-blog.csdnimg.cn/8269edc5685f40a88de76eb56ec01523.jpg?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBA5LuK5aSp55qE6aOO5YS_5aW96ICA55y8,size_20,color_FFFFFF,t_70,g_se,x_16#pic_center)


### Ebup解压后文件目录结构
![在这里插入图片描述](https://img-blog.csdnimg.cn/b5991ee7c253449a8a0344063417a407.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBA5LuK5aSp55qE6aOO5YS_5aW96ICA55y8,size_20,color_FFFFFF,t_70,g_se,x_16#pic_center)

```kotlin
   class PageInitTask(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {
    override fun doWork(): Result {
        val cid = inputData.getString("cid")
        val aid = inputData.getString("aid")
        val vid = inputData.getString("vid")
        val path = inputData.getString("filePath")
        //根据opf文件获取图片集合
        val chaptor = BookCenter.getChaptor(applicationcontext,filepath)
        //根据opf文件获取章节文本
        val epubDatas = mutableListOf<EpubData>()
        val path = File(context.externalCacheDir?.path+"/"+chaptor.aid+"/"+chaptor.vid)
        chaptor.chapters.forEach {
            //具体每一章节的文件路径
            var cpath = path.path+"/"+it.cid
            //插图筛选出来
            val listpic = getPicListByContent(readContent(cpath)!!, Host.RESULT)
            listpic.forEach{
                epubDatas.add(EpubData(path.path+"/"+it.trim(), EpubData.TYPE.IMG))
            }
            epubDatas.add(EpubData(readContent(cpath), EpubData.TYPE.TEXT))
        }
        //todo 
      
          val data = Data.Builder()
                .putString("aid",aid)
                .putString("vid",vid)
                .putString("cid",cid)
                //转为json传输,实际上持久化在数据库更好
                .putString("data",mGson.toJson(epubDatas))
                .build()
          //返回处理结果
          return Result.success(data)
  
      
  }
}



-------------------------------------处理小说事件
workManager.getWorkInfosByTagLiveData("download").observe(this,
            {
                it.forEach {
                    when (it.state) {
                        WorkInfo.State.SUCCEEDED -> {

                            //处理完成
                            //加载入ui
                            mpageview.initepub(mGson.fromJson(inputdata.getString("data")......))
                            
                            

                        }
                        else -> {

                        }
                    }
                }

        })
```

![在这里插入图片描述](https://img-blog.csdnimg.cn/17c66c3cb5564cbb812c47a0b04c792e.jpg?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBA5LuK5aSp55qE6aOO5YS_5aW96ICA55y8,size_20,color_FFFFFF,t_70,g_se,x_16#pic_center)
![在这里插入图片描述](https://img-blog.csdnimg.cn/b0b2ead4c3794003bc9645b297a7e0e3.jpg?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBA5LuK5aSp55qE6aOO5YS_5aW96ICA55y8,size_20,color_FFFFFF,t_70,g_se,x_16#pic_center)

9 . Worker 的各种状态说明
在Worker 生命周期内,会经历不同的 State

如果有尚未完成的前提性工作，则工作处于 BLOCKED State。
如果工作能够在满足 约束条件 和时机条件后立即运行，则被视为处于 ENQUEUED 状态。
当 Worker 在活跃地执行时，其处于 RUNNING State。
如果 Worker 返回 Result.success()，则被视为处于 SUCCEEDED 状态。这是一种终止 State；只有 OneTimeWorkRequest 可以进入这种 State。
如果 Worker 返回 Result.failure()，则被视为处于 FAILED 状态。这也是一个终止 State；只有 OneTimeWorkRequest 可以进入这种 State。所有依赖工作也会被标记为 FAILED，并且不会运行。
当取消尚未终止的 WorkRequest 时，它会进入 CANCELLED State。所有依赖工作也会被标记为 CANCELLED，并且不会运行。

<br/>

### 总结

开发者经常需要处理后台任务，如果处理后台任务所采用的API没有被正确使用，那么很可能会消耗大量设备的电量。Android出于设备电量的考虑，为开发者提供了WorkManager，旨在将一些不需要及时完成的任务交给它来完成。虽然WorkManager宣称，能够保证任务得到执行。在真实的设备中，执行情况可能不如人意，比如有的rom是不允许AlarmManager被唤起，那么执行不能得到保证

在模拟器中进行测试，模拟器采用的是Google原生系统，发现无论是彻底退出应用程序，或是重启设备，任务都能够被执行。所以，WorkManager在真实设备中不能正常使用，根本就是系统的问题。使用WorkManager作为解决方案时要慎重。

周期任务的实际执行，与所设定的时间差别较大。执行时间看起来并没有太明显的规律。并且在任务执行完成后，WorkInfo并不会收到Success的通知。因为Success和Failure都属于终止类的通知。如果发出这类通知，则表明任务彻底结束，而周期任务不会彻底终止，会一直执行下去，在使用LiveData观察周期任务时，不会收到Success这类的通知。
