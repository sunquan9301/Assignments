package assignment3;

/**
 * produce quest in quest queue
 */
public class Producer extends Thread {
    private Agenda agenda;

    public Producer(Agenda dataManager) {
        this.agenda = dataManager;
    }

    @Override
    public void run() {
        while (true) {
            try {
                agenda.produceQuest();
                Thread.sleep(Params.getProduceQuestTime());
            } catch (Exception e) {
                this.interrupt();
            }
        }
    }
}
