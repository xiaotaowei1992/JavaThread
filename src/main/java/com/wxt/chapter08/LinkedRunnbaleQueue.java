package com.wxt.chapter08;

import java.util.LinkedList;

public class LinkedRunnbaleQueue implements RunnableQueue {
	//任务队列的最大容量，在构造时候传入
	private final int limit;
	
	//若任务队列中的任务已经满了，则需要执行拒绝策略
	private final DenyPolicy denyPolicy;
	
	//存放任务的队列
	private final LinkedList<Runnable> runnableList = new LinkedList<Runnable>();
	
	private final ThreadPool threadPool;
	
	public LinkedRunnbaleQueue(int limit, DenyPolicy denyPolicy,
			ThreadPool threadPool) {
		super();
		this.limit = limit;
		this.denyPolicy = denyPolicy;
		this.threadPool = threadPool;
	}

	@Override
	public void offer(Runnable runnable) {
		synchronized (runnableList) {
			if (runnableList.size() >= limit) {
				//无法容忍纳入新的任务时，执行拒绝策略
				denyPolicy.reject(runnable, threadPool);
			} else {
				//将任务加入到队尾，并且唤醒阻塞中的线程
				runnableList.addLast(runnable);
				runnableList.notifyAll();
			}
		}

	}

	@Override
	public Runnable take() {
		synchronized (runnableList) {
			while (runnableList.isEmpty()) {
				try {
					//如果任务队列中没有可执行任务，则当前线程将会挂起，进入runnableList关联的monitor wait set 
					//等待被唤醒（新任务的加入）
					runnableList.wait();
				} catch (InterruptedException e) {
					// 被中断时候将异常抛出
					e.printStackTrace();
				}
			}
		}
		//从任务队列头部移除一个任务
		return runnableList.removeFirst();
	}

	@Override
	public int size() {
		synchronized (runnableList) {
			//返回当前任务队列中的任务数
			return runnableList.size();
		}
	
	}

}
