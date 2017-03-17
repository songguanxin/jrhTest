package daywork;

/**
 * desc: AsyncTask使用在哪些场景？它的缺陷是什么？如何解决？
 * Created by jiarh on 17/3/8 09:46.
 */

public class Day13 {

//    AsyncTask 运用的场景就是我们需要进行一些耗时的操作，耗时操作完成后更新主线程，或者在操作过程中对主线程的UI进行更新。AsycnTask<Params,Progress,Result>是一个抽象类，有三个泛型参数，分别对应启动任务执行的输入参数类型，后台任务完成的进度值类型，执行结果类型。
//
//    我们需要重载四个方法，onPreExecute()执行初始化工作；doInBackground(Params...)用于耗时操作；可以调用publishProgress()跟新进度；onProgressUpdate()用于进度更新；onPostExecute()用于处理结果
//    缺陷：AsyncTask中维护着一个长度为128的线程池，同时可以执行5个工作线程，还有一个缓冲队列，当线程池中已有128个线程，缓冲队列已满时，如果此时向线程提交任务，将会抛出RejectedExecutionException。
//
//    在3.0以前，最大支持128个线程的并发，10个任务的等待。在3.0以后，无论有多少任务，都会在其内部单线程执行
//    解决：由一个控制线程来处理AsyncTask的调用判断线程池是否满了，如果满了则线程睡眠否则请求AsyncTask继续处理。
}
