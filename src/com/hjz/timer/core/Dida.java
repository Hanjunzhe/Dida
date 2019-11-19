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
//		new һ��������
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
			System.out.println("���¿�����");
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
			System.out.println("û���κ��£�");
			return;
		}
		if (goon == false) {
			System.out.println("ʱ���Ѿ�ֹͣ��ʱ��");
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
//				�Ƚ��� �ȴ�  �����Լ� ����������
				lock.notify();
//				�������̻߳���
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
//						�Ƚ��Լ�����  ��������
//						Ƭ��û�� �����doSomething
//						����������dida�߳�
						}
						action.doSomething();
					} catch (InterruptedException e) {
						e.printStackTrace();
					
					}
				
				}
			}
			
		}
//	doSomething ÿ�εļ��ʱ����delayTime������
//	��Ϊ ����ʱ�䵽�� ���Ǹ��߳�û׼���ã����ٽ���һ�εȴ�
//	�����Ǹ��̺߳��� ���� ʱ��û���� �����ȵȴ�ʱ�����
	
		}

	
	
	
	

