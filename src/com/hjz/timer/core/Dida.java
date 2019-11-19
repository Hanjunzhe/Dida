package com.hjz.timer.core;


public class Dida implements Runnable{
	private static  long  delayTime;
	private static final long  DEFAULT_DELAYTIME=1000;
	private Object lock;
	private volatile boolean goon;
	private IDidaAction action;
//	private Action action;
	
	public Dida() {
		this(DEFAULT_DELAYTIME);
	}

	public Dida(long delayTime) {
		this( delayTime ,null);
	}
	
	public  Dida(long  delayTime,IDidaAction action ) {
		this.lock=new Object();
//		new 一个对象锁
		this.action=action;
		this.delayTime=delayTime;
	}
	
	public static long getDelayTime() {
		return delayTime;
	}

	public static void setDelayTime(long delayTime) {
		Dida.delayTime = delayTime;
	}

	public IDidaAction getAction() {
		return action;
	}

	public void setAction(IDidaAction action) {
		this.action = action;
	}
	
	void start() {
		if (action == null) {
			System.out.println("无事可做！");
			return;
		}
		if (goon) {
			return;
		}
		goon = true;
		new Thread(new TimeWorker(), "TimeWork").start();
		new Thread(this, "DidaDida").start();
	}

	void  stop() {
		if (action == null) {
			System.out.println("没做任何事！");
			return;
		}
		if (goon == false) {
			System.out.println("时钟已经停止计时！");
			return;
		}
		goon = false;
	}
	
	

	@Override
	public void run() {
		while(goon)
		synchronized(lock){
			try {
				lock.wait(delayTime);
//				先进行 等待  阻塞自己 将对象锁打开
				lock.notify();
//				将下面线程唤醒
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
		
	}
	
	class TimeWorker implements Runnable{
		public void TimerWorker() {
			
		}
		@Override
		public void run() {
			while(goon) {
					try {
						synchronized(lock) {
						lock.wait();
//						先将自己阻塞  对象锁打开
//						片段没到 则进行doSomething
//						到了则运行dida线程
						}
						action.doSomething();
					} catch (InterruptedException e) {
						e.printStackTrace();
					
					}
				
				}
			}
			
		}
//	doSomething 每次的间隔时间是delayTime整数倍
//	因为 可能时间到了 但那个线程没准备好，就再进行一次等待
//	或者那个线程好了 但是 时间没到就 继续等等待时间完结
	
		}

	
	
	
	

