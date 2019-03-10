import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SequencerTest {
    @Test
        //Serial testing
    void  test0() {
        Sequencer s = new Sequencer();
        int MAX = 256;
        for(int i = 0; i < MAX; i++) {
            long v = s.getNext();
            //System.out.println(i);
            //System.out.println(v);
            Assertions.assertEquals(i, v);
        }
    }

    //test with threads
    class ST implements Runnable {
        final int MAX;
        int id;
        Sequencer s;
        long v;

        ST(int id, int MAX, Sequencer s) {
            this.id = id;
            this.MAX = MAX;
            this.s = s;
        }

        @Override
        public void run() {
            v = -1;
            for(int i = 0; i < MAX; i++) {
                v = s.getNext();
            }
        }
    }

    @Test
    void test1() {
        Sequencer s = new Sequencer();
        int NTHREADS = 2;
        int MAX = 100;
        ST[] rs = new ST[NTHREADS];
        Thread[] ts = new Thread[NTHREADS];

        for(int n = 0; n < NTHREADS; n++) {
            ST st = new ST(n, MAX, s);
            rs[n] = st;
            ts[n] = new Thread(st);
            ts[n].start();
        }
        long max = -1;
        int n;
        for(n = 0; n < NTHREADS; n++) {
            try {
                ts[n].join();
                max = rs[n].v > max ? rs[n].v : max;
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Assertions.assertEquals(MAX*NTHREADS-1, max);
    }

}