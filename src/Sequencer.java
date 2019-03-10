public class Sequencer {
    private long val;
    public Sequencer() {
        val = 0;
    }
    long getNext() {
        return val++;
    }
}


