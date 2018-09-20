package DelayQueue;

import java.util.Date;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class DelayQueueTest {
    public static void main(String[] args) {
        DelayQueue<DelayTask> queue = new DelayQueue<>();
        queue.add(new DelayTask("test1", 1000L,"1", TimeUnit.MILLISECONDS));
        queue.add(new DelayTask("test2", 2000L, "2",TimeUnit.MILLISECONDS));
        queue.add(new DelayTask("test3", 3000L,"3", TimeUnit.MILLISECONDS));
        
        while(!queue.isEmpty()) {
            try {
            	System.out.println(queue.size());
                DelayTask task = queue.take();
                System.out.println(task.name + ":"+task.params+ ":"  + new Date());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
class DelayTask implements Delayed {
    public String name;
    public Long delayTime;
    public TimeUnit delayTimeUnit;
    public Long executeTime;//ms
    public String params;

    DelayTask(String name, long delayTime, String params,TimeUnit delayTimeUnit) {
        this.name = name;
        this.delayTime = delayTime;
        this.delayTimeUnit = delayTimeUnit;
        this.params = params;
        this.executeTime = System.currentTimeMillis() + delayTimeUnit.toMillis(delayTime);
    }


    @Override
    public int compareTo(Delayed o) {
        if(this.getDelay(TimeUnit.MILLISECONDS) > o.getDelay(TimeUnit.MILLISECONDS)) {
            return 1;
        }else if(this.getDelay(TimeUnit.MILLISECONDS) < o.getDelay(TimeUnit.MILLISECONDS)) {
            return -1;
        }
        return 0;
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(executeTime - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
    }

}
