
package com.yunmel.commons.service;

import org.apache.http.conn.HttpClientConnectionManager;

public class IdleConnectionEvictor extends Thread {
  private final HttpClientConnectionManager connMgr;
  private volatile boolean shutdown;

  public IdleConnectionEvictor(HttpClientConnectionManager connMgr) {
    this.connMgr = connMgr;
    start();
  }

  public void run() {
    try {
      while (!this.shutdown)
        synchronized (this) {
          wait(30000L);
          this.connMgr.closeExpiredConnections();
        }
    } catch (InterruptedException localInterruptedException) {
    }
  }

  public void shutdown() {
    this.shutdown = true;
    synchronized (this) {
      notifyAll();
    }
  }
}
